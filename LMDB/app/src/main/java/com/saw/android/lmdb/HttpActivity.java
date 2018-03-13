package com.saw.android.lmdb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class HttpActivity extends AppCompatActivity {

    private ListView listViewCandies;
    private Dialog insertOrUpdateDialog;
    private AlertDialog deleteDialog;
    private MoviesReaderController candiesReaderController;
    private Button btnSearch;
    private EditText etTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        etTitle = findViewById(R.id.etTitle);
        btnSearch = findViewById(R.id.btnSearch);
        // Take UI components:
        listViewCandies = (ListView) findViewById(R.id.listViewMovies);

        listViewCandies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    public void onClick_search(View view) {
        // Create controllers:
        candiesReaderController = new MoviesReaderController(this, etTitle.getText().toString());

        // Show all countries from server:
        candiesReaderController.readAllCountries();
    }
}
