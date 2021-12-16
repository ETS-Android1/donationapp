package com.example.finalproject;

import androidx.room.*;

import java.util.List;

@Dao
public interface CharityDao {

    @Query("SELECT * FROM Charity")
    List<Charity> getAll();

    @Query("SELECT * FROM Charity WHERE charity_id IN (:charityIds)")
    List<Charity> loadAllByIds(int[] charityIds);

    @Query("SELECT * FROM Charity WHERE title = :title")
    List<Charity> searchTitle(String title);

    @Query("SELECT * FROM Charity WHERE title LIKE :title")
    List<Charity> queryTitle(String title);

    @Query("SELECT * FROM Charity WHERE charity_id LIKE :charity_id")
    Charity findById(int charity_id);

    @Insert
    void insertAll(Charity...charities);

    @Delete
    void delete(Charity charity);

    @Update
    public void updateCharities(Charity charities);

}