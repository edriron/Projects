package com.saw.android.lmdb;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private Button btnLibrary, btnTest, btnTestDB;
    private int orientation = Configuration.ORIENTATION_PORTRAIT;

    public static MoviesList movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        movies = new MoviesList();

        //Handle tablets
        mainLayout = findViewById(R.id.mainLayout);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            orientation = Configuration.ORIENTATION_LANDSCAPE;
            Drawable d = Drawable.createFromStream(getResources().openRawResource(R.raw.dark_bg), null);
            mainLayout.setBackground(d);
        }

        //Dynamically adjust views to screen
        btnLibrary = findViewById(R.id.tnLibrary);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = (int) (getResources().getDimension(R.dimen.topMarginTitle)*5
                / getResources().getDisplayMetrics().density);
        params.width = (int) (getResources().getDisplayMetrics().widthPixels / 1.25);
        btnLibrary.setLayoutParams(params);

        //Start library activity
        btnLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyApp.getContext(), LibraryActivity.class);
                startActivity(i);
            }
        });

        //Start online search activity
        btnTest = findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyApp.getContext(), HttpActivity.class);
                startActivity(i);
            }
        });

        //Start Show movies database activity
        btnTestDB = findViewById(R.id.btnTestDB);
        btnTestDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyApp.getContext(), DatabaseShowActivity.class);
                startActivity(i);
            }
        });
    }
}
