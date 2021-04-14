package com.example.studygroupapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    Button createRoom;
    Button searchRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        createRoom = (Button) findViewById(R.id.btnCreateRoom);
        searchRoom = (Button) findViewById(R.id.btnSearchRoom);

        // Login functionality
        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateRoom.class);
                startActivity(intent);
            }
        });

        searchRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchRoom.class);
                startActivity(intent);
            }
        });


    }
}