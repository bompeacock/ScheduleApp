package com.example.cong.model;

import java.io.Serializable;
import java.util.StringTokenizer;


/**
 * Created by Cong on 27/09/2016.
 */
public class Note implements Serializable,Comparable<Note> {
    public  static  int tmp = 10;
    private int id;
    private String name;
    private String time;
    private String task;
    private String level;
    private String status;

    public Note() {
    }

    public Note( String name, String time, String task, String level, String status) {
        this.name = name;
        this.time = time;
        this.task = task;
        this.level = level;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = tmp;
        tmp++;

    }
    public void setId(int id) {
        this.id = id;


    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(Note note) {
        return this.getDateToCompare()-note.getDateToCompare();
    }
    private int getDateToCompare(){
        StringTokenizer tokenizer = new StringTokenizer(time,"-");
        int day = Integer.parseInt(tokenizer.nextToken());
        int month = Integer.parseInt(tokenizer.nextToken());
        int year = Integer.parseInt(tokenizer.nextToken());

        return day+year*370+month*35;
    }
    public int getLevelToCompare(){
        return this.getLevel().equalsIgnoreCase("high")?1:this.getLevel().equalsIgnoreCase("medium")?2:3;
    }
}