package com.momowords.ron.snamegameimproved;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by Ron on 27/02/2018.
 */

public class Head {

    private float x;
    private float y;
    private float radius;
    private Paint paint;
    private Bitmap bitmap = null;

    public Head(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        paint = new Paint();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void delBitmap() {
        bitmap = null;
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Point getPos() {
        return new Point((int)x, (int)y);
    }

    public void draw(Canvas canvas) {

        if(bitmap == null) {

            return;
        }

        canvas.drawCircle(x, y, radius, paint);
    }
}
