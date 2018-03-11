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

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean equals(Movie m) {
        return (m.getName().equals(name) && m.getBody().equals(body));
    }

    public void set(Movie m) {
        name = m.getName();
        name = m.getBody();
    }
}
