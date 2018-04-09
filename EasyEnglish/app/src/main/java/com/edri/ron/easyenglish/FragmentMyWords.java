package com.edri.ron.easyenglish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edri.ron.easyenglish.Classes.FragmentSaveState;
import com.edri.ron.easyenglish.Classes.MyApp;
import com.edri.ron.easyenglish.Classes.Word;
import com.edri.ron.easyenglish.Classes.WordsList;
import com.edri.ron.easyenglish.DialogFragments.DeleteAllDialogFragment;
import com.edri.ron.easyenglish.DialogFragments.QuestionDialogFragment;

import java.util.ArrayList;

/**
 * Created by Ron on 02/04/2018.
 */

public class FragmentMyWords extends Fragment {

    private WordsList wordsList;
    public final int sort_None = 0, sort_verbs = 1, sortNouns = 2, sort_Adjective = 3;
    private LinearLayout linearLayoutRoot;
    private TextView tvTitle;
    private int REQUEST_EDIT = 1, REQUEST_TEST = 2;
    private boolean showTrans;

    public FragmentMyWords() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        linearLayoutRoot = (LinearLayout)inflater.inflate(R.layout.fragment_my_words, container, false);
        tvTitle = (TextView) linearLayoutRoot.findViewById(R.id.textViewTitle);

        Bundle bundle = getArguments();
        if(bundle != null) {
            wordsList = (WordsList) bundle.getSerializable("wordsList");
            wordsList.reloadFromDB();
        }

        setHasOptionsMenu(true);
        //showTrans = false;
        showTrans = FragmentSaveState.getSwitchState();

        loadListView(sort_None);

        return linearLayoutRoot;
    }

    //  Method to load all words from WordsList to the list view
    public void loadListView(int sort) {
        String[] WordNames = showTrans ? wordsList.getNamesStringArrayWithTrans() : wordsList.getNamesStringArray();

        wordsList.reloadFromDB();

        int size = wordsList.size();
        if(size > 0)
            tvTitle.setText("My Words (" + size + ")");

        if(sort != sort_None)
            WordNames = sortListView(sort, WordNames);


        //simple_list_item_1
        ListAdapter MyAdapter = new ArrayAdapter<>(MyApp.getContext(), R.layout.custom_list_item, WordNames);
        ListView MyListView = (ListView)linearLayoutRoot.findViewById(R.id.listView);
        MyListView.setAdapter(MyAdapter);


        MyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showActionsDialog(i);
                return true;
            }
        });

        MyListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Word word = wordsList.get(i);
                        Intent intent = new Intent(MyApp.getContext(), EditWordActivity.class);
                        MyApp.setWordsList(wordsList);
                        intent.putExtra("word", word);
                        startActivityForResult(intent, REQUEST_EDIT);
                    }

                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_EDIT && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("newWordName");

            loadListView(sort_None);
            Toast.makeText(MyApp.getContext(), name + " has been updated !", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == REQUEST_TEST && resultCode == Activity.RESULT_OK) {

        }
    }

    public void showActionsDialog(int position) {
        QuestionDialogFragment questionDialogFragment = QuestionDialogFragment.newInstance("Delete this product?", wordsList.get(position));
        questionDialogFragment.show(getFragmentManager(), null); // null = the tag for this fragment
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_EDIT && resultCode == Activity.RESULT_OK) {
            //loadListView(sort_None);
            Toast.makeText(MyApp.getContext(), data.getExtras().getString("newWordName") + " was edited successfully !", Toast.LENGTH_SHORT).show();
        }
    }*/

    //  Method to sort the words in list view by group types
    public String[] sortListView(int sort, String[] strings) {
        if(sort == sort_None) return strings;
        ArrayList<String> arr = new ArrayList<>();
        for (String s : strings) {
            switch (sort) {
                case sort_verbs:
                    if(s.contains("Verb"))
                        arr.add(s);
                    break;

                case sortNouns:
                    if(s.contains("Noun"))
                        arr.add(s);
                    break;

                case sort_Adjective:
                    if(s.contains("Adjective"))
                        arr.add(s);
                    break;
            }
        }

        String[] s = new String[arr.size()];
        arr.toArray(s);
        return s;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_my_words, menu);
        SwitchCompat switchAB = (SwitchCompat)menu.findItem(R.id.app_bar_switch).getActionView().findViewById(R.id.menu_switcher);
        if(FragmentSaveState.getSwitchState())
            switchAB.setChecked(true);
            switchAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    showTrans = b;
                    loadListView(sort_None);
                }
            });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.app_bar_delete) {
            DeleteAllDialogFragment deleteAllDialogFragment = DeleteAllDialogFragment.newInstance("Delete ALL product?");
            deleteAllDialogFragment.show(getFragmentManager(), null); // null = the tag for this fragment
            return true;
        }
        else if(item.getItemId() == R.id.app_bar_test) {
            if(wordsList.size() < 8) {
                Toast.makeText(MyApp.getContext(), "You need at least 8 words !", Toast.LENGTH_SHORT).show();
                return true;
            }
            Intent i = new Intent(getActivity(), ActivityTestYourself.class);
            startActivityForResult(i, REQUEST_TEST);
        }
        return false;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();

        FragmentSaveState.setSwitchState(showTrans);
    }
}
