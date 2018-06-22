package com.smartcerist.mobile.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.model.Object;
import com.smartcerist.mobile.model.Server;
import com.smartcerist.mobile.util.NetworkUtil;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ObjectSettingsFragment extends DialogFragment {

    View view;
    CompositeDisposable mCompositeDisposable;

    public static final String TAG = ObjectSettingsFragment.class.getSimpleName();

    public ObjectSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null)
            view = inflater.inflate(R.layout.fragment_object_settings, container, false);

        mCompositeDisposable = new CompositeDisposable();

        assert getArguments() != null;
        Object object = (Object) getArguments().getSerializable("object");


        NumberPicker min_np = view.findViewById(R.id.min_np);
        NumberPicker max_np = view.findViewById(R.id.max_np);

        assert object != null;
        min_np.setMinValue(0);
        min_np.setMaxValue(1000);

        if(object.getMin_threshold()!=null)
            min_np.setValue(Integer.parseInt(object.getMin_threshold()));

        max_np.setMinValue(0);
        max_np.setMaxValue(1000);

        if(object.getMax_threshold()!=null)
            max_np.setValue(Integer.parseInt(object.getMax_threshold()));

        min_np.setWrapSelectorWheel(true);
        max_np.setWrapSelectorWheel(true);

        /********************/

        Button save_btn = view.findViewById(R.id.save_btn);

        save_btn.setOnClickListener(v -> {
            object.setMin_threshold(String.valueOf(min_np.getValue()));
            object.setMax_threshold(String.valueOf(max_np.getValue()));
            saveObjectProcess(object);
        });


        return view;
    }

    private void saveObjectProcess(Object object) {
        mCompositeDisposable.add(NetworkUtil.getRetrofit().updateObjectSettings(object.getServer_id(), object.getBeacon_id(), object.get_id(), object)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(Server server) {
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction()
                .remove(this)
                .commit();
    }

    private void handleError(Throwable error){
        if (error instanceof HttpException) {

            try {

                String errorBody = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    errorBody = Objects.requireNonNull(((HttpException) error).response().errorBody()).string();
                }
                showSnackBarMessage(errorBody);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    public void showSnackBarMessage(String message){
        if (getView() != null) {
            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }

}
