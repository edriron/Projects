package com.saw.android.lmdb;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private Button btnOk, btnCancel, btnShow;
    private EditText etTitle, etBody, etUrl;
    private TextView tvTitle, tvDesc, tvUrl;
    private ImageView imageView;
    private static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Define views
        mainLayout = findViewById(R.id.mainLayout);

        etTitle = findViewById(R.id.etSub);
        etBody = findViewById(R.id.etBody);
        etUrl = findViewById(R.id.etURL);

        tvTitle = findViewById(R.id.tvSubject);
        tvDesc = findViewById(R.id.tvBody);
        tvUrl = findViewById(R.id.tvURL);

        btnOk = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);
        btnShow = findViewById(R.id.btnShowUrl);

        imageView = findViewById(R.id.imageView);
        activity = this;

        DisplayMetrics met = getResources().getDisplayMetrics();

        //Get intent and state [edit or add]
        Intent i = getIntent();
        final String state = i.getStringExtra("state");

        if(state.equals("edit")) {
            final Movie movie = (Movie) i.getSerializableExtra("movie");
            etTitle.setText(movie.getTitle());
            etBody.setText(movie.getBody());
            etUrl.setText(movie.getUrl());

            btnOk.setText(getString(R.string.save));

            new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                    .execute(movie.getUrl());

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    i.putExtra("oldMovie", movie);
                    Movie newMovie = new Movie(etTitle.getText().toString(), etBody.getText().toString(), etUrl.getText().toString(), movie.getId());
                    i.putExtra("newMovie", newMovie);
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }
            });
        }
        else if(state.equals("add_from_api")) {
            Movie movie = (Movie) i.getSerializableExtra("movie");
            etTitle.setText(movie.getTitle());
            etBody.setText(movie.getBody());
            etUrl.setText(movie.getUrl());

            btnOk.setText(getString(R.string.add));

            new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                    .execute(movie.getUrl());

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    Movie movie = new Movie(etTitle.getText().toString(), etBody.getText().toString(), etUrl.getText().toString());
                    i.putExtra("movie", movie);
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }
            });
        }
        else { //add
            btnOk.setText(getString(R.string.add));
            btnOk.setEnabled(false);

            Bitmap bm = BitmapFactory.decodeResource(MyApp.getContext().getResources(), R.raw.movie_blank_thumbnail);
            imageView.setImageBitmap(bm);

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    Movie movie = new Movie(etTitle.getText().toString(), etBody.getText().toString(), etUrl.getText().toString());
                    i.putExtra("movie", movie);
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

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                        .execute(etUrl.getText().toString());
            }
        });

        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etTitle.getText().length() == 0)
                    btnOk.setEnabled(false);
                else btnOk.setEnabled(true);
            }
        });

    }
}
