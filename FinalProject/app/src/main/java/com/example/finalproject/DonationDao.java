package com.example.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DonationDao {

    @Query("SELECT * FROM Donation")
    List<Donation> getAll();

    @Query("SELECT * FROM Donation WHERE donation_id IN (:donationIds)")
    List<Donation> loadAllByIds(int[] donationIds);

    @Query("SELECT * FROM Donation WHERE d_user_id LIKE :user_id")
    List<Donation> findByUserId(int user_id);

    @Query("SELECT * FROM Donation WHERE donation_id LIKE :donation_id")
    Donation findById(int donation_id);

    @Insert
    void insertAll(Donation...donations);

    @Delete
    void delete(Donation donation);

    @Query("DELETE FROM Donation WHERE donation_id LIKE :donation_id")
    void deleteById(int donation_id);

    @Update
    public void updateDonations(Donation donation);
}
