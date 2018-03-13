package com.saw.android.lmdb;

import android.content.Context;

/**
 * Created by Ron on 09/03/2018.
 */

public class Movie extends android.support.v7.widget.AppCompatButton {

    private String name;
    private String body;
    private String url;
    private int id;

    public Movie(Context c, String name, String body) {
        super(c);
        this.name = name;
        this.body = body;
    }

    public Movie(Context c, String name, int id) {
        super(c);
        this.name = name;
        this.id = id;
        this.body = "";
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void set(Movie m) {
        name = m.getName();
        body = m.getBody();
    }
}
