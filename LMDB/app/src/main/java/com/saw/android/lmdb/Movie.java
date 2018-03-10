package com.saw.android.lmdb;

import android.content.Context;

/**
 * Created by Ron on 09/03/2018.
 */

public class Movie extends android.support.v7.widget.AppCompatButton {

    private String name;
    private String body;
    private String url;

    public Movie(Context c, String name, String body) {
        super(c);
        this.name = name;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }
}
