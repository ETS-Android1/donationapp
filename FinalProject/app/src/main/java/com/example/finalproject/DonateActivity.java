package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class DonateActivity extends AppCompatActivity {

    EditText charityTitleET,amountET;
    Button donateButton;
    int charity_id,user_id;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        charityTitleET=findViewById(R.id.charityTitleET);
        amountET=findViewById(R.id.donationAmountET);
        donateButton=findViewById(R.id.donateButton);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "AppDatabase").enableMultiInstanceInvalidation().allowMainThreadQueries().build();

        charity_id= intent.getIntExtra("charity_id",0);
        user_id= intent.getIntExtra("user_id",0);
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( amountET.getText().toString().length()!=0 && Integer.parseInt(amountET.getText().toString())!=0 )
                {
                    makeNewDonation(charity_id,user_id,amountET.getText().toString());
                }

            }
        });

    }
    private void makeNewDonation(int charity_id,int user_id,String price)
    {
        DonationDao dao = db.getDonationDao();
        Donation d1 = new Donation(user_id,charity_id,price);
        dao.insertAll(d1);

        DonationWithUserAndCharityDao dao2 = db.getDonationWithUserAndCharityDao();
        List<DonationWithUserAndCharity> donationsList = dao2.getDonationsWithUsersAndCharities();

        for ( DonationWithUserAndCharity x : donationsList) {

            Log.e("makenewdonation", "all donations: " + x.toString());
            Log.e("makenewdonation", "newly inserted DONATION class obj id: " + x.donation.getDonation_id());

        }

    }

}