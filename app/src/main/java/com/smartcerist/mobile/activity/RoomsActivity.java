package com.smartcerist.mobile.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartcerist.mobile.R;
import com.smartcerist.mobile.adapter.HomesCustomAdapter;
import com.smartcerist.mobile.adapter.RoomsCustomAdapter;
import com.smartcerist.mobile.model.Home;
import com.smartcerist.mobile.model.Response;
import com.smartcerist.mobile.model.Room;
import com.smartcerist.mobile.util.NetworkUtil;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class RoomsActivity extends AppCompatActivity {

    private Home home;

    private List<Room> roomsList;
    RecyclerView recyclerView;

    View view;

    CompositeDisposable mSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent intent = getIntent();
        home = (Home) intent.getSerializableExtra("home");

        recyclerView = findViewById(R.id.roomsList);

        mSubscriptions = new CompositeDisposable();

        loadRoomsProcess();
    }

    public void loadRoomsProcess(){

        String homeId = home.get_id();

        mSubscriptions.add(NetworkUtil.getRetrofit().getRooms(homeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable error) {
        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getEmail());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void handleResponse(List<Room> rooms) {
        roomsList = rooms;


        RoomsCustomAdapter roomsCustomAdapter = new RoomsCustomAdapter(this, roomsList);

        GridLayoutManager mLayoutManager = new GridLayoutManager(this ,2);
        recyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(roomsCustomAdapter);
    }

    public void showSnackBarMessage(String message){
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, message,Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
