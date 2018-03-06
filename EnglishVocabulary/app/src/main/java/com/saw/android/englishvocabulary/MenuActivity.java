package com.saw.android.englishvocabulary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_menu);

        mainLayout = ViewsCreator.createLinearLayout(Content.Match);

        Button button = new Button(this);
        button.setText("test");
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        button.setLayoutParams(params);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MyApp.getContext(), "test", Toast.LENGTH_SHORT).show();
            }
        });
        mainLayout.addView(button);
    }


}
