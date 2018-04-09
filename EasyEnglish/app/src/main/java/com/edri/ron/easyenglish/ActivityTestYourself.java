package com.edri.ron.easyenglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edri.ron.easyenglish.Classes.MyApp;
import com.edri.ron.easyenglish.Classes.Word;
import com.edri.ron.easyenglish.Classes.WordsList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Ron on 02/04/2018.
 */

public class ActivityTestYourself extends AppCompatActivity {

    private WordsList wordsList;
    private LinearLayout testLayout;
    private TextView tvWord, tvScore;
    private TextView[] answers;
    private ProgressBar progressBar;
    private Button btnNext, btnStart;
    private int score, index;
    private ArrayList<Word> temp;

    public ActivityTestYourself() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_yourself);

        testLayout = (LinearLayout) findViewById(R.id.testLayout);
        testLayout.setVisibility(View.INVISIBLE);

        Intent i = getIntent();
        wordsList = MyApp.getWordsList();
        wordsList.reloadFromDB();

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wordsList.size() >= 8)
                    startPractice();
                else {
                    Toast toast = Toast.makeText(MyApp.getContext(),"You need at least 8 words !", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

    private void startPractice() {

        btnStart.setVisibility(View.INVISIBLE);
        testLayout.setVisibility(View.VISIBLE);

        tvWord = findViewById(R.id.textViewWord);
        tvScore = findViewById(R.id.tvScore);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(onClick_Next);
        progressBar = findViewById(R.id.progressBar);
        score = 0;
        index = -1;

        answers = new TextView[4];
        answers[0] = findViewById(R.id.tvOption1);
        answers[1] = findViewById(R.id.tvOption2);
        answers[2] = findViewById(R.id.tvOption3);
        answers[3] = findViewById(R.id.tvOption4);

        temp = wordsList.duplicate();
        Collections.shuffle(temp);

        progressBar.setMax(temp.size());
        progressBar.setProgress(0);

        btnNext.callOnClick();
        btnNext.setEnabled(false);
    }

    private View.OnClickListener onClick_Next = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressBar.setProgress(++index);
            if(index >= temp.size()) {
                finishTest();
                return;
            }

            // Points at the current word
            Word current = temp.get(index);
            tvWord.setText(current.getName());

            // Gets array list of 4 random answers
            ArrayList<Word> randoms = get4Randoms(temp, current);

            // Renames all text view answers to the current random answers
            for(int i = 0; i < 4; i++) {
                answers[i].setText(randoms.get(i).getTrans());
                final int index = i;
                final String answer = current.getTrans();
                answers[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!btnNext.isEnabled()) {
                            if (answers[index].getText().toString().equals(answer)) {
                                Toast.makeText(MyApp.getContext(), "Current", Toast.LENGTH_SHORT).show();
                                tvScore.setText("Score: " + (++score) );
                            }
                            else
                                Toast.makeText(MyApp.getContext(), "Incurrect - " + answer, Toast.LENGTH_SHORT).show();
                            btnNext.setEnabled(true);
                        }
                    }
                });
            }

            btnNext.setEnabled(false);
        }
    };


    /*public void onClick_Next(View view) {
        progressBar.setProgress(++index);
        if(index >= temp.size()) {
            finishTest();
            return;
        }

        // Points at the current word
        Word current = temp.get(index);
        tvWord.setText(current.getName());

        // Gets array list of 4 random answers
        ArrayList<Word> randoms = get4Randoms(temp, current);

        // Renames all text view answers to the current random answers
        for(int i = 0; i < 4; i++) {
            answers[i].setText(randoms.get(i).getTrans());
            final int index = i;
            final String answer = current.getTrans();
            answers[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!btnNext.isEnabled()) {
                        if (answers[index].getText().toString().equals(answer)) {
                            Toast.makeText(MyApp.getContext(), "Current", Toast.LENGTH_SHORT).show();
                            tvScore.setText("Score: " + (++score) );
                        }
                        else
                            Toast.makeText(MyApp.getContext(), "Incurrect - " + answer, Toast.LENGTH_SHORT).show();
                        btnNext.setEnabled(true);
                    }
                }
            });
        }

        btnNext.setEnabled(false);
    }*/

    private void finishTest() {
        Toast.makeText(MyApp.getContext(), "End", Toast.LENGTH_SHORT).show();
        testLayout.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        btnStart.setText("Start Over");
    }

    //	Get answer by specific word out of array list
    public String getAns(ArrayList<Word> list, String word) {
        for(Word current : list)
            if(current.getName().equals(word))
                return current.getTrans();
        return null;
    }

    //	Get array list with 4 random quiz words including the current word
    public ArrayList<Word> get4Randoms(ArrayList<Word> list, Word current) {
        ArrayList<Word> arr = new ArrayList<>();
        arr.add(current);
        Random r = new Random();
        for(int i = 0; i < 3; i++) {
            Word randomWord = list.get(r.nextInt(list.size()));
            while(arr.contains(randomWord))
                randomWord = list.get(r.nextInt(list.size()));
            arr.add(randomWord);
        }
        Collections.shuffle(arr);
        return arr;
    }
}
