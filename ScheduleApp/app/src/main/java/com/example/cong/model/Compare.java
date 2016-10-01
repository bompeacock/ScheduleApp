package com.example.cong.model;

import java.util.Comparator;

/**
 * Created by Cong on 29/09/2016.
 */
public class Compare implements Comparator<Note> {
    @Override
    public int compare(Note note1, Note note2) {
        if(note1.getLevelToCompare()<note2.getLevelToCompare()){
            return -1;
        }else if(note1.getLevelToCompare()>note2.getLevelToCompare()) return 1;

        return 0;
    }
}
