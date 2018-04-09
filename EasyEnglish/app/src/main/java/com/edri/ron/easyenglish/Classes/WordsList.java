package com.edri.ron.easyenglish.Classes;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ron on 09/03/2018.
 */

public class WordsList implements Serializable {

    private ArrayList<Word> list;
    private Database db;
    private boolean needUpdate;

    public WordsList() {
        list = new ArrayList<>();
        db = new Database();
        list = db.getAllWords();
        needUpdate = false;
    }

    public void reloadFromDB() {
        if(needUpdate) {
            list = db.getAllWords();
            needUpdate = false;
        }
    }

    public int addWord(Word word) {
        if(contains(word))
            return -1;

        list.add(word);
        db.addWord(word);
        needUpdate = true;
        return 0;
    }

    //return true if deleted successfully, false if failed
    public boolean deleteWord(Word word) {
        for(int i = 0; i < list.size(); i++) {
            Word currentWord = list.get(i);
            if(currentWord.getName().equals(word.getName())) {
                db.deleteWord(word);
                list.remove(i);
                return true;
            }
        }
        return false;
    }

    public void deleteAllWords() {
        while(!list.isEmpty()) {
            db.deleteWord(list.get(0));
            list.remove(0);
        }
    }

    //return true if updated successfully, false if failed
    public boolean updateWord(Word oldWord, Word newWord) {
        for(int i = 0; i < list.size(); i++) {
            Word currentWord = list.get(i);
            if(currentWord.getName().equals(oldWord.getName())) {
                db.updateWord(newWord);
                currentWord.replace(newWord);
                needUpdate = true;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return list.size();
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

    public String[] getNamesStringArrayWithTrans() {
        String[] l = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            Word index = list.get(i);
            l[i] = index.getName() + " (" + index.getType() + ") - " + index.getTrans();
        }
        return l;
    }

    public boolean contains(Word word) {
        for(Word w : list)
            if(w.getName().equals(word.getName()))
                return true;
        return false;
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

    //return word by position
    public Word get(int pos) {
        return list.get(pos);
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

    /*public void test2() {
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

    public void testDB() {
        Database db = new Database();
        list = db.getAllWords();
    }*/

    public ArrayList<Word> duplicate() {
        ArrayList<Word> duplicated = new ArrayList<>();
        for(Word w : list)
            duplicated.add(w);
        return duplicated;
    }
}
