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

            //title
            String name = jsonObject.getString("original_title");

            //body
            String body = jsonObject.getString("overview");

            //url
            String url = jsonObject.getString("poster_path");

            Intent i = new Intent(activity, MovieDetailsActivity.class);
            i.putExtra("state", "add_from_api");
            i.putExtra("sub", name);
            i.putExtra("body", body);
            i.putExtra("url", "https://image.tmdb.org/t/p/w185_and_h278_bestv2/" + url);
            activity.startActivity(i);
        }
        catch (JSONException ex) {
            Toast.makeText(activity, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Dismiss dialog:
        progressDialog.dismiss();
    }

}