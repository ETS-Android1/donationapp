package com.example.finalproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class User {

    private String user_id;
    private String username;
    private String password_hash;

    private String password;

    public final static String TABLE_NAME="user";
    private final static String COL_1="user_id";
    private final static String COL_2="username";
    private final static String COL_3="password_hash";


    public User(String user_id, String username, String password_hash) {

        this.user_id = user_id;
        this.username = username;
        this.password_hash = password_hash;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", password_hash='" + password_hash + '\'' +
                '}';
    }


    public static User getUser(SQLiteDatabase db, String user_id)
    {
        Cursor res = db.rawQuery("Select * from "+ TABLE_NAME + " WHERE "+COL_1+"= ?",new String[] {user_id});
        return new User(res.getString(0), res.getString(1),res.getString(2) );
    }

    public static boolean insertData (SQLiteDatabase db, String user_id, String username, String password_hash) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, user_id);
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password_hash);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if ( result == -1)
            return  false;
        else
            return  true;
    }
    public Cursor getAllData(SQLiteDatabase db) {
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME, null);
        return res;
    }
}
