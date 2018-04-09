package com.saw.android.lmdb;

/**
 * Created by Android on 3/21/2018.
 */

public class Searches {
    private String title;
    private int id;

    public Searches(String title) {
        this.title = title;
    }

    public Searches(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public void setId(int id) {
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
        return title;
    }
}
