package com.edri.ron.easyenglish.API;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edri.ron.easyenglish.R;

import java.util.ArrayList;

/**
 * Created by Android on 3/13/2018.
 */

public abstract class DictionaryController implements HttpRequest.Callbacks {

    protected Activity activity; // The main activity.
    protected ProgressDialog progressDialog; // Progress dialog.
    protected EditText etSearch, etTrans;
    protected Button btnQuickAdd, btnAdd;

    // ctor:
    public DictionaryController(Activity activity) {
        this.activity = activity;
        btnQuickAdd = activity.findViewById(R.id.btnQuickAdd);
        btnAdd = activity.findViewById(R.id.btnAdd);

        etSearch = (EditText) activity.findViewById(R.id.searchTitle);
        etTrans = (EditText) activity.findViewById(R.id.etTrans);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Translating...");
        progressDialog.setMessage("Please Wait...");
    }

    // Server access is about to start - show progress dialog:
    public void onAboutToStart() {
        progressDialog.show();
    }

    // Got error from server - show toast and dismiss dialog:
    public void onError(String errorMessage) {
        Toast.makeText(activity, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
        //Toast.makeText(activity, "Not found !", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}