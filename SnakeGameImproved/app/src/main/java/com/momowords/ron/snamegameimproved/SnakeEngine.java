package com.momowords.ron.snamegameimproved;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Ron on 23/02/2018.
 */

public class SnakeEngine extends SurfaceView implements Runnable {

    private Thread thread = null;
    private Context context;

    private Snake snake;
    private Food food = null;

    private int screenX;
    private int screenY;

    private final int FPS = 30;
    private long nextFrameTime;

    private boolean isPlaying;

    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    private float x1,x2, y1, y2;
    private int swipeDirection;
    static final int MIN_DISTANCE = 150;

    public SnakeEngine (Context context) {
        super(context);

        screenX = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenY = Resources.getSystem().getDisplayMetrics().heightPixels;

        screenY -= getNavigationBarHeight();

        surfaceHolder = getHolder();
        paint = new Paint();

        snake = new Snake(new Rect(75, 75, 150, 150), new Point(150, 150));

        startGame();
    }

    public int getNavigationBarHeight() {
        int navigationBarHeight = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }

    @Override
    public void run() {
        while(isPlaying) {
            if(updateRequired()) {
                update();
                draw();
            }
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    private void startGame() {
        snake.setLength(50);
        nextFrameTime = System.currentTimeMillis();
    }

    public void update() {
        if (snake.isOnEdge(screenX, screenY))
            snake.setHeading(snake.getOppositeDirection());
        snake.moveSnake();

        if ((food != null) && (snake.getLeft() <= food.getX() && snake.getRight() >= food.getX()) && snake.getTop() <= food.getY() && snake.getBottom() >= food.getY()) {
            snake.eat(food);
            food.eaten(screenX, screenY);
        }
    }

    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.argb(255, 26, 128, 182)); //bg color

            paint.setTextSize(90);
            canvas.drawText("X: " + snake.getX() + "   Y: " + snake.getY() + "   S: " + snake.SPEED, 10, 70, paint);

            snake.draw(canvas);

            //food = (food == null) ? new Candy(new Rect(20, 20, 40, 40), new Point(0, 0), 1, 1) : food;
            if(food == null) {
                food = new Food();
                food.draw(canvas, screenX, screenY);
            } else {
                food.draw(canvas);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean updateRequired() {
        // Are we due to update the frame
        if(nextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            nextFrameTime =System.currentTimeMillis() + 1000 / FPS;

            // Return true so that the update and draw
            // functions are executed
            return true;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                if(Math.abs(y1-y2) > Math.abs(x1-x2))
                    swipeDirection = 1; //up or down
                else swipeDirection = 2; //left or right

                float deltaX = x2 - x1;
                float deltaY = y2 - y1;

                switch (swipeDirection) {
                    case 1://up or down
                        if(y2 > y1 && Math.abs(deltaY) > MIN_DISTANCE) { //swipe down
                            snake.setHeading(Snake.Heading.DOWN);
                        }
                        else if(y1 > y2 && Math.abs(deltaY) > MIN_DISTANCE){ //swipe up
                            snake.setHeading(Snake.Heading.UP);
                        }
                        break;

                    case 2://left or right
                        if(x2 > x1 && Math.abs(deltaX) > MIN_DISTANCE) { //swipe right
                            snake.setHeading(Snake.Heading.RIGHT);
                        }
                        else if(x1 > x2 && Math.abs(deltaX) > MIN_DISTANCE){ //swipe left
                            snake.setHeading(Snake.Heading.LEFT);
                        }
                    break;
                }
                break;
        }

        return true;
    }
}
