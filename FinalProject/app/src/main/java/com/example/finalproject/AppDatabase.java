package com.example.finalproject;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(version = 2, entities = {User.class,Charity.class,Donation.class})
abstract class AppDatabase extends RoomDatabase{

    abstract public UserDao getUserDao();
    abstract public CharityDao getCharityDao();
    abstract public DonationDao getDonationDao();
    abstract public DonationWithUserAndCharityDao getDonationWithUserAndCharityDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };
}

