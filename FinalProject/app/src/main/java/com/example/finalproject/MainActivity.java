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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    int currentUser_id;
    EditText searchText;
    TextView nameText;
    Button insertCharities,searchCharities;

    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchText = findViewById(R.id.searchText);
        nameText = findViewById(R.id.textView2);

        insertCharities=findViewById(R.id.insertCharities);
        searchCharities=findViewById(R.id.searchCharities);

        insertCharities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCharityInsertActivity();
            }
        });
        searchCharities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchCharities();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        currentUser_id= intent.getIntExtra("user_id",-2);//-2 will mean no user(error)
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "AppDatabase").enableMultiInstanceInvalidation().allowMainThreadQueries().build();
        if(currentUser_id==-1)
        {
            nameText.setText("admin");
            insertCharities=findViewById(R.id.insertCharities);
            insertCharities.setVisibility(View.VISIBLE);
        }
        else
        {
            UserDao userDao = db.getUserDao();
            User user = userDao.findById(currentUser_id);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    public void startCharityEditActivity(int charity_id)
    {
        Intent intent = new Intent(this, CharityEditActivity.class);
        intent.putExtra("charity_id",charity_id);
        startActivity(intent);
    }
    public void startCharityInsertActivity()
    {
        Intent intent = new Intent(this, CharityAddActivity.class);
        startActivity(intent);
    }
    public void startDonateActivity(int charity_id)
    {
        Intent intent = new Intent(this, DonateActivity.class);
        intent.putExtra("charity_id",charity_id);
        startActivity(intent);
    }
    private void searchCharities()
    {
        CharityDao charityDao = db.getCharityDao();
        List<Charity> charities = charityDao.queryTitle(searchText.getText().toString());

        if(charities.size()==0) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No Charity with the same name exists",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if(fm.getFragments().isEmpty()) {

            fragmentTransaction.add(R.id.frameLayout1, new CharityFragment(charities,db), null);
        }
        else {

            fragmentTransaction.replace(R.id.frameLayout1, new CharityFragment(charities,db), null).addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
    
    public void updateView()
    {
        CharityDao charityDao = db.getCharityDao();
        List<Charity> charities = charityDao.getAll();
        
        Log.e("view", "update view  " + charities.size());
        if(charities.size()==0) {
            return;
        }
        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if(fm.getFragments().isEmpty()) {

            fragmentTransaction.add(R.id.frameLayout1, new CharityFragment(charities,db), null);
        }
        else {

            fragmentTransaction.replace(R.id.frameLayout1, new CharityFragment(charities,db), null).addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}

