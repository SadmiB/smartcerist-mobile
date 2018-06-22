package com.smartcerist.mobile.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.adapter.MonitorPagerAdapter;
import com.smartcerist.mobile.adapter.SignPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonitorFragment extends Fragment {


    private View view;

    public MonitorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null)
            view = inflater.inflate(R.layout.fragment_monitor, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        final MonitorPagerAdapter adapter = new MonitorPagerAdapter
                (getFragmentManager(), 2);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}
