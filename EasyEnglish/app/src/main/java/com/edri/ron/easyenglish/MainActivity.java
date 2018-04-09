package com.edri.ron.easyenglish;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.edri.ron.easyenglish.Classes.MyApp;
import com.edri.ron.easyenglish.Classes.Word;
import com.edri.ron.easyenglish.Classes.WordsList;

public class MainActivity extends AppCompatActivity {

    public Fragment fragment;
    public WordsList wordsList;
    public static BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if(item.getItemId() == navigation.getSelectedItemId())
                return true;

            switch (item.getItemId()) {
                case R.id.navigation_new_word:
                    fragment = new FragmentAddWord();
                    break;
                case R.id.navigation_my_words:
                    fragment = new FragmentMyWords();
                    break;
                default:
                    fragment = new FragmentDictionary();
                    break;
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("wordsList", wordsList);
            fragment.setArguments(bundle);

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame, fragment);
            transaction.commit();

            return true;
        }
    };

    //delete specific word
    public void yes(Word word) {
        wordsList.deleteWord(word);
        Toast.makeText(MyApp.getContext(),word.getName() + " was deleted !", Toast.LENGTH_LONG).show();

        if(fragment instanceof FragmentMyWords) {
            ((FragmentMyWords)fragment).loadListView(((FragmentMyWords) fragment).sort_None);
        }
    }

    //delete all words
    public void yes() {
        wordsList.deleteAllWords();
        Toast.makeText(MyApp.getContext(),"All words were deleted !", Toast.LENGTH_LONG).show();

        if(fragment instanceof FragmentMyWords) {
            ((FragmentMyWords)fragment).loadListView(((FragmentMyWords) fragment).sort_None);
        }
    }

    public void no() {
        //Toast.makeText(MyApp.getContext(), "You clicked no.", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordsList = new WordsList();
        MyApp.setWordsList(wordsList);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState == null) {
            fragment = new FragmentAddWord();
            Bundle bundle = new Bundle();
            bundle.putSerializable("wordsList", wordsList);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment).commit();
        }
    }
}
