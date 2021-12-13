package com.example.finalproject;

import androidx.room.*;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE user_id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE username LIKE :username LIMIT 1")
    User findByUsername(String username);

    @Query("SELECT * FROM user WHERE user_id LIKE :user_id")
    User findById(int user_id);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    public void updateUsers(User... users);

}
