package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    int currentUser_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        intent.getIntExtra("user_id",-1);//-1 will mean no user(error)

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "AppDatabase").enableMultiInstanceInvalidation().allowMainThreadQueries().build();
    }
}