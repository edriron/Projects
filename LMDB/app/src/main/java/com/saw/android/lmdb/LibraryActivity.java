package com.saw.android.lmdb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
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
    public final int NEW_MOVIE = 1;
    public final int EDIT_MOVIE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        gridMovies = findViewById(R.id.gridMovies);

        fillMoviesByDB();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        optionA = menu.findItem(R.id.menuItem1);
        optionB = menu.findItem(R.id.menuItem2);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menuItemAdd:
                Toast.makeText(this, "Add", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, MovieDetailsActivity.class);
                i.putExtra("state", "add");
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

        if (requestCode == NEW_MOVIE || requestCode == EDIT_MOVIE) {
            if(resultCode == Activity.RESULT_OK){
                String subject = data.getStringExtra("sub");
                String body = data.getStringExtra("body");
                String state = data.getStringExtra("state");
                String original = data.getStringExtra("original");
                updateMovie(subject, body, state, original);
            }
        }
    }

    private void updateMovie(String sub, String body, String state, String original) {
        Movie b = new Movie(this, sub, body);

        if(state.equals("edit")) {
            int index = MenuActivity.movies.getMovieIndex(original);
                Toast.makeText(this, original + "." + state, Toast.LENGTH_LONG).show();
            MenuActivity.movies.setMovie(index, b);
        }
        else {
            MenuActivity.movies.add(b);
        }

        b.setText(sub);

        fillMoviesByDB();
    }

    private void fillMoviesByDB() {
        if(MenuActivity.movies.isEmpty())
            return;

        gridMovies.removeAllViews();
        for(Movie m : MenuActivity.movies.getList()) {
            final Movie b = new Movie(this, m.getName(), m.getBody());
            b.setText(m.getName());
            //b.setTextAppearance(this, R.style.MovieButton);
            b.setBackground(getResources().getDrawable(R.drawable.shape_menu_button));
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
                    //Toast.makeText(this, "Add", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MyApp.getContext(), MovieDetailsActivity.class);
                    i.putExtra("state", "edit");
                    i.putExtra("sub", b.getName());
                    i.putExtra("body", b.getBody());

                    startActivityForResult(i, EDIT_MOVIE);
                }
            });
        }
    }
}
