package com.saw.android.lmdb;

import java.util.ArrayList;

/**
 * Created by Android on 3/11/2018.
 */

public class MoviesList {
    private ArrayList<Movie> list;

    public MoviesList() {
        list = new ArrayList<Movie>();
    }

    public boolean isEmpty() {
        return (list.size() == 0);
    }

    public int getSize() {
        return list.size();
    }

    public void add(Movie movie) {
        list.add(movie);
    }

    public ArrayList<Movie> getList() {
        return list;
    }

    public int getMovieIndex(String name) {
        for(int i = 0; i < list.size(); i++)
            if(list.get(i).getName().equals(name))
                return i;
        return -1;
    }

    public Movie getMovieIndex(int i) {
        return list.get(i);
    }

    public void setMovie(int i, Movie m) {
        list.get(i).set(m);
    }
}
