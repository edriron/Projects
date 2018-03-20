package com.saw.android.englishvocabulary;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ron on 09/03/2018.
 */

public class WordsList {

    private ArrayList<Word> list;
    private Database db;
    private boolean needUpdate;

    public WordsList() {
        list = new ArrayList<>();
        db = new Database();
        list = db.getAllProducts();
        needUpdate = false;
    }

    public void reloadFromDB() {
        if(needUpdate) {
            list = db.getAllProducts();
            needUpdate = false;
        }
    }

    public void addWord(Word word) {
        db.addProduct(word);
        needUpdate = true;
    }

    //return true if deleted successfully, false if failed
    public boolean deleteWord(Word word) {
        for(int i = 0; i < list.size(); i++) {
            Word currentWord = list.get(i);
            if(currentWord.getName().equals(word.getName())) {
                db.deleteProduct(word);
                list.remove(i);
                return true;
            }
        }
        return false;
    }

    //return true if updated successfully, false if failed
    public boolean updateWord(Word oldWord, Word newWord) {
        for(int i = 0; i < list.size(); i++) {
            Word currentWord = list.get(i);
            if(currentWord.getName().equals(oldWord.getName())) {
                db.updateProduct(newWord);
                currentWord.replace(newWord);
                return true;
            }
        }
        return false;
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

    public void test2() {
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
        list = db.getAllProducts();
    }
}
