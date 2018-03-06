package com.saw.android.englishvocabulary;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private LinearLayout.LayoutParams params;
    //int screenX = getResources().getDisplayMetrics().widthPixels,
    //    screenY = getResources().getDisplayMetrics().heightPixels;
    //private Context c;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().hide();

        tv = (TextView) findViewById(R.id.tvTest);

        int[] a = getPixelsByPrecentage(90, 85);
        tv.setX(a[0]);
        tv.setY(a[1]);
        tv.setText(a[0] + ", " + a[1]);

        /*mainLayout = ViewsCreator.createLinearLayout(Content.Match);

        Button button = new Button(this);
        button.setText("test");
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        button.setLayoutParams(params);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] a = getPixelsByPrecentage(50, 50);
                Toast.makeText(c, a[0] + " " + a[1], Toast.LENGTH_SHORT).show();
            }
        });
        mainLayout.addView(button);

        setContentView(mainLayout);*/
    }

    public int[] getPixelsByPrecentage(int x, int y) {
        int[] xy = {0, 0};
        if(x < 0 || y < 0 || x > 100 || y > 100)
            return xy;
        int maxX = getResources().getDisplayMetrics().widthPixels;
        int maxY = getResources().getDisplayMetrics().heightPixels - getNavigationBarHeight();

        xy[0] = (x * maxX) / 100;
        xy[1] = (y * maxY) / 100;

        return xy;
    }

    public int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0)
            return resources.getDimensionPixelSize(resourceId);
        return 0;
    }

}
