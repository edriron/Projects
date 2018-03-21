package com.saw.android.lmdb;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.saw.android.lmdb.API.MoviesReaderController;
import com.saw.android.lmdb.API.ReaderController;

public class HttpActivity extends AppCompatActivity {

    private ListView listViewCandies;
    private Dialog insertOrUpdateDialog;
    private AlertDialog deleteDialog;
    //private MoviesReaderController candiesReaderController;
    private ReaderController controller;
    private Button btnSearch;
    private EditText etTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        etTitle = findViewById(R.id.etTitle);
        btnSearch = findViewById(R.id.btnSearch);
        // Take UI components:
        listViewCandies = findViewById(R.id.listViewMovies);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, LibraryActivity.searches);
        listViewCandies.setAdapter(adapter);

        listViewCandies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sendSearchToServer(LibraryActivity.searches.get(i));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            Intent i = new Intent();
            Movie movie = (Movie)data.getSerializableExtra("movie");
            i.putExtra("movie", movie);
            setResult(Activity.RESULT_OK, i);
            finish();
        }
    }

    public void onClick_search(View view) {

        // If no title was typed, abort method
        if(etTitle.getText().length() == 0)
            return;

        sendSearchToServer(etTitle.getText().toString());

        if(!LibraryActivity.searches.contains(etTitle.getText().toString()))
            LibraryActivity.searches.add(etTitle.getText().toString());
    }

    public void sendSearchToServer(String str) {

        // Create controllers:
        String searchString = str.replaceAll(" ", "%20"); //Replace all " " with "%20" to prevent error:400 bad-request
        controller = new ReaderController(this, 0);

        // Show all countries from server:
        controller.readByName(searchString);
    }

    /*public void sendSearchToServer(String str) {

        // Create controllers:
        String searchString = str.replaceAll(" ", "%20"); //Replace all " " with "%20" to prevent error:400 bad-request
        candiesReaderController = new MoviesReaderController(this, searchString);

        // Show all countries from server:
        candiesReaderController.readAllCountries();
    }*/
}
