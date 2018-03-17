package com.saw.android.lmdb;

/**
 * Created by Ron on 14/03/2018.
 */

public class SearchResult {
    private String title;
    private int id;

    public SearchResult(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
