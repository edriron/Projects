package com.saw.android.lmdb;

/**
 * Created by Ron on 18/03/2018.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database() {
        super(MyApp.getContext(), "MoviesDatabase", null, 1); // MoviesDatabase = Database name, 1 = Database Version.
    }

    // Will be invoked if database isn't present:
    public void onCreate(SQLiteDatabase db) {
        // Note: the identity column must be called _id for using a CursorAdapter.
        // In this example we don't use CursorAdapter (thus it can also be called "id"), but in the next example we do.
        // Thus the identity column is called _id - so we could continue this example to the next one with the CursorAdapter without changing the identity column name.
        db.execSQL("CREATE TABLE Movies(_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, body TEXT NOT NULL, url TEXT NOT NULL)");
        // execSQL Execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data.

        db.execSQL("CREATE TABLE Searches(_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL)");
    }

    // Will be invoked when database version will be different (like in an update app version):
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Note: Android will save (in some cases) and won't delete the old version from the device, even if we'll uninstall the app!
        // Thus it is important in the onUpgrade to delete the previous tables and to create them again, or the old versions will still be in use.
        db.execSQL("DROP TABLE Movies");
        db.execSQL("DROP TABLE Searches");
        onCreate(db);
    }

    // Add a new movie:
    public void addMovie(Movie movie) {
        String sql = String.format("INSERT INTO Movies(title,body,url) VALUES('%s','%s','%s')", movie.getTitle(), movie.getBody(), movie.getUrl());
        SQLiteDatabase db = getWritableDatabase(); // Open connection.
        db.execSQL(sql);
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        cursor.moveToNext();
        int id = cursor.getInt(0);
        movie.setId(id);
        cursor.close();
        db.close(); // Close connection.
    }

    public void addSearch(Searches search) {
        String sql = String.format("INSERT INTO Searches(title) VALUES('%s')", search.getTitle());
        SQLiteDatabase db = getWritableDatabase(); // Open connection.
        db.execSQL(sql);
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        cursor.moveToNext();
        int id = cursor.getInt(0);
        search.setId(id);
        cursor.close();
        db.close(); // Close connection.
    }

    // Update an existing movie:
    public void updateMovie(Movie movie) {
        String sql = String.format("UPDATE Movies SET title='%s',body='%s',url='%s' WHERE _id=%d", movie.getTitle(), movie.getBody(), movie.getUrl(), movie.getId());
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public void updateSearch(Searches search) {
        String sql = String.format("UPDATE Searches SET title='%s' WHERE _id=%d", search.getTitle(), search.getId());
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    // Delete an existing product:
    public void deleteProduct(Movie movie) {
        String sql = String.format("DELETE FROM Movies WHERE _id=%d", movie.getId());
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public void deleteSearch(Searches search) {
        String sql = String.format("DELETE FROM Searches WHERE _id=%d", search.getId());
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    // Get all products:
    public ArrayList<Movie> getAllMovies() {

        ArrayList<Movie> movies = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Movies", null);

        // Take indices of all columns:
        int idIndex = cursor.getColumnIndex("_id");
        int titleIndex = cursor.getColumnIndex("title");
        int bodyIndex = cursor.getColumnIndex("body");
        int urlIndex = cursor.getColumnIndex("url");

        // Run on all rows, create product from each row:
        while (cursor.moveToNext()) {
            int id = cursor.getInt(idIndex);
            String title = cursor.getString(titleIndex);
            String body = cursor.getString(bodyIndex);
            String url = cursor.getString(urlIndex);
            Movie movie = new Movie(title, body, url, id);
            movie.normalizeToLoad();
            movies.add(movie);
        }

        cursor.close();
        db.close();

        return movies;
    }

    public ArrayList<Searches> getAllSearches() {

        ArrayList<Searches> searches = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Searches", null);

        // Take indices of all columns:
        int idIndex = cursor.getColumnIndex("_id");
        int titleIndex = cursor.getColumnIndex("title");

        // Run on all rows, create product from each row:
        while (cursor.moveToNext()) {
            int id = cursor.getInt(idIndex);
            String title = cursor.getString(titleIndex);
            Searches search = new Searches(title, id);
            searches.add(search);
        }

        cursor.close();
        db.close();

        return searches;
    }
}