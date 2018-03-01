package com.saw.android.myapplication;

import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TrafficLights extends AppCompatActivity {

    private Button walkerGreen, walkerRed, veRed, veGreen, btnWalkers, btnVehicles, walkerOrange, veOrange;
    public int DURATION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trafficlights);

        walkerGreen = (Button) findViewById(R.id.walkerGreen);
        walkerRed = (Button) findViewById(R.id.walkerRed);
        walkerOrange = (Button) findViewById(R.id.walkerOrange);
        veOrange = (Button) findViewById(R.id.veOrange);
        veGreen = (Button) findViewById(R.id.veGreen);
        veRed = (Button) findViewById(R.id.veRed);
        btnVehicles = (Button) findViewById(R.id.btnVehicles);
        btnWalkers = (Button) findViewById(R.id.btnWalkers);

        TransitionDrawable transitionDrawable = (TransitionDrawable) veRed.getBackground();
        transitionDrawable.startTransition(0);
        veRed.setTag(false);
        btnVehicles.setEnabled(false);

        transitionDrawable = (TransitionDrawable) walkerGreen.getBackground();
        transitionDrawable.startTransition(0);
        walkerGreen.setTag(true);


        transitionDrawable = (TransitionDrawable) walkerOrange.getBackground();
        transitionDrawable.startTransition(0);
        transitionDrawable = (TransitionDrawable) veOrange.getBackground();
        transitionDrawable.startTransition(0);
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnVehicles:
                switchWalkersLights();
                switchDriversLights();

                btnVehicles.setEnabled(false);
                btnWalkers.setEnabled(true);
                break;

            case R.id.btnWalkers:
                switchDriversLights();
                switchWalkersLights();

                btnVehicles.setEnabled(true);
                btnWalkers.setEnabled(false);
                break;

        }
    }


    public void switchWalkersLights() {
        boolean state = Boolean.parseBoolean(walkerRed.getTag().toString());
        if(state) { //if red light is on

            TransitionDrawable transitionDrawable = (TransitionDrawable) walkerOrange.getBackground();
            transitionDrawable.reverseTransition(100); //switch orange on

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        TransitionDrawable transitionDrawable = (TransitionDrawable) walkerRed.getBackground();
                        transitionDrawable.startTransition(20);
                        transitionDrawable = (TransitionDrawable) walkerOrange.getBackground();
                        transitionDrawable.startTransition(20);
                        transitionDrawable = (TransitionDrawable) walkerGreen.getBackground();
                        transitionDrawable.reverseTransition(DURATION);
                    } catch (Exception e) { }
                }
            });

            walkerRed.setTag(false);

        }
        else { //if red light is off
            TransitionDrawable transitionDrawable = (TransitionDrawable) walkerGreen.getBackground();
            transitionDrawable.startTransition(DURATION); //switch red on
            transitionDrawable = (TransitionDrawable) walkerOrange.getBackground();
            transitionDrawable.reverseTransition(DURATION); //switch green off

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        TransitionDrawable transitionDrawable = (TransitionDrawable) walkerGreen.getBackground();
                        transitionDrawable.startTransition(DURATION);
                        transitionDrawable = (TransitionDrawable) walkerOrange.getBackground();
                        transitionDrawable.startTransition(DURATION);
                        transitionDrawable = (TransitionDrawable) walkerRed.getBackground();
                        transitionDrawable.reverseTransition(DURATION);
                    } catch (Exception e) { }
                }
            });

            walkerRed.setTag(true);
        }
    }

    public void switchDriversLights() {
        boolean state = Boolean.parseBoolean(veRed.getTag().toString());
        if(state) { //if red light is on

            TransitionDrawable transitionDrawable = (TransitionDrawable) veOrange.getBackground();
            transitionDrawable.reverseTransition(100); //switch orange on

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        TransitionDrawable transitionDrawable = (TransitionDrawable) veRed.getBackground();
                        transitionDrawable.startTransition(20);
                        transitionDrawable = (TransitionDrawable) veOrange.getBackground();
                        transitionDrawable.startTransition(20);
                        transitionDrawable = (TransitionDrawable) veGreen.getBackground();
                        transitionDrawable.reverseTransition(DURATION);
                    } catch (Exception e) { }
                }
            });

            veRed.setTag(false);

        }
        else { //if red light is off
            TransitionDrawable transitionDrawable = (TransitionDrawable) veGreen.getBackground();
            transitionDrawable.startTransition(DURATION); //switch red on
            transitionDrawable = (TransitionDrawable) veOrange.getBackground();
            transitionDrawable.reverseTransition(DURATION); //switch green off

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        TransitionDrawable transitionDrawable = (TransitionDrawable) veGreen.getBackground();
                        transitionDrawable.startTransition(DURATION);
                        transitionDrawable = (TransitionDrawable) veOrange.getBackground();
                        transitionDrawable.startTransition(DURATION);
                        transitionDrawable = (TransitionDrawable) veRed.getBackground();
                        transitionDrawable.reverseTransition(DURATION);
                    } catch (Exception e) { }
                }
            });

            veRed.setTag(true);
        }
    }

    /*public void switchDriversLights() {
        boolean state = Boolean.parseBoolean(veRed.getTag().toString());
        if(state) { //if red light is on
            TransitionDrawable transitionDrawable = (TransitionDrawable) veRed.getBackground();
            transitionDrawable.startTransition(DURATION); //switch red off
            transitionDrawable = (TransitionDrawable) veGreen.getBackground();
            transitionDrawable.reverseTransition(DURATION); //switch green on
            veRed.setTag(false);
        }
        else { //if red light is off
            TransitionDrawable transitionDrawable = (TransitionDrawable) veRed.getBackground();
            transitionDrawable.reverseTransition(DURATION); //switch red on
            transitionDrawable = (TransitionDrawable) veGreen.getBackground();
            transitionDrawable.startTransition(DURATION); //switch green off
            veRed.setTag(true);
        }
    }*/
}
