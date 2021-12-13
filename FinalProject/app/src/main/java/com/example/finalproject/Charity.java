package com.example.finalproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity
public class Charity {

    @PrimaryKey(autoGenerate = true) @NonNull
    private int charity_id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "about")
    private String about;

    public Charity(String title, String about) {
        this.title = title;
        this.about = about;
    }

    @Override
    public String toString() {
        return "Charity{" +
                "charity_id=" + charity_id +
                ", title='" + title + '\'' +
                ", about='" + about + '\'' +
                '}';
    }

    public int getCharity_id() {
        return charity_id;
    }

    public void setCharity_id(int charity_id) {
        this.charity_id = charity_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
