package com.example.studygroupapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchRoom extends AppCompatActivity {

    DBUtil dbUtil;
    ListView rooms;
    ArrayList<String> listRoom;
    ArrayAdapter arrayAdapter;
    Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchroom);

        dbUtil = new DBUtil(this);
        listRoom = new ArrayList<>();
        rooms = (ListView) findViewById(R.id.listViewRooms);
        back = (Button) findViewById(R.id.btnBack);

        viewData();

        rooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = rooms.getItemAtPosition(position).toString();
                Toast.makeText(SearchRoom.this, "" + text, Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
            }
        });

    }

    private void viewData() {
        Cursor cursor = dbUtil.viewRooms();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data to show." , Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                listRoom.add(cursor.getString(1));
                listRoom.add(cursor.getString(2));
                listRoom.add(cursor.getString(3));
                listRoom.add(cursor.getString(4));
                listRoom.add(cursor.getString(5));
                listRoom.add(cursor.getString(6));
                listRoom.add(cursor.getString(7));
                listRoom.add(cursor.getString(8));
            }
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listRoom);
            rooms.setAdapter(arrayAdapter);
        }
    }
}
