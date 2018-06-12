package com.smartcerist.mobile.fragment;


import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.smartcerist.mobile.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RulesFragment extends Fragment {

    View view;
    List rulesList;

    public RulesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null)
            view = inflater.inflate(R.layout.fragment_rules, container, false);

        return view;
    }

}
