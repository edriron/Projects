package com.saw.android.lmdb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.saw.android.lmdb.API.ReaderController;

public class HttpActivity extends AppCompatActivity {

    private ListView listViewCandies;
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

        ArrayAdapter<Searches> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, LibraryActivity.movies.getSearchesList());
        listViewCandies.setAdapter(adapter);

        // Listener to send name from the list to the server
        listViewCandies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sendSearchToServer(LibraryActivity.movies.getSearchesList().get(i).getTitle());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuItemDeleteSearches) {
            AlertDialog deleteDialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.delete_all_searches_warning))
                    .setNegativeButton(getString(R.string.no), null)
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            LibraryActivity.movies.deleteAllSearches();
                            ArrayAdapter<Searches> adapter = new ArrayAdapter<>(MyApp.getContext(), android.R.layout.simple_list_item_1, LibraryActivity.movies.getSearchesList());
                            listViewCandies.setAdapter(adapter);
                        }
                    })
                    .create();
            deleteDialog.show();
            return true;
        }
        return false;
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

        LibraryActivity.movies.addSearch(new Searches(etTitle.getText().toString()));
    }

    public void sendSearchToServer(String str) {

        // Create controllers:
        String searchString = str.replaceAll(" ", "%20"); //Replace all " " with "%20" to prevent error:400 bad-request
        controller = new ReaderController(this, 0);

        // Show all countries from server:
        controller.readByName(searchString);
    }
}
