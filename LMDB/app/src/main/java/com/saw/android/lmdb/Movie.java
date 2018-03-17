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

    public Movie(Context c, String name,String body, int id) {
        super(c);
        this.name = name;
        this.body = body;
        this.id = id;
    }

    public Movie(String name, String body) {
        super(null);
        this.name = name;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(String name) {
        this.body = body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void set(Movie m) {
        name = m.getName();
        body = m.getBody();
    }
}
