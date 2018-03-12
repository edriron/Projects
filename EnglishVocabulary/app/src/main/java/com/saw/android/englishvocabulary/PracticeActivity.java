package com.saw.android.englishvocabulary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class PracticeActivity extends AppCompatActivity {

    private TextView tvWord;
    private TextView[] answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);



        if(MenuActivity.wordsList.isEmpty())
            return;

        String[] names = MenuActivity.wordsList.getNames();
        String[] trans = MenuActivity.wordsList.getTrans();

        Random r = new Random();
        int size = names.length;
        int random = r.nextInt(size);
        Toast.makeText(this, names[random] + " - " + trans[random], Toast.LENGTH_LONG).show();
    }


}
