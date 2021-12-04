package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    AppDatabase db;

    EditText uEt,pEt;
    Button lB,rB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "AppDatabase").enableMultiInstanceInvalidation().allowMainThreadQueries().build();

         uEt = findViewById(R.id.usernameText);
         pEt = findViewById(R.id.passwordText);
         lB = findViewById(R.id.loginButton);
         rB = findViewById(R.id.registerButton);

        lB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCredentials();
            }
        });
        rB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { launchRegisterActivity();}
        });
    }

    private void launchRegisterActivity()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    }

    public void verifyCredentials()
    {
        UserDao userDao = db.getUserDao();
        User user = userDao.findByUsername(uEt.getText().toString());
        if(user==null)
        {
            Toast.makeText(this, "Could not find username",Toast.LENGTH_SHORT).show();
        }
        if(  PasswordHasher.validatePassword( pEt.getText().toString(), user.getPassword_hash() )  )
        {
            Toast.makeText(this, "Successfully logged in",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user_id",user.getUser_id());
            this.startActivity(intent);

        }
        else
        {
            Toast.makeText(this, "Wrong password",Toast.LENGTH_SHORT).show();
        }
    }
}