package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class DonationHistoryActivity extends AppCompatActivity {
    int currentUser_id;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_history);
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        Intent intent = getIntent();
        currentUser_id= intent.getIntExtra("user_id",-2);//-2 will mean no user(error)
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "AppDatabase").enableMultiInstanceInvalidation().allowMainThreadQueries().build();
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    public void updateView()
    {
        DonationWithUserAndCharityDao dao2 = db.getDonationWithUserAndCharityDao();
        List<DonationWithUserAndCharity> donationsList = dao2.getDonationsWithUsersAndCharities();

        Log.e("donationhistoryactivity", "update view  " + donationsList.size());

        if(donationsList.size()==0) {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if(fm.getFragments().isEmpty()) {

            fragmentTransaction.add(R.id.frameLayout2, new DonationHistoryFragment(donationsList,db), null);
        }
        else {

            fragmentTransaction.replace(R.id.frameLayout2, new DonationHistoryFragment(donationsList,db), null).addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
    public void startDonateActivity(int charity_id)
    {
        Intent intent = new Intent(this, DonateActivity.class);
        intent.putExtra("charity_id",charity_id);
        intent.putExtra("user_id",this.currentUser_id);
        startActivity(intent);
    }
}