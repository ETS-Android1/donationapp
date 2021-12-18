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
    @Transaction
    @Query("SELECT * FROM Donation WHERE d_user_id LIKE :user_id")
    DonationWithUserAndCharity findByuser_id(int user_id);
    @Transaction
    @Query("SELECT * FROM Donation WHERE d_charity_id LIKE :charity_id")
    DonationWithUserAndCharity findBycharity_id(int charity_id);
    @Transaction
    @Query("SELECT * FROM Donation WHERE donation_id LIKE :donation_id")
    DonationWithUserAndCharity findBydonation_id(int donation_id);
}
