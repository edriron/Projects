package com.saw.android.lmdb.API;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.saw.android.lmdb.Movie;
import com.saw.android.lmdb.MovieDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.util.ArrayList;

/**
 * Created by Android on 3/13/2018.
 */

// Class for reading server data:
public class ReaderController extends MovieController implements AdapterView.OnItemClickListener{

    private int type;

    public ReaderController(Activity activity, int type) {
        super(activity);
        this.type = type;
    }

    // Read all movies with a name from the server:
    public void readByName(String searchName) {
        HttpRequest httpRequest = new HttpRequest(this);
        String exe = "http://api.themoviedb.org/3/search/movie?%20&query=" + searchName + "&api_key=848880a3db2a7e880e5c31cfeb9464cd";
        httpRequest.execute(exe);
    }

    // Read a movie by specific id
    public void readMovieByID(int id) {
        HttpRequest httpRequest = new HttpRequest(this);
        String exe = "https://api.themoviedb.org/3/movie/" + id + "?api_key=848880a3db2a7e880e5c31cfeb9464cd";
        httpRequest.execute(exe);
    }

    // Got all candies from the server - update all in the ListView:
    public void onSuccess(String downloadedText) {

        //If request = filter movies by a name
        if(type == 0) {
            try {
                // Translate all to a JSON array:
                JSONObject jsonObject = new JSONObject(downloadedText);
                JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));

                // Create a new array list to hold all candies:
                searchResults = new ArrayList<>();

                // Run on all JSON objects:
                for (int i = 0; i < jsonArray.length(); i++) {

                    // Convert each candy from a JSON object into a Candy object:
                    JSONObject jsonCurrentObject = jsonArray.getJSONObject(i);
                    String title = jsonCurrentObject.getString("title");
                    int id = jsonCurrentObject.getInt("id");
                    SearchResult result = new SearchResult(title, id);

                    // Add the candy object into the candies array:
                    searchResults.add(result);
                }

                // Set adapter for the ListView:
                ArrayAdapter<SearchResult> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, searchResults);

                listViewCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        int id = searchResults.get(i).getId();
                        type = 1;
                        readMovieByID(id);

                    }
                });

                // Display all:
                listViewCountries.setAdapter(adapter);
            }
            catch (JSONException ex) {
                Toast.makeText(activity, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            // Dismiss dialog:
            progressDialog.dismiss();
        }

        //If request = filter movies to a specific id
        else {
            try {

                // Translate all to a JSON array:
                JSONObject jsonObject = new JSONObject(downloadedText);

                //title
                String title = jsonObject.getString("original_title");

                //body
                String body = jsonObject.getString("overview");

                //url
                String url = jsonObject.getString("poster_path");

                Intent i = new Intent(activity, MovieDetailsActivity.class);
                i.putExtra("state", "add_from_api");
                Movie movie = new Movie(title, body, "https://image.tmdb.org/t/p/w185_and_h278_bestv2/" + url);
                i.putExtra("movie", movie);
                activity.startActivityForResult(i, 1);

                //HttpActivity.setMovieToEditActivity(movie);
            }
            catch (JSONException ex) {
                Toast.makeText(activity, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            // Dismiss dialog:
            progressDialog.dismiss();
        }


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}