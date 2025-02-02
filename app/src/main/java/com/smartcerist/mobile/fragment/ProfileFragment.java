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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartcerist.mobile.R;
import com.smartcerist.mobile.adapter.HomesCustomAdapter;
import com.smartcerist.mobile.model.Home;
import com.smartcerist.mobile.model.Response;
import com.smartcerist.mobile.model.User;
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
public class ProfileFragment extends Fragment {

    View view;
    TextView firstName;
    TextView lastName;
    TextView email;
    TextView country;
    TextView phone;
    private CompositeDisposable mSubscriptions;

    User userProfile;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null)
            view=inflater.inflate(R.layout.fragment_profile, container, false);

        mSubscriptions = new CompositeDisposable();
        firstName = (TextView) view.findViewById(R.id.profileFirstName_2);
        lastName = (TextView) view.findViewById(R.id.profileLastName_2);
        email = (TextView) view.findViewById(R.id.profileEmail_2);
        phone = (TextView) view.findViewById(R.id.profilePhone_2);
        country = (TextView) view.findViewById(R.id.profileCountry_2);
        loadProfileProcess();
        return view;
    }

    public void loadProfileProcess (){
        UserPreferenceManager userPreferenceManager = new UserPreferenceManager(getActivity());

        String token = userPreferenceManager.isConnected();

        mSubscriptions.add(NetworkUtil.getRetrofit(token).getUserProfile()
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

    private void handleResponse(User user) {
        Log.d("user", "handleResponse: " + user);
        userProfile = user;

        firstName.setText(userProfile.getFirstName());
        lastName.setText(userProfile.getLastName());
        email.setText(userProfile.getEmail());
        phone.setText(userProfile.getPhone());
        country.setText(userProfile.getCountry());

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
