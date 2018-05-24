package com.smartcerist.mobile.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.adapter.HomesCustomAdapter;
import com.smartcerist.mobile.model.Home;
import com.smartcerist.mobile.util.NetworkUtil;
import com.smartcerist.mobile.util.UserPreferenceManager;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomesFragment extends Fragment {

    View view;

    RecyclerView recyclerView;
    ProgressBar progressBar;

    private CompositeDisposable mSubscriptions;

    List<Home> homesList ;


    public HomesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(view == null)
            view = inflater.inflate(R.layout.fragment_homes, container, false);

        recyclerView = view.findViewById(R.id.homesList);
        progressBar = view.findViewById(R.id.progressBar);
        mSubscriptions = new CompositeDisposable();

        loadHomesProcess();

        return view;
    }


    public void loadHomesProcess(){


        UserPreferenceManager userPreferenceManager = new UserPreferenceManager(getActivity());
        String token = userPreferenceManager.isConnected();

        mSubscriptions.add(NetworkUtil.getRetrofit(token).getHomes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable error) {
        if (error instanceof HttpException) {

            try {

                String errorBody = Objects.requireNonNull(((HttpException) error).response().errorBody()).string();
                showSnackBarMessage(errorBody);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
        progressBar.setVisibility(View.GONE);
    }

    private void handleResponse(List<Home> homes) {
        Log.d("homes", "handleResponse: " + homes);
        homesList = homes;

        HomesCustomAdapter homesCustomAdapter = new HomesCustomAdapter(getActivity(), homesList);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity() ,1);
        recyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(homesCustomAdapter);
        progressBar.setVisibility(View.GONE);
    }

    public void showSnackBarMessage(String message){
        if (getView() != null) {
            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.isDisposed();
    }

}
