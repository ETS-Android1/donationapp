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

    EditText uEt,pEt,errorT;
    Button lB,rB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "AppDatabase").enableMultiInstanceInvalidation().allowMainThreadQueries().addMigrations(AppDatabase.MIGRATION_1_2).build();

         uEt = findViewById(R.id.usernameText);
         pEt = findViewById(R.id.passwordText);
         lB = findViewById(R.id.loginButton);
         rB = findViewById(R.id.registerButton);
         errorT = findViewById(R.id.errorText);
         errorT.setVisibility(View.INVISIBLE);

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
        //empty fields? return
        if(uEt.getText().toString().isEmpty() || pEt.getText().toString().isEmpty())
        {
            return;
        }
        //admin logging in
        if(uEt.getText().toString().equals("admin") && pEt.getText().toString().equals("admin"))
        {
            errorT.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Successfully logged in as Admin",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user_id",-1);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent);
        }

        UserDao userDao = db.getUserDao();
        User user = userDao.findByUsername(uEt.getText().toString());
        if(user==null)
        {
            errorT.setVisibility(View.VISIBLE);
            return;
        }
        if(  PasswordHasher.validatePassword( pEt.getText().toString(), user.getPassword_hash() )  )
        {
            errorT.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Successfully logged in",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user_id",user.getUser_id());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent);

        }
        else
        {
            errorT.setVisibility(View.VISIBLE);
        }
    }
}