package com.saw.android.lmdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DatabaseShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_show);
        Database db = new Database();
        ArrayList<Movie> movies = db.getAllProducts(this);
        ListView listView = findViewById(R.id.testListView);
        ArrayAdapter<Movie> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movies);
        listView.setAdapter(adapter);
    }
}
