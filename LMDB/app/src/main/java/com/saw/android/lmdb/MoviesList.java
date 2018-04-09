package com.saw.android.lmdb;

import java.util.ArrayList;

/**
 * Created by Android on 3/11/2018.
 */

public class MoviesList {
    private ArrayList<Movie> list;
    private ArrayList<Searches> searches;
    private Database db;

    public MoviesList() {
        list = new ArrayList<>();
        db = new Database();
        list = db.getAllMovies();
        searches = db.getAllSearches();
    }

    public ArrayList<Movie> getMoviesList() {
        return list;
    }

    public ArrayList<Searches> getSearchesList() {
        return searches;
    }

    public void addMovie(Movie movie) {
        if(containsMovie(movie))
            return;

        list.add(movie);
        movie.normalizeToSave();
        db.addMovie(movie);
        movie.normalizeToLoad();
    }

    public void addSearch(Searches search) {
        if(containsSearch(search))
            return;

        searches.add(search);
        db.addSearch(search);
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

    public boolean containsMovie(Movie movie) {
        for(Movie m : list)
            if(m.getTitle().equals(movie.getTitle()))
                return true;
        return false;
    }

    public boolean containsSearch(Searches search) {
        for(Searches s : searches)
            if(s.getTitle().equals(search.getTitle()))
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

    public void deleteAllSearches() {
        for(Searches search : searches)
            db.deleteSearch(search);
        searches = new ArrayList<>();
    }
}
