package com.saw.android.lmdb;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity {

    private MenuItem optionA, optionB;
    private GridLayout gridMovies;
    private ArrayList<Movie> movies;
    public final int NEW_MOVIE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        gridMovies = (GridLayout) findViewById(R.id.gridMovies);
        movies = new ArrayList<Movie>();

        fillMoviesByDB();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        // Need to find menu items only here because they belong to the menu and not to the main layout.
        // Also, they are not created yet on the onCreate event:
        optionA = menu.findItem(R.id.menuItem1);
        optionB = menu.findItem(R.id.menuItem2);

        return true;
    }

    // Return true to state that the menu event has been handled.
    // Return false to state that the menu event has not been handled.
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menuItemAdd:
                Toast.makeText(this, "Add", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, MovieDetailsActivity.class);
                startActivityForResult(i, NEW_MOVIE);
                return true;
            case R.id.menuItemFavorites:
                Toast.makeText(this, "Favorites", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuItem1:
                Toast.makeText(this, "Item 1", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuItem2:
                Toast.makeText(this, "Item 2", Toast.LENGTH_LONG).show();
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == NEW_MOVIE) {
            if(resultCode == Activity.RESULT_OK){
                String subject = data.getStringExtra("subject");
                String body = data.getStringExtra("body");
                addMovie(subject, body);
            }
        }
    }

    private void addMovie(String sub, final String body) {
        Movie b = new Movie(this, sub, body);
        b.setText(sub);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        int width = getResources().getDisplayMetrics().widthPixels;
        params.width = (int) (width * 0.2);
        params.height = (int) (getResources().getDisplayMetrics().heightPixels * 0.15);
        width = (int) ( (width * 0.2) / 8);
        params.setMargins(width, width, width, width);
        params.gravity = Gravity.CENTER;
        b.setLayoutParams(params);
        gridMovies.addView(b);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyApp.getContext(), body, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillMoviesByDB() {
        // TODO: 09/03/2018 check with db for movies to present
    }
}
