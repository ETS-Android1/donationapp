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
}
