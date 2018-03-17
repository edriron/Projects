package com.saw.android.lmdb;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
    private EditText etSub, etBody, etUrl;
    private TextView tvTitle, tvDesc, tvUrl;
    private ImageView imageView;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mainLayout = findViewById(R.id.mainLayout);

        etSub = findViewById(R.id.etSub);
        etBody = findViewById(R.id.etBody);
        etUrl = findViewById(R.id.edURL);

        tvTitle = findViewById(R.id.tvSubject);
        tvDesc = findViewById(R.id.tvBody);
        tvUrl = findViewById(R.id.tvURL);

        btnOk = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);
        btnShow = findViewById(R.id.btnShowUrl);

        imageView = findViewById(R.id.imageView);

        db = new Database();

        DisplayMetrics met = getResources().getDisplayMetrics();
        orderViewsByScreen(met.widthPixels, met.heightPixels, (int)met.density);

        Intent i = getIntent();
        final String state = i.getStringExtra("state");
        final Movie m = new Movie(this, etSub.getText().toString(), etBody.getText().toString()); //CRASH
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
        else if(state.equals("add_from_api")) {
            etSub.setText(i.getStringExtra("sub"));
            etBody.setText(i.getStringExtra("body"));
            String url = i.getStringExtra("url");
            etUrl.setText(url);
            new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                    .execute(url);
        }
        else {
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    m.setName(etSub.getText().toString());
                    m.setBody(etBody.getText().toString());
                    db.addProduct(m);

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

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                        .execute("http://icons.iconarchive.com/icons/graphicloads/100-flat/256/home-icon.png");
            }
        });
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
