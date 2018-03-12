package com.saw.android.englishvocabulary;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ron on 09/03/2018.
 */

public class WordsList {

    private ArrayList<Word> list;

    public WordsList() {
        list = new ArrayList<Word>();
    }

    public void addWord(Word word) {
        list.add(word);
    }

    public ArrayList<Word> getArray() {
        return list;
    }

    public boolean isEmpty() {
        return list.size() == 0;
    }

    public String[] getNamesStringArray() {
        String[] l = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            Word index = list.get(i);
            l[i] = index.getName() + " (" + index.getType() + ")";
        }
        return l;
    }

    public String[] getNames() {
        String[] l = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            Word index = list.get(i);
            l[i] = index.getName();
        }
        return l;
    }

    public String[] getTrans() {
        String[] l = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            Word index = list.get(i);
            l[i] = index.getTrans();
        }
        return l;
    }

    //public String[] getGroup

    public String getTransByName(String name) {
        for(Word w : list) {
            String temp = name.substring(0, w.getName().length());
            Log.d("test", temp + "/" + name);
            if (w.getName().equalsIgnoreCase(temp))
                return w.getTrans();
        }
        return "";
    }

    public void test() {
        list.add(new Word("Dismay", "הבהיל", GroupType.Verb));
        list.add(new Word("Absently", "בהיסח דעת", GroupType.Adjective));
        list.add(new Word("Jabbing", "לנעוץ", GroupType.Verb));
        list.add(new Word("Frankly", "בכנות", GroupType.Adjective));
        list.add(new Word("Callow", "טירון", GroupType.Noun));
        list.add(new Word("Stifle", "מחניק", GroupType.Adjective));
        list.add(new Word("Affronted", "העליב", GroupType.Verb));
        list.add(new Word("Preposterous", "אבסורדי", GroupType.Adjective));
        list.add(new Word("Indulged", "התענג", GroupType.Verb));
        list.add(new Word("Strewn", "מכוסה במשהו, זרוע", GroupType.Adjective));
    }
}
