package com.saw.android.lmdb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by Ron on 14/03/2018.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    ImageView bmImage;
    //protected Activity activity;
    protected ProgressDialog progressDialog; // Progress dialog.

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
        //this.activity = activity;
        //progressDialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        //progressDialog.show();
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException me) {
            //mIcon11.setImageResource(R.drawable.movie_blank_thumbnail);
            mIcon11 = BitmapFactory.decodeResource(MyApp.getContext().getResources(), R.raw.movie_blank_thumbnail);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        //progressDialog.dismiss();
    }
}