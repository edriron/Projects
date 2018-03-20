package com.saw.android.lmdb.API;

import android.app.Activity;
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
public class MoviesReaderController extends MovieController implements AdapterView.OnItemClickListener{

    private String name = "";

    // ctor:
    public MoviesReaderController(Activity activity, String name) {
        super(activity);
        this.name = name;
    }

    // Read all countries from the server:
    public void readAllCountries() {
        HttpRequest httpRequest = new HttpRequest(this);
        //httpRequest.execute("https://restcountries.eu/rest/v2/all?fields=name");
        //httpRequest.execute("https://api.themoviedb.org/3/movie/343611?api_key=848880a3db2a7e880e5c31cfeb9464cd");
        httpRequest.execute("http://api.themoviedb.org/3/search/movie?%20&query="+name+"&api_key=848880a3db2a7e880e5c31cfeb9464cd");
    }

    public void readAllCountries(String url) {
        HttpRequest httpRequest = new HttpRequest(this);
        //httpRequest.execute("https://restcountries.eu/rest/v2/all?fields=name");
        //httpRequest.execute("https://api.themoviedb.org/3/movie/343611?api_key=848880a3db2a7e880e5c31cfeb9464cd");
        httpRequest.execute(url);
    }

    // Got all candies from the server - update all in the ListView:
    public void onSuccess(String downloadedText) {

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
                String title = jsonCurrentObject.getString("title");
                int id = jsonCurrentObject.getInt("id");
                SearchResult result = new SearchResult(title, id);

                // Add the candy object into the candies array:
                countries.add(result);
            }

            // Set adapter for the ListView:
            ArrayAdapter<SearchResult> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, countries);

            listViewCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    MovieController_Search controller = new MovieController_Search(activity);
                    int id = countries.get(i).getId();
                    controller.readMovieByID(id);

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}