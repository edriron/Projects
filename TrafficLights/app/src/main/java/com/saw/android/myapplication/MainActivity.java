package com.saw.android.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Button btnRed, btnGreen, btnBlue, btnYellow, btnRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        btnRed = (Button) findViewById(R.id.btnRed);
        btnBlue = (Button) findViewById(R.id.btnBlue);
        btnGreen = (Button) findViewById(R.id.btnGreen);
        btnYellow = (Button) findViewById(R.id.btnYellow);
        btnRandom = (Button) findViewById(R.id.btnRandom);
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnRed:
                tv.setTextColor(getResources().getColor(R.color.red));
                break;

            case R.id.btnGreen:
                tv.setTextColor(getResources().getColor(R.color.green));
                break;

            case R.id.btnBlue:
                tv.setTextColor(getResources().getColor(R.color.blue));
                break;

            case R.id.btnYellow:
                tv.setTextColor(getResources().getColor(R.color.yellow));
                break;

            case R.id.btnRandom:
                Random r = new Random();
                tv.setTextColor(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));

                String text = "<font color=#cc0029>First Color</font> <font color=#ffcc00>Second Color</font>";
                tv.setText(Html.fromHtml(text));

                break;
        }
    }
}
