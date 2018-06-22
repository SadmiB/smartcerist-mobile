package com.smartcerist.mobile.activity;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.fragment.AnalyticsFragment;
import com.smartcerist.mobile.fragment.HistoryFragment;
import com.smartcerist.mobile.fragment.MonitorFragment;
import com.smartcerist.mobile.model.Room;

import java.util.Objects;

public class RoomActivity extends AppCompatActivity {
    private Room room;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                Fragment fragment;
                FragmentTransaction ft;
                switch (item.getItemId()) {
                    case R.id.navigation_objects:
                        fragment = new MonitorFragment();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.room_fragment, fragment);
                        //ft.addToBackStack(null);
                        ft.commit();

                        return true;
                    case R.id.navigation_dashboard:

                        fragment = new AnalyticsFragment();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.room_fragment, fragment);
                        //ft.addToBackStack(null);
                        ft.commit();

                        return true;
                    case R.id.navigation_history:
                        fragment = new HistoryFragment();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.room_fragment, fragment);
                        //ft.addToBackStack(null);
                        ft.commit();

                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        this.room = (Room) intent.getSerializableExtra("room");


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.room_fragment,
                        new MonitorFragment()).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
