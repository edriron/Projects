package com.saw.android.foodstore;

import android.app.Application;
import android.content.Context;

/**
 * Created by Android on 3/5/2018.
 */

public class MyApp extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}