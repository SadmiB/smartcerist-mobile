package com.smartcerist.mobile.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcerist.mobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment1 extends Fragment {


    public WelcomeFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_fragment1, container, false);
    }

}
