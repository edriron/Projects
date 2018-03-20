package com.saw.android.englishvocabulary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddWordActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText etName, etTrans;
    private Button btnOk, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        spinner = findViewById(R.id.spinner);
        etName = findViewById(R.id.editTextWord);
        etTrans = findViewById(R.id.editTextTrans);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);

        final Intent intent = getIntent();
        if(intent.hasExtra("word")) {
            final Word word = (Word) intent.getSerializableExtra("word");
            etName.setText(word.getName());
            etTrans.setText(word.getTrans());
            spinner.setSelection(word.getTypeByInt());
            btnOk.setText("Save");

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Word newWord = new Word(etName.getText().toString(), etTrans.getText().toString(), Word.getGroupType(spinner));
                    newWord.setId(word.getId());
                    MenuActivity.wordsList.updateWord(word, newWord);
                    intent.putExtra("newWordName", newWord.getName());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });
        }
        else {
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(etName.getText().length() == 0) {
                        Toast.makeText(MyApp.getContext(), "Must type a word !", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(etTrans.getText().length() == 0) {
                        Toast.makeText(MyApp.getContext(), "Must type a translation !", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(spinner.getSelectedItem().toString().equals("Choose a group type:")) {
                        Toast.makeText(MyApp.getContext(), "Must choose a group !", Toast.LENGTH_LONG).show();
                        return;
                    }

                    addWord(etName.getText().toString(), etTrans.getText().toString(), Word.getGroupType(spinner));

                    Toast.makeText(MyApp.getContext(), etName.getText().toString() + " was added !", Toast.LENGTH_LONG).show();
                    etName.getText().clear();
                    etTrans.getText().clear();
                    spinner.setSelection(0);
                    etName.requestFocus();
                }
            });
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void addWord(String name, String trans, GroupType type) {
        Word word = new Word(name, trans, type);
        MenuActivity.wordsList.addWord(word);
    }
}
