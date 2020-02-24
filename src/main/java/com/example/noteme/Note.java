package com.example.noteme;

//this class holds the data from the add note activity
//and it will pass the data to the SQL Lite Database

import java.io.Serializable;

public class Note implements Serializable {
    private long ID;
    private String title;
    private String content;
    private String date;
    private String time;


    Note(){}

    //before you save the data to the database, we have no ID
    Note(String title, String content, String date, String time){
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
    }

    //after the data has been added to the database, now it has its own ID
    Note(long id, String title, String content, String date, String time){
        this.ID = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;

    }

    public long getID() {
        return this.ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
