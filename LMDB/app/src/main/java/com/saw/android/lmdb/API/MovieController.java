package com.saw.android.lmdb.API;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.ListView;
import android.widget.Toast;

import com.saw.android.lmdb.R;

import java.util.ArrayList;

/**
 * Created by Android on 3/13/2018.
 */

public abstract class MovieController  implements HttpRequest.Callbacks {

    protected static ArrayList<SearchResult> searchResults; // All search results.
    protected Activity activity; // The main activity.
    protected ProgressDialog progressDialog; // Progress dialog.
    protected ListView listViewCountries; // The main ListView for updating the countries list.

    // ctor:
    public MovieController(Activity activity) {
        this.activity = activity;
        listViewCountries = activity.findViewById(R.id.listViewMovies);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Downloading...");
        progressDialog.setMessage("Please Wait...");
    }

    // Server access is about to start - show progress dialog:
    public void onAboutToStart() {
        progressDialog.show();
    }

    // Got error from server - show toast and dismiss dialog:
    public void onError(String errorMessage) {
        Toast.makeText(activity, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }
}