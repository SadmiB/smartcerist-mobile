package com.smartcerist.mobile.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartcerist.mobile.R;
import com.smartcerist.mobile.adapter.HomesCustomAdapter;
import com.smartcerist.mobile.adapter.NotificationsCustomAdapter;
import com.smartcerist.mobile.model.Home;
import com.smartcerist.mobile.model.Notification;
import com.smartcerist.mobile.model.Response;
import com.smartcerist.mobile.util.NetworkUtil;
import com.smartcerist.mobile.util.UserPreferenceManager;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    View view;

    RecyclerView recyclerView;

    private CompositeDisposable mSubscriptions;

    List<Notification> notificationsList ;


    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(view == null)
            view = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.notificationsList);
        mSubscriptions = new CompositeDisposable();

        loadNotificationsProcess();

        return view;
    }


    public void loadNotificationsProcess(){


        UserPreferenceManager userPreferenceManager = new UserPreferenceManager(getActivity());
        String token = userPreferenceManager.isConnected();

        mSubscriptions.add(NetworkUtil.getRetrofit(token).getNotifications()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(List<Notification> notifications) {
        Log.d("notifications", "handleResponse: " + notifications);
        notificationsList = notifications;

        NotificationsCustomAdapter notificationsCustomAdapter = new NotificationsCustomAdapter(getActivity(), notificationsList);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity() ,1);
        recyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(notificationsCustomAdapter);
    }

    public void showSnackBarMessage(String message){
        if (getView() != null) {
            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.isDisposed();
    }

}
