package com.saw.android.lmdb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private LinearLayout linearLayout;
    public static final int NEW_MOVIE = 1;
    public static final int EDIT_MOVIE = 2;
    public static MoviesList movies;
    private DisplayMetrics metrics;
    public static ArrayList<String> searches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        scrollView = findViewById(R.id.scrollView);
        linearLayout = findViewById(R.id.scrollLinearLayout);
        metrics = getResources().getDisplayMetrics();

        searches = new ArrayList<>();
        movies = new MoviesList();

        fillMoviesByDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.menuItemAdd:
                Intent i = new Intent(this, MovieDetailsActivity.class);
                i.putExtra("state", "add");
                startActivityForResult(i, NEW_MOVIE);
                return true;

            case R.id.menuItemAddApi:
                i = new Intent(this, HttpActivity.class);
                //i.putExtra("state", "add_from_api");
                startActivityForResult(i, NEW_MOVIE);
                return true;

            case R.id.menuItemFavorites:

                return true;

            case R.id.menuItemExit:

                return true;

            case R.id.menuItemDeleteAll:
                AlertDialog deleteDialog = new AlertDialog.Builder(this)
                        .setTitle("Are you sure you want to delete ALL movies?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                movies.deleteAllMovies();
                                fillMoviesByDB();
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

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == NEW_MOVIE) {
                Movie movie = (Movie)data.getSerializableExtra("movie");
                movies.addMovie(movie);
                fillMoviesByDB();
            }
            else if(requestCode == EDIT_MOVIE) {
                Movie oldMovie = (Movie)data.getSerializableExtra("oldMovie");
                Movie newMovie = (Movie)data.getSerializableExtra("newMovie");
                movies.updateMovie(oldMovie, newMovie);
                fillMoviesByDB();
            }
        }
    }

    private void fillMoviesByDB() {
        ArrayList<Movie> array = movies.getList();

        linearLayout.removeAllViews();
        for(final Movie movie : array) {
            MovieThumbnail thumbnail = new MovieThumbnail(this, movie, this);
            thumbnail.addViews(metrics.heightPixels);
            thumbnail.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showActionDialog(movie);
                    return true;

                }
            });
            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendMovieToEditActivity(movie);
                }
            });
            linearLayout.addView(thumbnail);
        }
    }

    private void sendMovieToEditActivity(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("state", "edit");
        intent.putExtra("movie", movie);
        startActivityForResult(intent, EDIT_MOVIE);
    }

    private void showActionDialog(final Movie movie) {
        final Context c = this;
        AlertDialog dialog = new AlertDialog.Builder(this)
                //.setIcon(R.drawable.delete)
                .setTitle("Action alert")
                .setMessage("Choose an option for:\n" + movie.getTitle())
                .setCancelable(false)
                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendMovieToEditActivity(movie);
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showDeleteDialog(movie);
                    }
                })
                .setNeutralButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void showDeleteDialog(final Movie movie) {
        AlertDialog deleteDialog = new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to delete?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        movies.deleteWord(movie);
                        fillMoviesByDB();
                    }
                })
                .create();
        deleteDialog.show();
    }
}
