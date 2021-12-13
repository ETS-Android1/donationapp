package com.example.finalproject;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface DonationWithUserAndCharityDao {

    @Transaction
    @Query("SELECT * FROM Donation")
    List<DonationWithUserAndCharity> getDonationsWithUsersAndCharities();
}
