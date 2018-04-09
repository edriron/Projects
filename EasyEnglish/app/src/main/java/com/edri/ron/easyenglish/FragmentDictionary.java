package com.edri.ron.easyenglish;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.edri.ron.easyenglish.API.ReaderController;
import com.edri.ron.easyenglish.Classes.FragmentSaveState;
import com.edri.ron.easyenglish.Classes.GroupType;
import com.edri.ron.easyenglish.Classes.MyApp;
import com.edri.ron.easyenglish.Classes.Word;
import com.edri.ron.easyenglish.Classes.WordsList;

/**
 * Created by Ron on 06/04/2018.
 */

public class FragmentDictionary extends Fragment {

    private EditText etSearch, etTrans;
    private Button btnSearch, btnQuickAdd, btnAdd;
    private WordsList wordsList;

    public FragmentDictionary() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayoutRoot = (LinearLayout)inflater.inflate(R.layout.fragment_dictionary, container, false);
        etSearch = (EditText) linearLayoutRoot.findViewById(R.id.searchTitle);
        btnSearch = (Button) linearLayoutRoot.findViewById(R.id.btnSearch);
        btnQuickAdd = (Button) linearLayoutRoot.findViewById(R.id.btnQuickAdd);
        btnAdd = (Button) linearLayoutRoot.findViewById(R.id.btnAdd);
        etTrans = (EditText) linearLayoutRoot.findViewById(R.id.etTrans);

        Bundle bundle = getArguments();
        if(bundle != null) {
            wordsList = (WordsList) bundle.getSerializable("wordsList");
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create controllers:
                ReaderController controller = new ReaderController(getActivity());

                // Show all countries from server:
                controller.readByName(etSearch.getText().toString());

                //etSearch.clearFocus();
                btnSearch.requestFocus();
            }
        });

        btnQuickAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentSaveState.setAddWordBundle(etSearch.getText().toString(), etTrans.getText().toString(), R.id.radioVerb);

                MainActivity.navigation.setSelectedItemId(R.id.navigation_new_word);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etSearch.setText(FragmentAddWord.regulateString(etSearch.getText().toString()));

                Word word = new Word(etSearch.getText().toString(), etTrans.getText().toString(), "", "", GroupType.Verb);
                int a = wordsList.addWord(word);
                if(a == -1) {
                    //word already exists
                    Toast.makeText(MyApp.getContext(), etSearch.getText().toString() + " already exists !", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(MyApp.getContext(), etSearch.getText().toString() + " was added !", Toast.LENGTH_LONG).show();
                etSearch.getText().clear();
                etTrans.getText().clear();
                btnQuickAdd.setEnabled(false);
                btnAdd.setEnabled(false);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean t = false;
                if (!etSearch.getText().toString().equals("") && !etTrans.getText().toString().equals(""))
                    t = true;
                btnQuickAdd.setEnabled(t);
                btnAdd.setEnabled(t);
            }
        });

        etTrans.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean t = false;
                if (!etSearch.getText().toString().equals("") && !etTrans.getText().toString().equals(""))
                    t = true;
                btnQuickAdd.setEnabled(t);
                btnAdd.setEnabled(t);
            }
        });

        return linearLayoutRoot;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = FragmentSaveState.getDicionaryBundle();
        if (bundle != null) {
            //Restore the fragment's state here
            etSearch.setText(bundle.getString("name"));
            etTrans.setText(bundle.getString("trans"));
            btnQuickAdd.setEnabled(bundle.getBoolean("enable"));
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        FragmentSaveState.setDicionaryBundle(etSearch.getText().toString(), etTrans.getText().toString(), btnQuickAdd.isEnabled());
    }
}
