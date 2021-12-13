package com.example.finalproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;

@Entity
public class Donation {

    @PrimaryKey(autoGenerate = true) @NonNull
    private int donation_id;
    @ColumnInfo(name = "d_user_id")
    private int user_id;
    @ColumnInfo(name = "d_charity_id")
    private int charity_id;
    @ColumnInfo(name = "amount")
    private String amount;

    public Donation(int user_id, int charity_id, String amount) {
        this.user_id = user_id;
        this.charity_id = charity_id;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "donation_id=" + donation_id +
                ", user_id=" + user_id +
                ", charity_id=" + charity_id +
                ", amount='" + amount + '\'' +
                '}';
    }

    public int getDonation_id() {
        return donation_id;
    }

    public void setDonation_id(int donation_id) {
        this.donation_id = donation_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCharity_id() {
        return charity_id;
    }

    public void setCharity_id(int charity_id) {
        this.charity_id = charity_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
