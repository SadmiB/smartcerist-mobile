package com.smartcerist.mobile.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.adapter.CamerasCustomAdapter;
import com.smartcerist.mobile.model.Camera;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CamerasFragment extends Fragment {

    RecyclerView mRecyclerView;
    View view;
    List<Camera> camerasList = Arrays.asList(
            new Camera("Dahua", "rtsp://admin:smartBuilding2017@192.168.8.102/cam/realmonitor?channel=1&subtype=1")
            , new Camera("Dahua3", "rtsp://admin:smartBuilding2017@192.168.8.102/cam/realmonitor?channel=1&subtype=1")
    );

    public CamerasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null)
            view = inflater.inflate(R.layout.fragment_cameras, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.cameras_list);

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

}
