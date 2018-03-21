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
        //orderViewsByScreenTest(met.widthPixels, met.heightPixels);

        //Get intent and state [edit or add]
        Intent i = getIntent();
        final String state = i.getStringExtra("state");
        //final Movie m = new Movie(etSub.getText().toString(), etBody.getText().toString());

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

    public void orderViewsByScreenTest(int screenX, int screenY) {

        //  Array to hold all views except layouts
        ArrayList<View> viewArrayList = putAllChildInArray(mainLayout);

        //  Set padding right and left of main layout (screen)
        int p = (int)(screenX * 0.025);
        mainLayout.setPadding(p, 0, p, 0);

        int viewsLevelCountInLayout = 6; // how many views levels in the layout
        int[] defaultScreenSize = {1080, 1920}; //  default screen size (pixel 2)
        int differenceOfScreens = screenY - defaultScreenSize[1];
        int heightBonusPerView = (int)((Math.abs(differenceOfScreens) / viewsLevelCountInLayout)); //  pixels amount to add to each view's height
        if(heightBonusPerView < 10) return; //   No need to perform action - screen is similar to default

        boolean isBigger = true;
        if(differenceOfScreens < 0)
            isBigger = false;

        for(View v : viewArrayList) {
            int height = v.getLayoutParams().height; //current view's height
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.height = isBigger ? height + heightBonusPerView : height - heightBonusPerView;
            //params.height = height + heightBonusPerView;

            if(v instanceof EditText) {
                params.width = LinearLayout.LayoutParams.MATCH_PARENT;

                //  Align body text with all other edit texts
                if(v.getId() == R.id.etBody)
                    params.rightMargin = p;
                else if(v.getId() == R.id.etURL)
                    params.weight = 1;
            }
            else if(v instanceof TextView && !(v instanceof Button)) {
                params.rightMargin = p;
            }
            else if(v instanceof Button){
                params.weight = 1;
                if(v.getId() == R.id.btnShowUrl) {
                    params.weight = 0;
                    //params.weight = 1;
                }
                //params.topMargin = 200;
            }
            else if(v instanceof ImageView) {
                params.gravity = Gravity.CENTER;
            }
            v.setLayoutParams(params);
        }


    }

    public void orderViewsByScreen(int screenX, int screenY, int density) {

        ArrayList<View> viewArrayList = putAllChildInArray(mainLayout);
        int p = (int)(screenX * 0.025);
        mainLayout.setPadding(p, 0, p, 0);

        int viewsLevelCountInLayout = viewArrayList.size();    //how many views levels in the layout
        int[] defaultScreenSize = {1080, 1920};     //default screen size (pixel 2)
        int heightBonusPerView = (int)((Math.abs(screenY - defaultScreenSize[1]) / viewsLevelCountInLayout) * 0.7);    //pixels amount to add to each view's height


        for(View v : viewArrayList) {
            int height = v.getLayoutParams().height; //current view's height
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, height + heightBonusPerView);

            if(v instanceof EditText) {
                params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, height + heightBonusPerView);

                //align body text with all other edit texts
                if(v.getId() == R.id.etBody)
                    params.rightMargin = p;
            }
            else if(v instanceof TextView && !(v instanceof Button)) {
                params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, height + heightBonusPerView);
                params.rightMargin = p;
            }
            else if(v instanceof Button){
                params.weight = 1;
                if(v.getId() == R.id.btnShowUrl) {
                    params.weight = 0;
                    //params.weight = 1;
                }
                //params.topMargin = 200;
            }
            else if(v instanceof ImageView) {
                params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, height + heightBonusPerView);
                params.gravity = Gravity.CENTER;
            }
            v.setLayoutParams(params);
        }

        LinearLayout layoutWarpperButtons = findViewById(R.id.btnWrapperLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutWarpperButtons.setLayoutParams(params);

        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, etUrl.getLayoutParams().height + heightBonusPerView);
        etUrl.setLayoutParams(params);

    }

    public ArrayList<View> putAllChildInArray(LinearLayout layout) {
        ArrayList<View> array = new ArrayList<>();
        int count = layout.getChildCount();
        for(int i = 0; i < count; i++) {
            View v = layout.getChildAt(i);
            if(v instanceof LinearLayout) {
                for(int j = 0; j < ((LinearLayout) v).getChildCount(); j++) {
                    array.add(((LinearLayout) v).getChildAt(j));
                }
            }
            else array.add(v);
        }

        return array;
    }

    public int countAllChild(View view) {

        //if view is not a layout, return 0
        if(!(view instanceof LinearLayout))
            return 1;

        LinearLayout layout = (LinearLayout)view;
        int count = 1;
        for(int i = 0; i < layout.getChildCount(); i++) {
            count += countAllChild(layout.getChildAt(i));
        }
        return count;
    }

    public int getTotalChildCount(View view) {

        //if view is not a layout, return 0
        if(view instanceof TextView || view instanceof Button || view instanceof ImageView)
            return 1;

        //if layout has no children, return 0
        LinearLayout layout = (LinearLayout) view;
        if (layout.getChildCount() == 0)
            return 0;

        int count = 0;
        for(int i = 0; i < layout.getChildCount(); i++) {
            count += getTotalChildCount(layout.getChildAt(i));
        }
        return count;
    }
}
