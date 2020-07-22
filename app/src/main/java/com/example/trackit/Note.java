package com.example.trackit;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
@PrimaryKey(autoGenerate = true)
private int id;
private String title;
private String description;
//private String date;
private String time;
private int day_no;
private String alarmType;

    public Note(String title, String description,String time,int day_no,String alarmType) {
        this.title = title;
        this.description = description;
        //this.date = date;
        this.time = time;
        this.day_no = day_no;
        this.alarmType = alarmType;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    /*public String getDate(){
        return date;
    }*/

    public String getTime(){return time; }

    public int getDay_no(){ return day_no;}

public String getAlarmType(){ return alarmType;}
}
