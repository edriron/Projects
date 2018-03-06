package com.saw.android.englishvocabulary;

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
}
