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

    public boolean isEmpty() {
        return (list.size() == 0);
    }

    public ArrayList<Movie> getList() {
        return list;
    }

    public int getMovieIndex(String name) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getTitle().equals(name))
                return i;
        return -1;
    }

    public void setMovie(int i, Movie m) {
        list.get(i).set(m);
    }

    public void addMovie(Movie movie) {
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
