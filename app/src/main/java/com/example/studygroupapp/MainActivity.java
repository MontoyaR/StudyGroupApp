package com.example.studygroupapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Variable declaration
    EditText username, password, repassword, email;
    Button signup, signin;
    DBUtil dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Variable initialization
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        email = (EditText) findViewById(R.id.email);
        signup = (Button) findViewById(R.id.btnRegister);
        signin = (Button) findViewById(R.id.btnLogin);
        dbUtil = new DBUtil(this);

        // Registration functionality
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String e_mail = email.getText().toString();

                //Exception handling for when
                if(user.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill out all the fields.", Toast.LENGTH_SHORT).show();
                } else {
                    // If the password and repassword EditText fields are equal then,
                    if(pass.equals(repass)) {

                        // Check that the username does not currently exist in the database.
                        Boolean verifyUser = dbUtil.checkUsername(user);

                        // If the username comes back as false, meaning the username does not exist
                        if (verifyUser == false) {

                            // Then the username and password would be insert to the database.
                            Boolean insert = dbUtil.insertData(user, pass, e_mail);

                            // If the data insertion was successful then output a message.
                            if(insert == true) {
                                Toast.makeText(MainActivity.this, "Registered successfully.", Toast.LENGTH_SHORT).show();

                                // Once a user has successfully registered, the Dashboard screen will be displayed.
                                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Registered failed.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "User already exists! Sign in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Passwords do not match, double check spelling!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        /**
         * This method sets an onClick listener to the signin button to change screens.
         */
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}