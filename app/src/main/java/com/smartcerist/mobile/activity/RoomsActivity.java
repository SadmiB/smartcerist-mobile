package com.smartcerist.mobile.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartcerist.mobile.R;
import com.smartcerist.mobile.adapter.HomesCustomAdapter;
import com.smartcerist.mobile.adapter.RoomsCustomAdapter;
import com.smartcerist.mobile.fragment.RulesFragment;
import com.smartcerist.mobile.model.Home;
import com.smartcerist.mobile.model.Response;
import com.smartcerist.mobile.model.Room;
import com.smartcerist.mobile.util.NetworkUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class RoomsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;

    Home home;

    private Room room;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent intent = getIntent();
        home = (Home) intent.getSerializableExtra("home");

        recyclerView = findViewById(R.id.roomsList);
        progressBar = findViewById(R.id.progressBar);

        List<Room> roomsList = home.getRooms();

        setRoom(roomsList.get(0));

        RoomsCustomAdapter roomsCustomAdapter = new RoomsCustomAdapter(this, roomsList);

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(roomsCustomAdapter);

        progressBar.setVisibility(View.GONE);

    }

    public Home getHome() {
        return home;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void showRules(View view) {
        RulesFragment rulesFragment = new RulesFragment();
        rulesFragment.show(getSupportFragmentManager(), rulesFragment.getTag());
    }
}
