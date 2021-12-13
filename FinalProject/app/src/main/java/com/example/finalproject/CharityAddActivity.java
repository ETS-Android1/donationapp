package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class CharityAddActivity extends AppCompatActivity {
    AppDatabase db;
    Charity charity;
    EditText titleET,aboutET;
    Button insertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_add);
        titleET=findViewById(R.id.charityTitle);
        aboutET=findViewById(R.id.charityAbout);
        insertButton = findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertCharity();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "AppDatabase").enableMultiInstanceInvalidation().allowMainThreadQueries().build();
    }

    public void insertCharity()
    {

        if(titleET.getText().toString()!=null && aboutET.getText().toString()!=null) {
            CharityDao charityDao = db.getCharityDao();
            List<Charity> c = charityDao.queryTitle(titleET.getText().toString());
            if(c.isEmpty())
            {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Charity with the same name already exists",
                        Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            charity = new Charity(titleET.getText().toString(),aboutET.getText().toString());
            charity.setTitle(titleET.getText().toString());
        }

        CharityDao charityDao = db.getCharityDao();
        charityDao.insertAll(charity);
        

    }
}