package com.edri.ron.easyenglish.Classes;

import android.widget.RadioGroup;
import android.widget.Spinner;

import com.edri.ron.easyenglish.R;

import java.io.Serializable;

/**
 * Created by Ron on 09/03/2018.
 */

public class Word implements Serializable {

    private int id;
    private String word;
    private String trans;
    private String description;
    private String example;
    private GroupType type;
    private int currects, mistakes, rating;

    public Word(String word, String trans, String description, String example, int currects, int mistakes, int rating, GroupType type) {
        this.word = word;
        this.trans = trans;
        this.description = description;
        this.example = example;
        this.type = type;
        this.currects = currects;
        this.mistakes = mistakes;
        this.rating = rating;
    }

    public Word(String word, String trans, String description, String example, GroupType type) {
        this.word = word;
        this.trans = trans;
        this.description = description;
        this.example = example;
        this.type = type;
        this.currects = 0;
        this.mistakes = 0;
        this.rating = 0;
    }

    // Constructor for Database
    public Word(String word, String trans, String description, String example, int currects, int mistakes, int rating, int type, int id) {
        this.word = word;
        this.trans = trans;
        this.description = description;
        this.example = example;
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
        this.currects = currects;
        this.mistakes = mistakes;
        this.rating = rating;
    }

    // Getters
    public String getName() {
        return word;
    }

    public String getType() {
        return type.toString();
    }

    public String getDescription() {
        return description;
    }

    public int getCurrects() { return currects; }

    public int getMistakes() { return mistakes; }

    public int getRating() { return rating; }

    public String getExample() {
        return example;
    }

    public GroupType getGroupType() {
        return type;
    }

    public String getTrans() {
        return trans;
    }

    public int getId() {
        return id;
    }


    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setAllRatings(int currects, int mistakes, int rating) {
        this.currects = currects;
        this.mistakes = mistakes;
        this.rating = rating;
    }


    // Methods

    //  Get an integer that represents the group type
    public int getTypeByInt() {
        switch (type) {
            case Noun: return 2;
            case Verb: return 1;
            default: return 3;
        }
    }

    public void replace(Word word) {
        this.word = word.getName();
        this.trans = word.getTrans();
        this.type = word.getGroupType();
    }

    public static GroupType getGroupType(RadioGroup group) {
        switch(group.getCheckedRadioButtonId()) {
            case R.id.radioVerb:
                return GroupType.Verb;

            case R.id.radioNoun:
                return GroupType.Noun;

            default:
                return GroupType.Adjective;
        }
    }

    public static GroupType getGroupType(int id) {
        switch(id) {
            case R.id.radioVerb:
                return GroupType.Verb;

            case R.id.radioNoun:
                return GroupType.Noun;

            default:
                return GroupType.Adjective;
        }
    }
}
