package com.example.studygroupapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CreateRoom extends AppCompatActivity {

    DBUtil dbUtil;
    CalendarView calendarView;
    EditText roomName, password, subject, university, professor, description;
    Switch access;
    Button save, cancel;
    String selectedDate, selectedAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createroom);

        roomName = (EditText) findViewById(R.id.editTextRoomName);
        password = (EditText) findViewById(R.id.editTextPassword);
        subject = (EditText) findViewById(R.id.editTextSubject);
        university = (EditText) findViewById(R.id.editTextUniversity);
        professor = (EditText) findViewById(R.id.editTextProfessor);
        description = (EditText) findViewById(R.id.editTextDescription);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        access = (Switch) findViewById(R.id.switchAccess);
        save = (Button) findViewById(R.id.btnSave);
        cancel = (Button) findViewById(R.id.btnCancel);
        dbUtil = new DBUtil(this);
        selectedAccess = "public";

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);
                System.out.println(selectedDate);
            }
        });

        access.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    selectedAccess = "private";

                } else {
                    selectedAccess = "public";

                }
            }
        });

        /**
         * This method sets an onClick listener to the cancel button to change screens.
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
            }
        });

        /**
         *
         */
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = roomName.getText().toString();
                String locked = selectedAccess;
                String _password = password.getText().toString();
                String _subject = subject.getText().toString();
                String _university = university.getText().toString();
                String _professor = professor.getText().toString();
                String _description = description.getText().toString();
                String date = selectedDate;

                if (name.isEmpty() || locked.isEmpty() || _subject.isEmpty()
                        || _university.isEmpty() || _professor.isEmpty() || _description.isEmpty() || date.isEmpty()) {
                    Toast.makeText(CreateRoom.this, "Please fill out all the fields.", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean insert = dbUtil.insertRoomData(name, locked, _password, _subject, _university, _professor,
                            _description, date);
                    if(insert == true) {
                        Toast.makeText(CreateRoom.this, "Registered successfully.", Toast.LENGTH_SHORT).show();

                        // Once a user has successfully registered, the Dashboard screen will be displayed.
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CreateRoom.this, "Registered failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
