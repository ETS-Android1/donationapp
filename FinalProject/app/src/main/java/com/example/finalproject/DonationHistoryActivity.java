package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DonationHistoryActivity extends AppCompatActivity {
    int currentUser_id;
    AppDatabase db;
    EditText searchText;
    Button searchDonations,backToMainButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_history);
        searchText=findViewById(R.id.searchText2);
        searchDonations=findViewById(R.id.searchDonations);
        searchDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchDonationsInHistory();
            }
        });
        backToMainButton=findViewById(R.id.backToMainButton);
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        Intent intent = getIntent();
        currentUser_id= intent.getIntExtra("user_id",-2);//-2 will mean no user(error)
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "AppDatabase").enableMultiInstanceInvalidation().allowMainThreadQueries().build();
        searchText.setText("");
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

    private void searchDonationsInHistory()
    {
        DonationWithUserAndCharityDao dao = db.getDonationWithUserAndCharityDao();
        List<DonationWithUserAndCharity> donationsList = dao.getDonationsWithUsersAndCharities();

        List<DonationWithUserAndCharity> donationsQueried = new ArrayList<>();
        if(donationsList.size()==0) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No Charity with the same name exists",
                    Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Pattern p = Pattern.compile(".*"+searchText.getText().toString()+".*");
        for(DonationWithUserAndCharity x :donationsList)
        {
            Matcher m = p.matcher(x.charity.getTitle());
            if(m.find())
            {
                donationsQueried.add(x);
            }
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if(fm.getFragments().isEmpty()) {

            fragmentTransaction.add(R.id.frameLayout2, new DonationHistoryFragment(donationsQueried,db), null);
        }
        else {

            fragmentTransaction.replace(R.id.frameLayout2, new DonationHistoryFragment(donationsQueried,db), null).addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}