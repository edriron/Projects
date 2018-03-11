package com.saw.android.lmdb;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MovieDetailsActivity extends AppCompatActivity {

    private Button btnOk, btnCancel;
    private EditText etSub, etBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        etSub = findViewById(R.id.etSub);
        etBody = findViewById(R.id.etBody);
        btnOk = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        final String state = i.getStringExtra("state");
        if(state.equals("edit")) {

            etSub.setText(i.getStringExtra("sub"));
            etBody.setText(i.getStringExtra("body"));
            final String original = etSub.getText().toString();
            
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    i.putExtra("state", state);
                    i.putExtra("original", original);
                    i.putExtra("sub", etSub.getText().toString());
                    i.putExtra("body", etBody.getText().toString());
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }
            });
        }
        else {
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    i.putExtra("state", state);
                    i.putExtra("sub", etSub.getText().toString());
                    i.putExtra("body", etBody.getText().toString());
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }
            });
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                setResult(Activity.RESULT_CANCELED, i);
                finish();
            }
        });
    }
}
