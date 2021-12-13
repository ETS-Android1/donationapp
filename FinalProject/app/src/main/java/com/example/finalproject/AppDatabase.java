package com.example.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(version = 1, entities = {User.class,Charity.class,Donation.class} )
abstract class AppDatabase extends RoomDatabase{

    abstract public UserDao getUserDao();
    abstract public CharityDao getCharityDao();
    abstract public DonationDao getDonationDao();
    abstract public DonationWithUserAndCharityDao getDonationWithUserAndCharityDao();
}

