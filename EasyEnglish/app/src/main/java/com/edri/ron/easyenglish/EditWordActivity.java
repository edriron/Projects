package com.edri.ron.easyenglish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edri.ron.easyenglish.API.ReaderController;
import com.edri.ron.easyenglish.API.ReaderDescController;
import com.edri.ron.easyenglish.Classes.MyApp;
import com.edri.ron.easyenglish.Classes.Word;
import com.edri.ron.easyenglish.Classes.WordsList;

import java.text.Format;

public class EditWordActivity extends AppCompatActivity {

    private EditText etName, etTrans, etDesc, etExample;
    private Button btnOk, btnCancel;
    private WordsList wordsList;
    private Word word;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_word);

        etName = findViewById(R.id.editTextWord);
        etTrans = findViewById(R.id.editTextTrans);
        etDesc = findViewById(R.id.editTextDescription);
        etExample = findViewById(R.id.editTextExample);
        radioGroup = findViewById(R.id.radioGroup);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setVisibility(View.VISIBLE);

        Intent intent = getIntent();

        wordsList = MyApp.getWordsList();
        word = (Word) intent.getSerializableExtra("word");


        etName.setText(word.getName());
        etTrans.setText(word.getTrans());
        etDesc.setText(word.getDescription());
        etExample.setText(word.getExample());
        btnOk.setText("Save");
        TextView mainTitle = (TextView) findViewById(R.id.textViewTitle);
        mainTitle.setText("Edit a word");
        switch (word.getGroupType()) {
            case Adjective:
                radioGroup.check(R.id.radioAdjective);
                break;
            case Verb:
                radioGroup.check(R.id.radioVerb);
                break;
            default:
                radioGroup.check(R.id.radioNoun);
        }
        etName.requestFocus();
        etName.setSelection(etName.getText().toString().length());

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

                int radio = radioGroup.getCheckedRadioButtonId();
                Word newWord = new Word(etName.getText().toString(), etTrans.getText().toString(), etDesc.getText().toString(), etExample.getText().toString(), Word.getGroupType(radio));
                newWord.setId(word.getId());
                wordsList.updateWord(word, newWord);
                Intent intent = new Intent();
                intent.putExtra("newWordName", newWord.getName());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_word, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.app_bar_desc) {

            // Create controllers:
            ReaderDescController controller = new ReaderDescController(this);

            // Show all countries from server:
            controller.readByName(etName.getText().toString());
        }
        else if(item.getItemId() == R.id.app_bar_info) {
            String text = String.format("Currects: %d. Mistakes: %d. Rating: %d", word.getCurrects(), word.getMistakes(), word.getRating());
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
