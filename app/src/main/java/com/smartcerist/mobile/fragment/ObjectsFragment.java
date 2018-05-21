package com.smartcerist.mobile.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.adapter.ObjectsCustomAdapter;
import com.smartcerist.mobile.model.Object;
import com.smartcerist.mobile.model.ObjectsTypes;

import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ObjectsFragment extends Fragment {

    View view;
    RecyclerView mRecyclerView;
    CompositeDisposable mCompositeDisposable;

    List<Object> objectsList = Arrays.asList(
            new Object("Led3","/lights/led3", ObjectsTypes.led,"2001:4340:1010:22:2ca:29ff:fea2:9512","",""),
            new Object("Light","/lights/adc", ObjectsTypes.light,"2001:4340:1010:22:02a0:44ff:fe66:6b6e","",""),
            new Object("Temperature","/lights/temperature", ObjectsTypes.temperature,"2001:4340:1010:22:02f5:b6ff:fed2:8e61","",""),
            new Object("Power","/lights/power", ObjectsTypes.power,"2001:4340:1010:22:027f:08ff:fec7:69b0","",""),
            new Object("Presence","/lights/presence", ObjectsTypes.presence,"2001:4340:1010:22:02a0:44ff:fe66:6b6e","","")
            );


    public ObjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null)
            view = inflater.inflate(R.layout.fragment_objects, container, false);

        mCompositeDisposable = new CompositeDisposable();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.objects_list);


        ObjectsCustomAdapter objectsCustomAdapter = new ObjectsCustomAdapter(getActivity(), objectsList, mCompositeDisposable);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity() ,1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);
        mRecyclerView.setAdapter(objectsCustomAdapter);

        return view;
    }

}
