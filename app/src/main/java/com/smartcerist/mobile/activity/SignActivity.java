package com.smartcerist.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.adapter.SignPagerAdapter;
import com.smartcerist.mobile.util.UserPreferenceManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign);

        UserPreferenceManager userPreferenceManager = new UserPreferenceManager(this);
        if (userPreferenceManager.isConnected() != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final SignPagerAdapter adapter = new SignPagerAdapter
                (getSupportFragmentManager(), 2);
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


    }

    public void sign(View view){
        Intent intent = new Intent(SignActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
