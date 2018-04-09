package com.edri.ron.easyenglish.API;

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
public class ReaderController extends DictionaryController{

    private String KEY = "trnsl.1.1.20180408T184236Z.a608f97b4824c4a2.2a866265fc4d63131ce355631d2e7da0d71122bd";

    public ReaderController(Activity activity) {
        super(activity);
    }

    // Read all movies with a name from the server:
    public void readByName(String searchName) {
        HttpRequest httpRequest = new HttpRequest(this);
        //String exe = "http://api.pearson.com/v2/dictionaries/entries?headword=" + searchName;
        String exe = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + KEY + "&text=" + searchName + "&lang=en-he";
        httpRequest.execute(exe);
    }

    // Got all candies from the server - update all in the ListView:
    public void onSuccess(String downloadedText) {

        //If request = filter movies by a name
        try {
            // Translate all to a JSON array:
            JSONObject jsonObject = new JSONObject(downloadedText);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("text"));

            String t = jsonArray.getString(0);
            if(t.matches(".*[a-z].*")) {
                Toast.makeText(activity, "Not found !", Toast.LENGTH_SHORT).show();
                btnQuickAdd.setEnabled(false);
            }
            else {
                etTrans.setText(t);
                btnQuickAdd.setEnabled(true);
                btnAdd.setEnabled(true);

                try {
                    hideKeyboard();
                } catch (Exception e) { }
            }


        } catch (JSONException ex) {
            Toast.makeText(activity, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Dismiss dialog:
        progressDialog.dismiss();
    }
}