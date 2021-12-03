package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "finalprojectdb.db";

    public DatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" create table " + "user" + " (user_id integer primary key autoincrement, username text, password_hash text) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + User.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /*

    public boolean insertData (String name, String age, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, Integer.parseInt(age));
        contentValues.put(COL_3, phone);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if ( result == -1)
            return  false;
        else
            return  true;
    }



    public Cursor getAllData() {
        SQLiteDatabase  db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData (String name, String age, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, age);
        contentValues.put(COL_3, phone);
        db.update(TABLE_NAME, contentValues, "NAME = ? ", new String[] {name});
        return  true;

    }

    public Integer deleteData (String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_NAME, "NAME = ? ", new String[] {name});
    }
    */

}
