package com.example.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(version = 1, entities = {User.class} )
abstract class AppDatabase extends RoomDatabase{

    abstract public UserDao getUserDao();
}

