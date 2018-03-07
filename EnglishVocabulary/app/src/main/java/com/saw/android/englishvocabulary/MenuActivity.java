package com.saw.android.englishvocabulary;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private LinearLayout.LayoutParams params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_menu);
        //getSupportActionBar().hide();

        mainLayout = ViewsCreator.createLinearLayout(Content.Match);

        Button button = new Button(this);
        button.setText("test");
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        button.setLayoutParams(params);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] a = ViewsCreator.getPixelsByPercentage(50, 50);
                Toast.makeText(MyApp.getContext(), a[0] + ", " + a[1], Toast.LENGTH_SHORT).show();
            }
        });
        mainLayout.addView(button);

        setContentView(mainLayout);
    }



}
