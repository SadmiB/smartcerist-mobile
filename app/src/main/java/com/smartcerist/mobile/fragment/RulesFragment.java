package com.smartcerist.mobile.fragment;


import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.activity.RoomsActivity;
import com.smartcerist.mobile.adapter.CamerasCustomAdapter;
import com.smartcerist.mobile.adapter.RoomsCustomAdapter;
import com.smartcerist.mobile.adapter.RulesCustomAdapter;
import com.smartcerist.mobile.model.Home;
import com.smartcerist.mobile.model.Rule;
import com.smartcerist.mobile.util.NetworkUtil;

import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


/**
 * A simple {@link Fragment} subclass.
 */
public class RulesFragment extends BottomSheetDialogFragment {

    View view;
    RecyclerView mRecyclerView;
    private CompositeDisposable mSubscriptions;

    List<Rule> rulesList;
    public RulesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null)
            view = inflater.inflate(R.layout.fragment_rules, container, false);

        mRecyclerView = view.findViewById(R.id.rules_list);

        RoomsActivity activity = (RoomsActivity)getActivity();
        assert activity != null;
        Home home =  activity.getHome();
        rulesList = home.getRules();

        RulesCustomAdapter rulesCustomAdapter= new RulesCustomAdapter(getActivity(), home.get_id() , rulesList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);
        mRecyclerView.setAdapter(rulesCustomAdapter);

        return view;
    }

}
