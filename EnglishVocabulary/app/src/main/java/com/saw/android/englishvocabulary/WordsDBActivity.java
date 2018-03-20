package com.saw.android.englishvocabulary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordsDBActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private LinearLayout layoutDB;
    public final int sort_None = 0, sort_verbs = 1, sortNouns = 2, sort_Adjective = 3;
    private GestureDetectorCompat gestureDetector;
    private int REQUEST_EDIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_db);

        layoutDB = findViewById(R.id.layoutDB);
        MenuActivity.wordsList.reloadFromDB(); //Reloads from db only in case update is needed
        gestureDetector = new GestureDetectorCompat(this, this);

        loadListView(sort_None);
    }

    //  Method to load all words from WordsList to the list view
    public void loadListView(int sort) {
        String[] WordNames = MenuActivity.wordsList.getNamesStringArray();

        if(sort != sort_None)
            WordNames = sortListView(sort, WordNames);

        ListAdapter MyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, WordNames);
        ListView MyListView = findViewById(R.id.listView);
        MyListView.setAdapter(MyAdapter);


        MyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showActionsDialog(i);
                return false;
            }
        });

        /*MyListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String trans = MenuActivity.wordsList.getTransByName(String.valueOf(adapterView.getItemAtPosition(i)));
                        //String trans = MenuActivity.wordsList.getTransByName(String.valueOf(adapterView.getItemAtPosition(0)));
                        Toast.makeText(MyApp.getContext(), trans, Toast.LENGTH_LONG).show();
                    }

                });*/
    }

    //  Shows actions dialog
    public void showActionsDialog(int position) {
        final Word word = MenuActivity.wordsList.get(position);
        AlertDialog dialog = new AlertDialog.Builder(this)
                //.setIcon(R.drawable.delete)
                .setTitle("Action alert")
                .setMessage("Choose an option for:\n\n" + word.getName()
                    + " - " + word.getTrans() + "\t[" + word.getType() + "]")
                .setCancelable(false)
                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MyApp.getContext(), AddWordActivity.class);
                        intent.putExtra("word", word);
                        startActivityForResult(intent, REQUEST_EDIT);
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showDeleteDialog(word);
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        dialog.show();
    }

    public void showDeleteDialog(final Word word) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to delete?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MenuActivity.wordsList.deleteWord(word);
                        loadListView(sort_None);
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_EDIT && resultCode == Activity.RESULT_OK) {
            loadListView(sort_None);
            Toast.makeText(MyApp.getContext(), data.getExtras().getString("newWordName") + " was edited successfully !", Toast.LENGTH_SHORT).show();
        }
    }

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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filter_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdjective:
                loadListView(sort_Adjective);
                return true;
            case R.id.menuVerb:
                loadListView(sort_verbs);
                return true;
            case R.id.menuNoun:
                loadListView(sortNouns);
                return true;
            case R.id.menuNone:
            case R.id.menuRefresh:
                loadListView(sort_None);
                return true;
        }

        return false;
    }

    ///////// OnGestureListener METHODS //////////
    public boolean onSingleTapConfirmed(MotionEvent e) {

        return true;
    }

    public boolean onDoubleTap(MotionEvent e) {

        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent e) {

        return true;
    }

    public boolean onDown(MotionEvent e) {

        return true;
    }

    public void onShowPress(MotionEvent e) {


    }

    public boolean onSingleTapUp(MotionEvent e) {

        return true;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        return true;
    }

    public void onLongPress(MotionEvent e) {


    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return true;
    }
}
