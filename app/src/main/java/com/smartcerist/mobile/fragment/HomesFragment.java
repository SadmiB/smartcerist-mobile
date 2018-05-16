package com.smartcerist.mobile.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.adapter.HomesCustomAdapter;
import com.smartcerist.mobile.model.Home;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomesFragment extends Fragment {

    View view;

    RecyclerView recyclerView;

    List<Home> homesList = Arrays.asList(new Home("Sweet Home 1", R.drawable.home), new Home("Sweet Home 4", R.drawable.home), new Home("Sweet Home 2", R.drawable.home), new Home("Sweet Home 3", R.drawable.home));

    public HomesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(view == null)
            view = inflater.inflate(R.layout.fragment_homes, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.homesList);


        HomesCustomAdapter homesCustomAdapter = new HomesCustomAdapter(getActivity(), homesList);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity() ,1);
        recyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(homesCustomAdapter);

        return view;
    }

}
