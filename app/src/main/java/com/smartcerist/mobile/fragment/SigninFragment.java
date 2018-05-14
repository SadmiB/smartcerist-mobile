package com.smartcerist.mobile.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartcerist.mobile.model.Response;
import com.smartcerist.mobile.model.User;
import com.smartcerist.mobile.util.NetworkUtil;
import com.smartcerist.mobile.util.UserPreferenceManager;
import com.smartcerist.mobile.util.Validation;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.activity.MainActivity;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment {

    private CompositeDisposable mSubscriptions;
    EditText et_email, et_password;
    Button signin_btn;
    TextInputLayout ti_email;
    TextInputLayout ti_password;
    ProgressBar progressBar;

    public SigninFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signin, container, false);

        mSubscriptions = new CompositeDisposable();

        initViews(view);

        signin_btn.setOnClickListener(v -> signIn(view));

        return view;
    }

    public void signIn(View v) {

        setError();

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        int err = 0;

        if(!Validation.validateEmail(email)){
            err++;
            ti_email.setError("Email should be valid !");
        }

        if(!Validation.validateFields(password)){
            err++;
            ti_password.setError("Password should not be empty !");
        }

        if(err == 0) {
            signinProcess(email, password);
        }
    }

    private void initViews(View view) {
        signin_btn = view.findViewById(R.id.sign_in);
        et_email = view.findViewById(R.id.email);
        et_password = view.findViewById(R.id.password);
        ti_email = view.findViewById(R.id.ti_email);
        ti_password = view.findViewById(R.id.ti_password);
        progressBar = view.findViewById(R.id.progressBar);
    }

    private void setError() {
        ti_email.setError(null);
        ti_password.setError(null);
    }

    private void signinProcess(String email, String password) {

        User user = new User(email, password);

        mSubscriptions.add(NetworkUtil.getRetrofit().signIn(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable error) {
        progressBar.setVisibility(View.GONE);

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


    private void handleResponse(Response response) {

        progressBar.setVisibility(View.GONE);

        if(response.getEmail() != null){
            UserPreferenceManager userPreferenceManager = new UserPreferenceManager(getActivity());
            userPreferenceManager.saveConnectedUser(response.getEmail(), response.getToken());
            Toast.makeText(getActivity(), response.getEmail(), Toast.LENGTH_SHORT).show();
            et_email.setText(null);
            et_password.setText(null);

            Intent intent = new Intent(this.getActivity(), MainActivity.class);
            startActivity(intent);
        } else {
            showSnackBarMessage(response.getMessage());
        }
    }



    private void showSnackBarMessage(String message) {
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

