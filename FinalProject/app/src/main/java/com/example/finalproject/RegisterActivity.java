package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    AppDatabase db;
    EditText usernameEt,passwordEt,confirmPasswordEt,usernameErrorEt,passwordErrorEt;
    Button rB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEt = findViewById(R.id.usernameText2);
        passwordEt = findViewById(R.id.passwordText2);
        confirmPasswordEt = findViewById(R.id.confirmPasswordText);

        usernameErrorEt = findViewById(R.id.usernameErrorText);
        usernameErrorEt.setVisibility(View.INVISIBLE);
        passwordErrorEt = findViewById(R.id.passwordErrorText);
        passwordErrorEt.setVisibility(View.INVISIBLE);

        rB = findViewById(R.id.registerButton2);
        rB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
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

    public void register()
    {
        UserDao userDao = db.getUserDao();
        User existingUser = userDao.findByUsername(usernameEt.getText().toString());
        //if there are no users with the same username
        if(existingUser==null)
        {
            usernameErrorEt.setVisibility(View.INVISIBLE);
            //if passwords do not match, tell user and deny profile creation
            if( ! passwordEt.getText().toString().equals(confirmPasswordEt.getText().toString() ))
            {
                passwordErrorEt.setVisibility(View.VISIBLE);
                return;
            }
            else
            {
                passwordErrorEt.setVisibility(View.INVISIBLE);
                User user = new User(usernameEt.getText().toString(),PasswordHasher.generateStrongPasswordHash(passwordEt.getText().toString()));
                userDao.insertAll(user);
                Toast.makeText(this, "successfully registered ",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else //if there is a user record stored in the database with the same username, we tell the current user and deny the profile creation.
        {
            usernameErrorEt.setVisibility(View.VISIBLE);
        }

    }
}