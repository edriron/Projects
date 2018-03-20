package com.saw.android.englishvocabulary;

import android.widget.Spinner;

import java.io.Serializable;

/**
 * Created by Ron on 09/03/2018.
 */

public class Word implements Serializable {

    private int id;
    private String word;
    private String trans;
    private GroupType type;

    public Word(String word, String trans, GroupType type) {
        this.word = word;
        this.trans = trans;
        this.type = type;
    }

    public Word(String word, String trans, int type, int id) {
        this.word = word;
        this.trans = trans;
        this.id = id;
        switch (type) {
            case 2:
                this.type = GroupType.Noun;
                break;
            case 1:
                this.type = GroupType.Verb;
                break;
            default:
                this.type = GroupType.Adjective;
                break;
        }
    }


    public String getName() {
        return word;
    }

    public String getType() {
        return type.toString();
    }

    public GroupType getGroupType() {
        return type;
    }

    //  Get an integer that represents the group type
    public int getTypeByInt() {
        switch (type) {
            case Noun: return 2;
            case Verb: return 1;
            default: return 3;
        }
    }

    public String getTrans() {
        return trans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void replace(Word word) {
        this.word = word.getName();
        this.trans = word.getTrans();
        this.type = word.getGroupType();
    }

    //  Get the group type of the selected item in the spinner [Add Word Activity]
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
