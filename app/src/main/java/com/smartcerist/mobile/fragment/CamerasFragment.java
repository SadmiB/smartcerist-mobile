package com.smartcerist.mobile.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.activity.RoomActivity;
import com.smartcerist.mobile.adapter.CamerasCustomAdapter;
import com.smartcerist.mobile.model.Camera;
import com.smartcerist.mobile.model.Room;
import com.smartcerist.mobile.model.Server;
import com.smartcerist.mobile.util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * A simple {@link Fragment} subclass.
 */
public class CamerasFragment extends Fragment {

    RecyclerView mRecyclerView;
    View view;
    CompositeDisposable mCompositeDisposable;
    List<Camera> camerasList;

    /*
    List<Camera> camerasList = Arrays.asList(
            new Camera("Dahua", "rtsp://admin:smartBuilding2017@10.0.88.122/cam/realmonitor?channel=1&subtype=1")
            , new Camera("Dahua3", "rtsp://admin:smartBuilding2017@10.0.88.122/cam/realmonitor?channel=1&subtype=1")
    );
    */

    public CamerasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null)
            view = inflater.inflate(R.layout.fragment_cameras, container, false);

        mRecyclerView = view.findViewById(R.id.cameras_list);

        mCompositeDisposable = new CompositeDisposable();
        camerasList = new ArrayList<>();


        RoomActivity activity = (RoomActivity)getActivity();

        assert activity!=null;
        String[] camerasIds = activity.getRoom().getCameras();

        getCameras(camerasIds);

        CamerasCustomAdapter camerasCustomAdapter = new CamerasCustomAdapter(getActivity(), camerasList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);
        mRecyclerView.setAdapter(camerasCustomAdapter);

        return view;
    }

    private void getCameras(String[] camerasIds) {
        for (String cameraId: camerasIds) {
            getCameraByCameraId(cameraId);
        }
    }

    private void getCameraByCameraId(String cameraId) {
        mCompositeDisposable.add(NetworkUtil.getRetrofit().getCameraByCameraId(cameraId)
        .observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(server -> {
                            handleResponse(server, cameraId);
                        },
                        this::handleError
                ));
    }

    private void handleResponse(Server server, String cameraId) {
        Camera camera = server.getCameras().get(0);
        camera.setIpv4(server.getIpv4());
        camera.setIpv6(server.getIpv6());

        camerasList.add(camera);
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
            Toast.makeText(getActivity(), "error :" + error, Toast.LENGTH_SHORT).show();
        }
    }

    private void showSnackBarMessage(String message){
        if (getView() != null) {
            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }


}
