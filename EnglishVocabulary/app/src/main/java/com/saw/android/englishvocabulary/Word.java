package com.saw.android.englishvocabulary;

import android.widget.Spinner;

/**
 * Created by Ron on 09/03/2018.
 */

public class Word {

    private String word;
    private String trans;
    private GroupType type;

    public Word(String word, String trans, GroupType type) {
        this.word = word;
        this.trans = trans;
        this.type = type;
    }

    public String getName() {
        return word;
    }

    public String getType() {
        return type.toString();
    }

    public String getTrans() {
        return trans;
    }

    public static GroupType getGroupType(Spinner spinner) {

        switch (spinner.getSelectedItemPosition()) {
            case 1:
                return GroupType.Verb;

            case 2:
                return GroupType.Noun;

            case 3:
                return GroupType.Adjective;

            default:
                return null;
        }

    }
}
