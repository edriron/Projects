package com.saw.android.englishvocabulary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class PracticeActivity extends AppCompatActivity {

    private TextView tvWord, tvScore;
    private TextView[] answers;
    private Button btnNext;
    private int score;
    public boolean answeredAlready;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        if(MenuActivity.wordsList.isEmpty())
            return;

        tvWord = findViewById(R.id.textViewWord);
        tvScore = findViewById(R.id.tvScore);
        btnNext = findViewById(R.id.btnNext);
        score = 0;
        answeredAlready = false;

        answers = new TextView[4];
        answers[0] = findViewById(R.id.tvOption1);
        answers[1] = findViewById(R.id.tvOption2);
        answers[2] = findViewById(R.id.tvOption3);
        answers[3] = findViewById(R.id.tvOption4);

        final String[] names = MenuActivity.wordsList.getNames();
        final String[] trans = MenuActivity.wordsList.getTrans();

        /*ArrayList<String> list = new ArrayList<>(Arrays.asList(trans));
        Collections.shuffle(list);
        list.toArray(trans);*/

        btnNext.callOnClick();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(score > names.length) {
                    finish();
                    return;
                }

                answeredAlready = false;
                Random r = new Random();

                final int answerPos = r.nextInt(names.length);

                tvWord.setText(names[answerPos]);
                ArrayList<String> list = new ArrayList<>(Arrays.asList(trans));
                Collections.shuffle(list);

                boolean is = false;
                for(int i = 0; i < 4; i++) {
                    if(list.get(i).equals(trans[answerPos])) {
                        is = true;
                        break;
                    }
                }

                if(!is)
                    list.set(r.nextInt(4), trans[answerPos]);


                for(int i = 0; i < 4; i++) {
                    answers[i].setText(list.get(i));
                    final int pos = i;
                    answers[pos].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(answers[pos].getText().toString().equals(trans[answerPos])) {
                                Toast.makeText(MyApp.getContext(), "True", Toast.LENGTH_SHORT).show();
                                if(!answeredAlready) {
                                    answeredAlready = true;
                                    score++;
                                }
                            }
                            else {
                                Toast.makeText(MyApp.getContext(), "False", Toast.LENGTH_SHORT).show();
                                if(!answeredAlready) {
                                    answeredAlready = true;
                                    score--;
                                }
                            }
                            tvScore.setText("Score: " + score);
                        }
                    });
                }
            }
        });


    }
}
