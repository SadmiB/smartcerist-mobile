package com.smartcerist.mobile.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcerist.mobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Fragment fragment;
        FragmentTransaction ft;
        switch (item.getItemId()) {
            case R.id.navigation_objects:
                fragment = new MonitorFragment();
                assert getFragmentManager() != null;
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.room_fragment, fragment);
                //ft.addToBackStack(null);
                ft.commit();

                return true;
            case R.id.navigation_dashboard:

                fragment = new AnalyticsFragment();
                assert getFragmentManager() != null;
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.room_fragment, fragment);
                //ft.addToBackStack(null);
                ft.commit();

                return true;
            case R.id.navigation_history:
                fragment = new HistoryFragment();
                assert getFragmentManager() != null;
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.room_fragment, fragment);
                //ft.addToBackStack(null);
                ft.commit();

                return true;
        }
        return false;
    };


    public RoomFragment() {
        // Required empty public constructor
    }


    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null)
            view = inflater.inflate(R.layout.fragment_room, container, false);

        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction()
                .replace(R.id.room_fragment,
                        new MonitorFragment()).commit();

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        return view;
    }

}
