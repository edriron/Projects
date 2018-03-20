package com.saw.android.lmdb;

import java.io.Serializable;

/**
 * Created by Ron on 09/03/2018.
 */

public class Movie implements Serializable {

    private int id;
    private String title;
    private String body;
    private String url;

    public Movie(String title, String body) {
        this.title = title;
        this.body = body;
        this.url = "http://files.softicons.com/download/tv-movie-icons/movie-icons-by-tobias-vogel/png/512x512/movie-noPlay-blank.png";
    }

    public Movie(String title, String body, String url) {
        this.title = title;
        this.body = body;
        this.url = url;
    }

    public Movie(String title,String body, String url, int id) {
        this.title = title;
        this.body = body;
        this.url = url;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.title = name;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void set(Movie m) {
        title = m.getTitle();
        body = m.getBody();
        url = m.getUrl();
    }

    public void normalizeToSave() {
        title = title.replaceAll("'", "_xx_");
        body = body.replaceAll("'", "_xx_");
        url = url.replaceAll("'", "_xx_");
    }

    public void normalizeToLoad() {
        title = title.replaceAll("_xx_", "'");
        body = body.replaceAll("_xx_", "'");
        url = url.replaceAll("_xx_", "'");
    }

    @Override
    public String toString() {
        return title;
    }

    //Adds \ before every '
    private static String normalizeString(String str) {
        if (!str.contains("'"))
            return str;

        String string = "";
        for (int i = 0; i < str.length(); i++)
            if (str.charAt(i) == '\'') {
                String first = str.substring(0, i);
                String rest = str.substring(i, str.length());
                first += "\\";
                string = first + rest;
                i++;
                str = string;
            }
        return string;
    }

    //Removes all ' from string
    public static String removeAllChars(String str) {
        if (!str.contains("'"))
            return str;

        String string = "";
        for (int i = 0; i < str.length(); i++)
            if (str.charAt(i) == '\'') {
                String first = str.substring(0, i);
                String rest = str.substring(i+1, str.length());
                string = first + rest;
                i++;
                str = string;
            }
        return string;
    }

    public Movie duplicate() {
        return new Movie(title, body, url, id);
    }
}
