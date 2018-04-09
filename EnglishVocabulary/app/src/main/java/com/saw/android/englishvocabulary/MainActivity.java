package com.saw.android.englishvocabulary;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fr;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fr = new BlankFragment();
                    break;
                case R.id.navigation_dashboard:
                    fr = new SecondFragment();
                    break;
                case R.id.navigation_notifications:
                    fr = new ThirdFragment();
                    break;
                default:
                    fr = new BlankFragment();
                    break;
            }

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame, fr);
            transaction.commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState == null) {
            BlankFragment bunnyFragment = new BlankFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.frame, bunnyFragment).commit();
        }
    }

}
