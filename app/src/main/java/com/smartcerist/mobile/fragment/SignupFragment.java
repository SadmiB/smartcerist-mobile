package com.smartcerist.mobile.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartcerist.mobile.R;
import com.smartcerist.mobile.activity.MainActivity;
import com.smartcerist.mobile.model.Response;
import com.smartcerist.mobile.model.User;
import com.smartcerist.mobile.util.NetworkUtil;
import com.smartcerist.mobile.util.UserPreferenceManager;
import com.smartcerist.mobile.util.Validation;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {


    private CompositeDisposable mSubscriptions;
    EditText et_first_name, et_last_name,et_email, et_password, et_confirm_password;
    Button signun_btn;
    TextInputLayout  ti_first_name, ti_last_name, ti_email, ti_password, ti_confirm_password;

    ProgressDialog progressDialog;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        mSubscriptions = new CompositeDisposable();
        initViews(view);
        signun_btn.setOnClickListener(v -> signUp(view));

        return view;
    }

    public void signUp(View v) {

        setError();

        String firstName = et_first_name.getText().toString();
        String lastName = et_last_name.getText().toString();
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
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Signing up..");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            progressDialog.show();

            signupProcess(firstName, lastName, email, password);
        }
    }

    private void setError() {
        ti_first_name.setError(null);
        ti_last_name.setError(null);
        ti_email.setError(null);
        ti_password.setError(null);
        ti_confirm_password.setError(null);
    }

    private void initViews(View view) {
        signun_btn = view.findViewById(R.id.sign_up);
        et_first_name = view.findViewById(R.id.first_name);
        et_last_name = view.findViewById(R.id.last_name);
        et_email = view.findViewById(R.id.email);
        et_password = view.findViewById(R.id.password);
        et_confirm_password = view.findViewById(R.id.confirm_password);
        ti_first_name = view.findViewById(R.id.ti_first_name);
        ti_last_name = view.findViewById(R.id.ti_last_name);
        ti_email = view.findViewById(R.id.ti_email);
        ti_password = view.findViewById(R.id.ti_password);
        ti_confirm_password = view.findViewById(R.id.ti_confirm_password);
    }

    private void signupProcess(String firstName, String lastName, String email, String password) {


        User user = new User(firstName, lastName ,email, password);

        mSubscriptions.add(NetworkUtil.getRetrofit().signUp(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable error) {
        progressDialog.dismiss();
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

        progressDialog.dismiss();
        if(response.getEmail() != null){
            UserPreferenceManager userPreferenceManager = new UserPreferenceManager(getActivity());
            userPreferenceManager.saveConnectedUser(response.getEmail(), response.getToken());
            Toast.makeText(getActivity(), "welcome...", Toast.LENGTH_SHORT).show();
            et_first_name.setText(null);
            et_last_name.setText(null);
            et_email.setText(null);
            et_password.setText(null);
            et_confirm_password.setText(null);

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
