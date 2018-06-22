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
import com.smartcerist.mobile.adapter.ObjectsCustomAdapter;
import com.smartcerist.mobile.model.Object;
import com.smartcerist.mobile.model.ObjectsTypes;
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
public class ObjectsFragment extends Fragment {

    View view;
    RecyclerView mRecyclerView;
    CompositeDisposable mCompositeDisposable;

    List<Object> objectsList;


    /*

    List<Object> objectsList = Arrays.asList(
            new Object("Led3","/lights/led3", ObjectsTypes.led,"2001:4340:1010:22:2ca:29ff:fea2:9512","",""),
            new Object("Light","/lights/adc", ObjectsTypes.light,"2001:4340:1010:22:02a0:44ff:fe66:6b6e","",""),
            new Object("Ventilator","/lights/ventilator", ObjectsTypes.ventilator,"2001:4340:1010:22:02f5:b6ff:fed2:8e61","",""),
            new Object("Temperature","/lights/temperature", ObjectsTypes.temperature,"2001:4340:1010:22:02f5:b6ff:fed2:8e61","",""),
            new Object("Power","/lights/power", ObjectsTypes.power,"2001:4340:1010:22:027f:08ff:fec7:69b0","",""),
            new Object("Presence","/lights/presence", ObjectsTypes.presence,"2001:4340:1010:22:02a0:44ff:fe66:6b6e","","")
            );
    */


    public ObjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null)
            view = inflater.inflate(R.layout.fragment_objects, container, false);

        mCompositeDisposable = new CompositeDisposable();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.objects_list);


        RoomActivity activity = (RoomActivity) getActivity();
        assert activity != null;
        String[] objectsIds  = activity.getRoom().getObjects();

        objectsList = new ArrayList<>();

        getObjects(objectsIds);


        ObjectsCustomAdapter objectsCustomAdapter = new ObjectsCustomAdapter(getActivity(), objectsList);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity() ,1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);
        mRecyclerView.setAdapter(objectsCustomAdapter);

        return view;
    }



    private void getServerByObjectIdProcess(String objectId) {

        mCompositeDisposable.add(NetworkUtil.getRetrofit().getServerByObjectId(objectId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(server -> {
                      handleResponse(server, objectId);
                    },
                        this::handleError
                ));
    }

    private void handleResponse(Server server, String objectId) {
        objectsList.add(getObject(server, objectId));

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

    public void showSnackBarMessage(String message){
        if (getView() != null) {
            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }

    private void getObjects(String[] objectsIds) {
        for (String objectId: objectsIds) {
            getServerByObjectIdProcess(objectId);
        }
    }

    private Object getObject(Server server, String objectId) {
        Object theObject = null;
        for (Object object: server.getBeacons().get(0).getObjects() ) {
            if(object.get_id().equals(objectId)) {
                theObject = new Object(
                        object.get_id(),
                        object.getName(),
                        object.getPath(),
                        object.getType(),
                        server.getBeacons().get(0).getIpv6(),
                        server.getIpv6(),
                        server.getLipv6(),
                        server.getIpv4(),
                        server.getLipv6(),
                        object.getMin_threshold(),
                        object.getMax_threshold()
                        );
                break;
            }
        }
        return theObject;
    }

}
