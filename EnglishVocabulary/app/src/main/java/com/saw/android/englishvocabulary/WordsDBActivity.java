package com.saw.android.englishvocabulary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordsDBActivity extends AppCompatActivity {

    private LinearLayout layoutDB;
    public final int sort_None = 0, sort_verbs = 1, sortNouns = 2, sort_Adjective = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_db);

        layoutDB = findViewById(R.id.layoutDB);

        loadListView(sort_None);
    }

    public void loadListView(int sort) {
        //  Method to load all words to the list view
        String[] WordNames = MenuActivity.wordsList.getNamesStringArray();
        if(sort != sort_None)
            WordNames = sortListView(sort, WordNames);

        ListAdapter MyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, WordNames);
        ListView MyListView = findViewById(R.id.listView);
        MyListView.setAdapter(MyAdapter);
        MyListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String trans = MenuActivity.wordsList.getTransByName(String.valueOf(adapterView.getItemAtPosition(i)));
                        //String trans = MenuActivity.wordsList.getTransByName(String.valueOf(adapterView.getItemAtPosition(0)));
                        Toast.makeText(MyApp.getContext(), trans, Toast.LENGTH_LONG).show();
                    }

                });
    }

    public String[] sortListView(int sort, String[] strings) {
        //  Method to sort the words in list view by group types
        if(sort == sort_None) return strings;
        ArrayList<String> arr = new ArrayList<String>();
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
                loadListView(sort_None);
                return true;
        }

        return false;

    }
}
