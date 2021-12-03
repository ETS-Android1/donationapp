package com.example.finalproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;

public class Donation {


    private String donation_id;
    private BigDecimal amount;
    private String date;
    private String user_id;


    private String password;

    public final static String TABLE_NAME="user";
    private final static String COL_1="donation_id";
    private final static String COL_2="amount";
    private final static String COL_3="date";
    private final static String COL_4="user_id";


    public Donation(String donation_id, BigDecimal amount, String date, String user_id ) {

        this.donation_id = donation_id;
        this.amount = amount;
        this.date=date;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "donation_id='" + donation_id + '\'' +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", user_id='" + user_id + '\'' +
                ", password='" + password + '\'' +
                '}';
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
