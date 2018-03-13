package com.saw.android.lmdb;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by Android on 3/13/2018.
 */

// Class for reading server data:
public class MovieController_Search extends MovieController {

    // ctor:
    public MovieController_Search(Activity activity) {
        super(activity);
    }

    // Read all countries from the server:
    public void readAllCountries() {
        HttpRequest httpRequest = new HttpRequest(this);
        httpRequest.execute("https://api.themoviedb.org/3/movie/603?api_key=848880a3db2a7e880e5c31cfeb9464cd");
    }

    public void readAllCountries(String url) {
        HttpRequest httpRequest = new HttpRequest(this);
        httpRequest.execute(url);
    }

    public void readMovieByID(int id) {
        HttpRequest httpRequest = new HttpRequest(this);
        httpRequest.execute("https://api.themoviedb.org/3/movie/" + id + "?api_key=848880a3db2a7e880e5c31cfeb9464cd");
    }

    public void onSuccess(String downloadedText) {

        try {

            // Translate all to a JSON array:
            JSONObject jsonObject = new JSONObject(downloadedText);

            //body
            final String body = jsonObject.getString("overview");

            // Convert each candy from a JSON object into a Candy object:
            JSONObject objectGroup = jsonObject.getJSONObject("belongs_to_collection");
            String name = objectGroup.getString("name");

            Intent i = new Intent(activity, MovieDetailsActivity.class);
            i.putExtra("state", "edit");
            i.putExtra("sub", name);
            i.putExtra("body",body);
            activity.startActivity(i);
        }
        catch (JSONException ex) {
            Toast.makeText(activity, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Dismiss dialog:
        progressDialog.dismiss();
    }

    // Got all candies from the server - update all in the ListView:
   /* public void onSuccess(String downloadedText) {

        try {

            // Translate all to a JSON array:
            JSONObject jsonObject = new JSONObject(downloadedText);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));

            // Create a new array list to hold all candies:
            countries = new ArrayList<>();

            // Run on all JSON objects:
            for (int i = 0; i < jsonArray.length(); i++) {

                // Convert each candy from a JSON object into a Candy object:
                JSONObject jsonCurrentObject = jsonArray.getJSONObject(i);
                String name = jsonCurrentObject.getString("title");
                final int id = jsonCurrentObject.getInt("id");

                // Add the candy object into the candies array:
                countries.add(name);

                listViewCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //HttpRequest httpRequestNew = new HttpRequest(ac2);
                        //httpRequestNew.execute("https://api.themoviedb.org/3/movie/"+id+"?api_key=848880a3db2a7e880e5c31cfeb9464cd");
                        readAllCountries("https://api.themoviedb.org/3/movie/"+id+"?api_key=848880a3db2a7e880e5c31cfeb9464cd");
                    }
                });
            }

            // Set adapter for the ListView:
            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, countries);

            // Display all:
            listViewCountries.setAdapter(adapter);
        }
        catch (JSONException ex) {
            Toast.makeText(activity, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Dismiss dialog:
        progressDialog.dismiss();
    }*/
}