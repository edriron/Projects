package com.momowords.ron.snamegameimproved;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Ron on 25/02/2018.
 */

public class Food {
    private Rect food;
    private Point pos;
    private int color;

    public Food() {
        food = new Rect(20, 20, 40, 40);
        this.pos = new Point(0, 0);
        color = Color.WHITE;
    }

    public Food(Rect food) {
        food = new Rect(20, 20, 40, 40);
        this.pos = new Point(0, 0);
        color = Color.WHITE;
    }

    public Food(Point pos) {
        food = new Rect(20, 20, 40, 40);
        this.pos = pos;
        color = Color.WHITE;
    }

    public Food(Rect food, Point pos) {
        this.food = food;
        this.pos = pos;
    }

    public int getX() {
        return pos.x;
    }

    public int getY() {
        return pos.y;
    }

    public int getTop() {
        return food.top;
    }

    public int getBottom() {
        return food.bottom;
    }

    public int getLeft() {
        return food.left;
    }

    public int getRight() {
        return food.right;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void eaten(int screenX, int screenY) {
        setRandomPos(screenX, screenY);
    }

    public void setRandomPos(int screenX, int screenY) {
        Random r = new Random();
        //int randomX = r.nextInt(screenX);
        //int randomY = r.nextInt(screenY);
        pos.x = r.nextInt(screenX - 20) + 20;
        pos.y = r.nextInt(screenY - 20) + 20;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);

        food.set(pos.x - food.width()/2, pos.y - food.height()/2, pos.x + food.width()/2, pos.y + food.height()/2);
        canvas.drawRect(food, paint);
    }

    public void draw(Canvas canvas, int screenX, int screenY) {
        Paint paint = new Paint();
        paint.setColor(color);

        setRandomPos(screenX, screenY);

        food.set(pos.x - food.width()/2, pos.y - food.height()/2, pos.x + food.width()/2, pos.y + food.height()/2);
        canvas.drawRect(food, paint);
    }
}
