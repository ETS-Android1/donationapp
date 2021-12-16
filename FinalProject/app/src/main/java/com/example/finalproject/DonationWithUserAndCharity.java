package com.example.finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

public class DonationWithUserAndCharity {

        @Embedded
        Donation  donation;
        @Relation(entity = User.class,parentColumn = "d_user_id",entityColumn = "user_id")
        User user;
        @Relation(entity = Charity.class,parentColumn = "d_charity_id",entityColumn = "charity_id")
        Charity charity;


}
