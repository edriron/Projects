package com.saw.android.lmdb;

/**
 * Created by Ron on 18/03/2018.
 */

import android.content.Context;
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
    }

    // Will be invoked when database version will be different (like in an update app version):
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Note: Android will save (in some cases) and won't delete the old version from the device, even if we'll uninstall the app!
        // Thus it is important in the onUpgrade to delete the previous tables and to create them again, or the old versions will still be in use.
        db.execSQL("DROP TABLE Movies");
        onCreate(db);
    }

    // Add a new product:
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

    // Update an existing product:
    public void updateMovie(Movie movie) {
        String sql = String.format("UPDATE Movies SET title='%s',body='%s',url='%s' WHERE _id=%d", movie.getTitle(), movie.getBody(), movie.getUrl(), movie.getId());
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
        while(cursor.moveToNext()) {
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

/*
    // Save CRUD as above, but with built-in CRUD functions:

    // Add a new product:
    public void addProduct(Product product) {
        ContentValues values = new ContentValues(); // Dictionary.
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        SQLiteDatabase db = getWritableDatabase();
        int id = (int) db.insert("Products", null, values); // null = nullColumnHack = the column to enter null into if values are empty (WTF).
        product.setId(id);
        db.close();
    }

    // Update an existing product:
    public void updateProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        SQLiteDatabase db = getWritableDatabase();
        db.update("Products", values, "_id=" + product.getId(), null); // null = whereArgs = values to replace the "?" in the whereClause
        // or:
        // db.update("Products",values, "_id=?", new String[]{""+product.getId()});
        db.close();
    }

    // Delete an existing product:
    public void deleteProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Products", "_id=" + product.getId(), null); // null = whereArgs = values to replace the "?" in the whereClause
        // or:
        // db.delete("Products", "_id=?", new String[]{"" + product.getId()});
        db.close();
    }

    // Get all products:
    public ArrayList<Product> getAllProducts() {

        ArrayList<Product> products = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Products", null, null, null, null, null, null); // null = don't bother...

        // Take indices of all columns:
        int idIndex = cursor.getColumnIndex("_id");
        int nameIndex = cursor.getColumnIndex("name");
        int priceIndex = cursor.getColumnIndex("price");

        // Run on all rows, create product from each row:
        while (cursor.moveToNext()) {
            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            double price = cursor.getDouble(priceIndex);
            Product product = new Product(id, name, price);
            products.add(product);
        }

        cursor.close();
        db.close();

        return products;
    }
*/
}

/* To view the content of the SQLite Database inside the emulator:

1. Open Command-Line in [SDK Folder]\platform-tools where [SDK Folder] is the location of the SKD.
e.g: C:\Users\Owner\AppData\Local\Android\sdk\platform-tools

2. Write the following to get all open emulators device ids:
adb devices
e.g: emulator-5554

3. Write the following to enter the device:
adb -s [Device ID] shell
where [Device ID] is the emulator's device id presented in previous section.
e.g: adb -s emulator-5554 shell

4. Write after # sign the following, to enter to the database file:
sqlite3 /data/data/[Package]/databases/[DB Name]
where [Package] is the name of your main package and [DB Name] is the name of your database file.
e.g: sqlite3 /data/data/anna.karp/databases/ProductsDatabase

5. Write .help for getting help.

6. Write .tables to get the list of all tables.

7. Write .schema [Table Name] to get only that table's schema
where [Table Name] is the name of the table (the output will be the table creation statement).
e.g: .schema Products

8. Write .dump [Table Name] to get all data inside a specific table
(the output will be an INSERT statements for all table data).
e.g: .dump Products

9. Write any sql statement to show that data. Note: you must end any statement with ;
e.g:
select * from Products;
select name, price from Products; where price >= 10 and price <= 20;
insert into Products(name, price) values(Berry, 5.43);
update Products set name='Blue Berry', price=6.75 where id=2;
delete from Products where id=2;

10. Write .exit to exit the SQLite database.

11. write exit again (without dot) to exit the emulator. */