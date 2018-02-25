package com.momowords.ron.snamegameimproved;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Ron on 25/02/2018.
 */

public class Candy extends Food {
    private int bonusSpeed;
    private int bonusScore;

    public Candy(int bonusSpeed, int bonusScore) {
        super();
        this.bonusSpeed = bonusSpeed;
        this.bonusScore = bonusScore;
    }

    public Candy(Rect rect, Point pos, int bonusSpeed, int bonusScore) {
        super(rect, pos);
        this.bonusSpeed = bonusSpeed;
        this.bonusScore = bonusScore;
    }

    public Candy(Rect rect, int bonusSpeed, int bonusScore) {
        super(rect);
        this.bonusSpeed = bonusSpeed;
        this.bonusScore = bonusScore;
    }

    public int getBonusSpeed() {
        return  bonusSpeed;
    }

    public int getBonusScore() {
        return  bonusScore;
    }

    public void setBonusSpeed(int bonusSpeed) {
        this.bonusSpeed = bonusSpeed;
    }

    public void setBonusScore(int bonusScore) {
        this.bonusScore = bonusScore;
    }
}
