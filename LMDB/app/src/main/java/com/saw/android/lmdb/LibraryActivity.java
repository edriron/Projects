package com.saw.android.lmdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class LibraryActivity extends AppCompatActivity {

    private MenuItem optionA, optionB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        // Need to find menu items only here because they belong to the menu and not to the main layout.
        // Also, they are not created yet on the onCreate event:
        optionA = menu.findItem(R.id.menuItem1);
        optionB = menu.findItem(R.id.menuItem2);

        return true;
    }

    // Return true to state that the menu event has been handled.
    // Return false to state that the menu event has not been handled.
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menuItemAdd:
                Toast.makeText(this, "Add", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuItemFavorites:
                Toast.makeText(this, "Favorites", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuItem1:
                Toast.makeText(this, "Item 1", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuItem2:
                Toast.makeText(this, "Item 2", Toast.LENGTH_LONG).show();
                return true;
        }
        return false;
    }
}
