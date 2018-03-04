package com.edri.ron.matrixhomework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int screenX, screenY;
    public final int ROWS = 9;
    public TextView[][] cellsArr = new TextView[ROWS][ROWS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenX = getResources().getDisplayMetrics().widthPixels;
        screenY = getResources().getDisplayMetrics().heightPixels;

        createViews();
    }
    
    private void createViews() {
        //  Linear Layout
        LinearLayout mainLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(mainLayout, layoutParams);

        //  Text View
        TextView textView = new TextView(this);
        textView.setText("Matrix");
        textView.setTextSize(50);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewParams.gravity = Gravity.CENTER;
        textViewParams.topMargin = 50;
        textViewParams.bottomMargin = 50;
        textView.setLayoutParams(textViewParams);
        mainLayout.addView(textView);

        //buttons layout
        LinearLayout[] buttonLayout = new LinearLayout[2];
        for(int i = 0; i < buttonLayout.length; i++) {
            buttonLayout[i] = new LinearLayout(this);
            LinearLayout.LayoutParams buttonLayout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            buttonLayout[i].setLayoutParams(buttonLayout_params);
            mainLayout.addView(buttonLayout[i]);
        }

        //creating buttons and adding them to the layout
        String[] text = {"Upper Tri", "Bottom Tri", "Borders", "Diagonals"};
        Button[] buttons = new Button[4];
        for(int i = 0; i < buttons.length; i++) {

            buttons[i] = new Button(this);
            buttons[i].setText(text[i]);
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonParams.weight = 1;
            buttons[i].setLayoutParams(buttonParams);

            int r = (i < 2) ? 0 : 1;
            buttonLayout[r].addView(buttons[i]);

            onClick(buttons[i]);
        }

        //int m = (int)buttonLayout[1].getBottom();
        //screenY -= m;
        screenY /= 2;

        //creating rows layout to hold the views
        LinearLayout[] rowsLayout = new LinearLayout[ROWS];
        for (int i = 0; i < rowsLayout.length; i++) {
            rowsLayout[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            params.rightMargin = 20;
            params.leftMargin = 20;
            if(i == 0) params.topMargin = 20;
            rowsLayout[i].setLayoutParams(params);
            mainLayout.addView(rowsLayout[i]);
        }

        // creating a TextView array with 9 cells
        int x = screenX / ROWS, y = screenY / ROWS;
        for (int i = 0; i < cellsArr.length; i++) {
            for(int j = 0; j < cellsArr[i].length; j++) {
                cellsArr[i][j] = new TextView(this);
                LinearLayout.LayoutParams cellsParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                cellsParams.gravity = Gravity.CENTER;
                cellsParams.weight = 1;
                cellsParams.width = x;
                cellsParams.height = y;
                cellsArr[i][j].setLayoutParams(cellsParams);
                cellsArr[i][j].setBackground(getResources().getDrawable(R.drawable.shape_table_cell));
                rowsLayout[i].addView(cellsArr[i][j]);
            }
        }
    }

    public void onClick(View v) {
        Button button = (Button) v;
        switch (button.getText().toString()) {

            case "Diagonals":
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(int i = 0; i < cellsArr.length; i++) {
                            for(int j = 0; j < cellsArr[i].length; j++) {
                                if(i == j || i + j == cellsArr.length - 1)
                                    cellsArr[i][j].setBackground(getResources().getDrawable(R.drawable.shape_table_cell_colored));
                                else cellsArr[i][j].setBackground(getResources().getDrawable(R.drawable.shape_table_cell));
                            }
                        }
                    }
                });
                break;

            case "Bottom Tri":
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(int i = 0; i < cellsArr.length; i++) {
                            for (int j = 0; j < cellsArr[i].length; j++) {
                                if(i == j || i > j)
                                    cellsArr[i][j].setBackground(getResources().getDrawable(R.drawable.shape_table_cell_colored));
                                else cellsArr[i][j].setBackground(getResources().getDrawable(R.drawable.shape_table_cell));
                            }
                        }
                    }
                });
                break;

            case "Upper Tri":
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(int i = 0; i < cellsArr.length; i++) {
                            for (int j = 0; j < cellsArr[i].length; j++) {
                                if(i == j || i < j)
                                    cellsArr[i][j].setBackground(getResources().getDrawable(R.drawable.shape_table_cell_colored));
                                else cellsArr[i][j].setBackground(getResources().getDrawable(R.drawable.shape_table_cell));
                            }
                        }
                    }
                });
                break;

            case "Borders":
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(int i = 0; i < cellsArr.length; i++) {
                            for (int j = 0; j < cellsArr[i].length; j++) {
                                if(i == 0 || j == 0 || i == cellsArr.length - 1 || j == cellsArr[i].length - 1)
                                    cellsArr[i][j].setBackground(getResources().getDrawable(R.drawable.shape_table_cell_colored));
                                else cellsArr[i][j].setBackground(getResources().getDrawable(R.drawable.shape_table_cell));
                            }
                        }
                    }
                });
                break;
        }
    }

}
