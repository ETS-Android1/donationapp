package com.example.finalproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true) @NonNull
    private int user_id;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "password_hash")
    private String password_hash;

    @Ignore
    private String password;

    public User(String username, String password_hash) {
        this.username = username;
        this.password_hash = password_hash;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", password_hash='" + password_hash + '\'' +
                '}';
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

}
