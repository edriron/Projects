package com.saw.android.lmdb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ron on 20/03/2018.
 */

public class MovieThumbnail extends LinearLayout {
    private Context context;
    private Movie movie;
    private Activity activity;

    public MovieThumbnail(Context context, Movie movie, Activity activity) {
        super(context);
        this.context = context;
        this.movie = movie;
        this.activity = activity;
    }

    public void addViews(int screenY) {
        this.setOrientation(HORIZONTAL);

        int screenRelativeHeight = screenY / 4;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 20;
        params.height = screenRelativeHeight; //300
        this.setPadding(20, 20, 8, 20);
        this.setBackground(getResources().getDrawable(R.drawable.shape_tumbnail_background, null));

        this.setLayoutParams(params);

        LinearLayout textLayout = new LinearLayout(context);
        textLayout.setOrientation(VERTICAL);
        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.weight = 1;
        textLayout.setLayoutParams(params);

        TextView title = new TextView(context);
        title.setText(movie.getTitle());
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 10;
        title.setLayoutParams(params);
        textLayout.addView(title);

        TextView body = new TextView(context);
        body.setText(movie.getBody());
        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        body.setLayoutParams(params);
        textLayout.addView(body);

        this.addView(textLayout);

        ImageView image = new ImageView(context);
        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.width = (int) (screenRelativeHeight / 1.5);
        //params.height = screenRelativeHeight;
        params.gravity = Gravity.END;
        params.leftMargin = 10;
        image.setLayoutParams(params);
        new DownloadImageTask(image).execute(movie.getUrl());
        this.addView(image);
    }

}
