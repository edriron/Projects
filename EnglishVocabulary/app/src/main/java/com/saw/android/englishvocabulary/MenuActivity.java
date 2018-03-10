package com.saw.android.englishvocabulary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private Button btnAdd, btnDB;
    public static WordsList wordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mainLayout = findViewById(R.id.mainLayout);
        btnAdd = findViewById(R.id.btnAdd);
        btnDB = findViewById(R.id.btnDB);
        wordsList = new WordsList();

        wordsList.test();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyApp.getContext(), AddWordActivity.class);
                startActivity(i);
            }
        });

        btnDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyApp.getContext(), WordsDBActivity.class);
                startActivity(i);
            }
        });
    }



}
