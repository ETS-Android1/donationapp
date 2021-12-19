package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserEditing extends AppCompatActivity {

    AppDatabase db;
    int currentUser_id;
    EditText usernameET,passwordET,passwordConfirmET,errorET;
    Button editB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_editing);

        usernameET=findViewById(R.id.usernameET);
        passwordET=findViewById(R.id.passwordET);
        passwordConfirmET=findViewById(R.id.passwordConfirmET);
        errorET=findViewById(R.id.errorET);
        editB=findViewById(R.id.editButton2);
        editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInformation();
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
        UserDao userDao = db.getUserDao();
        User user = userDao.findById(currentUser_id);
        usernameET.setText(user.getUsername());
    }
    public void validateInformation()
    {
        UserDao userDao = db.getUserDao();
        User user = userDao.findById(currentUser_id);
        //empty fields? return
        if(!passwordET.getText().toString().isEmpty() &&  !passwordConfirmET.getText().toString().isEmpty())
        {
        }
        if(  PasswordHasher.validatePassword( passwordET.getText().toString(), user.getPassword_hash() )&&
                PasswordHasher.validatePassword( passwordConfirmET.getText().toString(), user.getPassword_hash() )  )
        {
            errorET.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Successfully edited",Toast.LENGTH_LONG).show();
            user.setUsername(usernameET.getText().toString());

            userDao.updateUsers(user);
        }
        else
        {
            errorET.setVisibility(View.VISIBLE);
        }
    }
}