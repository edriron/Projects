package com.momowords.ron.snamegameimproved;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Ron on 25/02/2018.
 */

public class Snake {
    private Rect snake;
    private int length;
    private ArrayList<Boolean> tail;
    private int color;
    private Point snakePos;

    public int SPEED = 5;
    public enum Heading {UP, DOWN, LEFT, RIGHT};
    public Heading heading = Heading.RIGHT;

    public Snake(Rect snake, Point snakePos) {
        this.snake = snake;
        this.snakePos = snakePos;
        color = Color.YELLOW;
    }

    public void moveSnake() {
        switch (heading) {
            case UP:
                snakePos = new Point(snakePos.x, snakePos.y - SPEED);
                break;

            case RIGHT:
                snakePos = new Point(snakePos.x + SPEED, snakePos.y);
                break;

            case DOWN:
                snakePos = new Point(snakePos.x, snakePos.y + SPEED);
                break;

            case LEFT:
                snakePos = new Point(snakePos.x - SPEED, snakePos.y);
                break;
        }
    }

    public int getX() {
        return snakePos.x;
    }

    public int getY() {
        return snakePos.y;
    }

    public int getTop() {
        return snake.top;
    }

    public int getBottom() {
        return snake.bottom;
    }

    public int getLeft() {
        return snake.left;
    }

    public int getRight() {
        return snake.right;
    }

    public int getHeight() {
        return snake.height();
    }

    public int getWidth() {
        return snake.width();
    }

    public void eat(Food food) {
        SPEED++;
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isOnEdge(int screenX, int screenY) {
        return (snake.top == 0 || snake.left == 0 || snake.right == screenX || snake.bottom == screenY);
    }

    public Heading getOppositeDirection(int screenX) {
        if(snake.top == 0)
            return Heading.DOWN;
        else if(snake.left == 0)
            return Heading.RIGHT;
        else if(snake.right == screenX)
            return Heading.LEFT;
        else return Heading.UP;

    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        //paint.setColor(Color.rgb(255, 255, 0));

        snake.set(snakePos.x - snake.width()/2, snakePos.y - snake.height()/2, snakePos.x + snake.width()/2,snakePos.y + snake.height()/2);

        canvas.drawRect(snake, paint);
    }
}
