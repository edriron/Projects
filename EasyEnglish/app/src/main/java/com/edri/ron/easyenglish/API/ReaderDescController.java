package com.edri.ron.easyenglish.API;

import android.app.Activity;
import android.widget.Toast;

import com.edri.ron.easyenglish.FragmentAddWord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Android on 3/13/2018.
 */

// Class for reading server data:
public class ReaderDescController extends DictionaryDescriptionController{

    public ReaderDescController(Activity activity) {
        super(activity);
    }

    // Read all movies with a name from the server:
    public void readByName(String searchName) {
        HttpRequest httpRequest = new HttpRequest(this);
        //String exe = "http://api.pearson.com/v2/dictionaries/entries?headword=" + searchName;
        String exe = "http://api.pearson.com/v2/dictionaries/laad3/entries?headword=" + searchName; //laad3 dictionary
        httpRequest.execute(exe);
    }

    // Got all candies from the server - update all in the ListView:
    public void onSuccess(String downloadedText) {

        //If request = filter movies by a name
        try {
            // Translate all to a JSON array:
            JSONObject jsonObject = new JSONObject(downloadedText);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));

            JSONObject object = jsonArray.getJSONObject(0);
            String title = object.getString("headword");
            String groupType = object.getString("part_of_speech");

            jsonArray = object.getJSONArray("senses");
            object = jsonArray.getJSONObject(0);
            String t = object.getString("definition");

            t = FragmentAddWord.regulateString(t);
            etDesc.setText(t);

            try {
                jsonArray = object.getJSONArray("examples");
                object = jsonArray.getJSONObject(0);
                t = object.getString("text");
                t = FragmentAddWord.regulateString(t);
                etExample.setText(t);
            } catch (JSONException ex) {
                if(ex.getMessage().contains("value of examples")) {
                    //no example
                }
            }

        }
        catch (JSONException ex) {
            Toast.makeText(activity, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Dismiss dialog:
        progressDialog.dismiss();
    }
}