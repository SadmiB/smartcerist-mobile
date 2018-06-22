package com.smartcerist.mobile.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.smartcerist.mobile.fragment.CamerasFragment;
import com.smartcerist.mobile.fragment.ObjectsFragment;

public class MonitorPagerAdapter extends FragmentStatePagerAdapter {

    int PAGES_NUM;


    public MonitorPagerAdapter(FragmentManager fm, int PAGES_NUM) {
        super(fm);
        this.PAGES_NUM = PAGES_NUM;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ObjectsFragment();
            case 1:
                return new CamerasFragment();
            default:
                return null;

        }
    }


    @Override
    public int getCount() {
        return PAGES_NUM;
    }
}
