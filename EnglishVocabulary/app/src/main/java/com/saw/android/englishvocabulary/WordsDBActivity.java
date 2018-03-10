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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordsDBActivity extends AppCompatActivity {

    private LinearLayout layoutDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_db);

        layoutDB = findViewById(R.id.layoutDB);

        loadListView(-1);
    }

    public void loadListView(int sort) {
        String[] foods = MenuActivity.wordsList.getNamesStringArray();
        if(sort != -1)
            foods = sortListView(sort, foods);

        ListAdapter MyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foods);
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
        if(sort == 0) return strings;
        ArrayList<String> arr = new ArrayList<String>();
        for (String s : strings) {
            //  1-verb  2-noun  3-adjective 0-none
            switch (sort) {

                case 1:
                    if(s.contains("Verb"))
                        arr.add(s);
                    break;

                case 2:
                    if(s.contains("Noun"))
                        arr.add(s);
                    break;

                case 3:
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
                loadListView(3);
                return true;
            case R.id.menuVerb:
                loadListView(1);
                return true;
            case R.id.menuNoun:
                loadListView(2);
                return true;
            case R.id.menuNone:
                loadListView(0);
                return true;
        }

        return false;

    }
}
