package com.saw.android.englishvocabulary;

import android.content.res.Resources;
import android.widget.LinearLayout;

/**
 * Created by Android on 3/6/2018.
 */

enum Content {
    Wrap, Match
}

public class ViewsCreator {

    public ViewsCreator() {

    }

    public static LinearLayout createLinearLayout(Content c1, Content c2) {
        LinearLayout layout = new LinearLayout(MyApp.getContext());
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                (c1 == Content.Match) ? (LinearLayout.LayoutParams.MATCH_PARENT) : (LinearLayout.LayoutParams.WRAP_CONTENT),
                (c2 == Content.Match) ? (LinearLayout.LayoutParams.MATCH_PARENT) : (LinearLayout.LayoutParams.WRAP_CONTENT))
        );
        return layout;
    }

    public static LinearLayout createLinearLayout(Content c) {
        LinearLayout layout = new LinearLayout(MyApp.getContext());
        int res = (c == Content.Match) ? -1 : -2;
        layout.setLayoutParams(new LinearLayout.LayoutParams(res, res));
        return layout;
    }

    public static int[] getPixelsByPercentage(int x, int y) {
        int[] xy = {0, 0};
        if(x < 0 || y < 0 || x > 100 || y > 100)
            return xy;
        int maxX = MyApp.getContext().getResources().getDisplayMetrics().widthPixels;
        int maxY = MyApp.getContext().getResources().getDisplayMetrics().heightPixels - getNavigationBarHeight();

        xy[0] = (x * maxX) / 100;
        xy[1] = (y * maxY) / 100;

        return xy;
    }

    public static int getNavigationBarHeight() {
        Resources resources = MyApp.getContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0)
            return resources.getDimensionPixelSize(resourceId);
        return 0;
    }
}
