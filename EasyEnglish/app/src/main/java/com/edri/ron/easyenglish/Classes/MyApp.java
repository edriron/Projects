package com.edri.ron.easyenglish.Classes;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * Created by Ron on 02/04/2018.
 */

public class MyApp extends Application {

    private static Context context;
    private static WordsList wordsList;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static void setWordsList(WordsList w) {
        wordsList = w;
    }

    public static WordsList getWordsList() {
        return wordsList;
    }
}