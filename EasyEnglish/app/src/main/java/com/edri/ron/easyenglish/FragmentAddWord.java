package com.edri.ron.easyenglish;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edri.ron.easyenglish.Classes.FragmentSaveState;
import com.edri.ron.easyenglish.Classes.MyApp;
import com.edri.ron.easyenglish.Classes.Word;
import com.edri.ron.easyenglish.Classes.WordsList;

import java.util.Locale;

/**
 * Created by Ron on 02/04/2018.
 */

public class FragmentAddWord extends Fragment {

    private WordsList wordsList;
    private EditText etName, etTrans, etDesc, etExample;
    private Button btnOk;
    private RadioGroup radioGroup;

    public FragmentAddWord() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout linearLayoutRoot = (LinearLayout)inflater.inflate(R.layout.fragment_add_word, container, false);
        etName = (EditText)linearLayoutRoot.findViewById(R.id.editTextWord);
        etTrans = (EditText)linearLayoutRoot.findViewById(R.id.editTextTrans);
        etDesc = (EditText)linearLayoutRoot.findViewById(R.id.editTextDescription);
        etExample = (EditText)linearLayoutRoot.findViewById(R.id.editTextExample);
        btnOk = (Button)linearLayoutRoot.findViewById(R.id.btnOk);
        radioGroup = (RadioGroup)linearLayoutRoot.findViewById(R.id.radioGroup);

        TextView title = (TextView)linearLayoutRoot.findViewById(R.id.textViewTitle);
        title.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        if(bundle != null) {
            wordsList = (WordsList) bundle.getSerializable("wordsList");
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etName.getText().length() == 0) {
                    Toast.makeText(MyApp.getContext(), "Must type a word !", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!etName.getText().toString().matches(".*[a-z].*")) {
                    Toast.makeText(MyApp.getContext(), "Word not legal !", Toast.LENGTH_LONG).show();
                    return;
                }

                if(etTrans.getText().length() == 0) {
                    Toast.makeText(MyApp.getContext(), "Must type a translation !", Toast.LENGTH_LONG).show();
                    return;
                }

                etName.setText(regulateString(etName.getText().toString()));
                etExample.setText(regulateString(etExample.getText().toString()));
                etDesc.setText(regulateString(etDesc.getText().toString()));

                Word word = new Word(etName.getText().toString(), etTrans.getText().toString(), etDesc.getText().toString(), etExample.getText().toString(), Word.getGroupType(radioGroup));
                int a = wordsList.addWord(word);
                if(a == -1) {
                    //word already exists
                    Toast.makeText(MyApp.getContext(), etName.getText().toString() + " already exists !", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(MyApp.getContext(), etName.getText().toString() + " was added !", Toast.LENGTH_LONG).show();
                etName.getText().clear();
                etTrans.getText().clear();
                etDesc.getText().clear();
                etExample.getText().clear();
                radioGroup.check(R.id.radioVerb);
                etName.requestFocus();
            }
        });

        return linearLayoutRoot;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        etName.requestFocus();

        Bundle bundle = FragmentSaveState.getAddWordBundle();
        if (bundle != null) {
            //Restore the fragment's state here
            etName.setText(bundle.getString("name"));
            etTrans.setText(bundle.getString("trans"));
            etDesc.setText(bundle.getString("desc"));
            etExample.setText(bundle.getString("example"));
            radioGroup.check(bundle.getInt("group"));
            //etName.setSelection(etName.getText().toString().length());
        }
        else {
            radioGroup.check(R.id.radioVerb);
            etDesc.setText("");
            etExample.setText("");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        FragmentSaveState.setAddWordBundle(etName.getText().toString(), etTrans.getText().toString(), etDesc.getText().toString(), etExample.getText().toString(), radioGroup.getCheckedRadioButtonId());
    }

    public static String regulateString(String str) {
        if(str == null || str.equals(""))
            return "";
        String t = str.toLowerCase();
        char c = t.charAt(0);
        char newC = (char) ((int)c - 32);
        t = t.replaceFirst(c + "", newC + "");
        return t;
    }
}
