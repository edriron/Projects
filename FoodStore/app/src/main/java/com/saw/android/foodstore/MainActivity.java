package com.saw.android.foodstore;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MenuItem menuItemPizza, menuItemChips, menuItemPersonalPizza, crash;
    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.food_menu, menu);

        // Need to find menu items only here because they belong to the menu and not to the main layout.
        // Also, they are not created yet on the onCreate event:
        menuItemPizza = menu.findItem(R.id.menuItemPizza);
        menuItemChips = menu.findItem(R.id.menuItemChips);
        menuItemPersonalPizza = menu.findItem(R.id.menuItemPersonalPizza);
        crash = menu.findItem(R.id.crash);

        tvText = (TextView) findViewById(R.id.tvText);
        tvText.setTextSize(30);
        tvText.setText("");
        tvText.setTextColor(Color.BLUE);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == R.id.menuItemPizza || itemId == R.id.menuItemChips || itemId == R.id.menuItemPersonalPizza
                || itemId == R.id.crash) {
            menuItemPizza.setChecked(false);
            menuItemChips.setChecked(false);
            menuItemPersonalPizza.setChecked(false);
            item.setChecked(true);
        }

        switch (itemId) {

            case R.id.menuItemPizza:
                tvText.setText("You chose: Pizza");
                getSupportActionBar().setTitle("Pizza");
                //setRString(pl.mylib.R.class,"app_name",pl.myapp.R.string.app_name);
                return true;

            case R.id.menuItemChips:
                tvText.setText("You chose: Chips");
                getSupportActionBar().setTitle("Chips");
                return true;

            case R.id.menuItemPersonalPizza:
                tvText.setText("You chose: Personal Pizza");
                getSupportActionBar().setTitle("Personal Pizza");
                return true;

            case R.id.crash:
                int a = 0/0;
                return true;

        }

        return false;
    }
}
