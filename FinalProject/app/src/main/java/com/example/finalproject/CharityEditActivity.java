package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CharityEditActivity extends AppCompatActivity {

    AppDatabase db;
    Charity charity;
    EditText titleET,aboutET;
    Button editButton,deleteCharityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_edit);
        titleET=findViewById(R.id.charityTitle);
        aboutET=findViewById(R.id.charityAbout);
        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCharity();

            }
        });
        deleteCharityButton = findViewById(R.id.deleteCharityButton);
        deleteCharityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCharity();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "AppDatabase").enableMultiInstanceInvalidation().allowMainThreadQueries().build();
        CharityDao charityDao = db.getCharityDao();

        charity = charityDao.findById(intent.getIntExtra("charity_id",-2));
        titleET.setText(charity.getTitle());
        aboutET.setText(charity.getAbout());

    }
    private void deleteCharity()
    {
        DonationDao dDao = db.getDonationDao();
        dDao.deleteByCharity_id(this.charity.getCharity_id());

        CharityDao charityDao = db.getCharityDao();
        charityDao.delete(this.charity);
        Toast toast = Toast.makeText(getApplicationContext(),
                "Successfully deleted charity ",
                Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user_id",-1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);

    }

    private void editCharity() {
        if (titleET.getText().toString() != null && aboutET.getText().toString() != null)
        {
            charity.setTitle(titleET.getText().toString());
            charity.setAbout(aboutET.getText().toString());
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Successfully editted  charity ",
                    Toast.LENGTH_SHORT);
            toast.show();
        }


        CharityDao charityDao = db.getCharityDao();
        charityDao.updateCharities(charity);
    }
}