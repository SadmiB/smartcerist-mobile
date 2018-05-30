package com.smartcerist.mobile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.model.Home;
import com.smartcerist.mobile.model.Room;

public class RoomActivity extends AppCompatActivity {
    private Room room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        Intent intent = getIntent();
        this.room = (Room) intent.getSerializableExtra("room");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public Room getRoom(){
        return this.room;
    }
}
