package com.saw.android.lmdb;

import java.util.ArrayList;

/**
 * Created by Android on 3/11/2018.
 */

public class MoviesList {
    private ArrayList<Movie> list;
    private Database db;

    public MoviesList() {
        list = new ArrayList<>();
        db = new Database();
        list = db.getAllMovies();
    }

    public ArrayList<Movie> getList() {
        return list;
    }

    public void addMovie(Movie movie) {
        if(contains(movie))
            return;

        list.add(movie);
        movie.normalizeToSave();
        db.addMovie(movie);
        movie.normalizeToLoad();
    }

    public void updateMovie(Movie oldMovie, Movie newMovie) {
        for (int i = 0; i < list.size(); i++) {
            Movie currentMovie = list.get(i);
            if (currentMovie.getTitle().equals(oldMovie.getTitle())) {
                currentMovie.set(newMovie);
                newMovie.normalizeToSave();
                db.updateMovie(newMovie);
                newMovie.normalizeToLoad();
                return;
            }
        }
    }

    public boolean contains(Movie movie) {
        for(Movie m : list)
            if(m.getTitle().equals(movie.getTitle()))
                return true;
        return false;
    }

    public void deleteWord(Movie movie) {
        for (int i = 0; i < list.size(); i++) {
            Movie currentMovie = list.get(i);
            if (currentMovie.getTitle().equals(movie.getTitle())) {
                db.deleteProduct(movie);
                list.remove(i);
                return;
            }
        }
    }

    public void deleteAllMovies() {
        for(Movie movie : list)
            db.deleteProduct(movie);
        list = new ArrayList<>();
    }
}
