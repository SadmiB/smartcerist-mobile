package com.smartcerist.mobile.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.arturogutierrez.Badges;
import com.github.arturogutierrez.BadgesNotSupportedException;
import com.smartcerist.mobile.R;
import com.smartcerist.mobile.fragment.HomesFragment;
import com.smartcerist.mobile.fragment.NotificationsFragment;
import com.smartcerist.mobile.fragment.ProfileFragment;
import com.smartcerist.mobile.model.Notification;
import com.smartcerist.mobile.util.NetworkUtil;
import com.smartcerist.mobile.util.UserPreferenceManager;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CompositeDisposable mSubscriptions;
    List<Notification> notificationsList;
    ConstraintLayout notificationCount ;
    TextView textCartItemCount;
    int mCartItemCount = 10;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFragmentManager().beginTransaction()
                .replace(R.id.main_fragment,
                        new HomesFragment()).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        TextView email_tv = navigationView.getHeaderView(0).findViewById(R.id.email);
        UserPreferenceManager userPreferenceManager = new UserPreferenceManager(this);
        email_tv.setText(userPreferenceManager.getConnectedUser());


        token = userPreferenceManager.isConnected();

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item1 = menu.findItem(R.id.action_notification);
        View actionView = MenuItemCompat.getActionView(item1);
        textCartItemCount = (TextView) actionView.findViewById(R.id.textView_notification);

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hello World");
                mSubscriptions = new CompositeDisposable();
                mSubscriptions.add(NetworkUtil.getRetrofit(token).getNotificationsNotSeen()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
            }
            private void handleResponse(List<Notification> notifications) {
                Log.d("notifications", "handleResponse: " + notifications);
                notificationsList = notifications;
                mCartItemCount= notifications.size();
                Log.d("notificationNumber", "notificationsList.size : " + notificationsList.size());
                if (mCartItemCount == 0) {
                    if (textCartItemCount.getVisibility() != View.GONE) {
                        textCartItemCount.setVisibility(View.GONE);
                    }
                } else {
                    textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                    if (textCartItemCount.getVisibility() != View.VISIBLE) {
                        textCartItemCount.setVisibility(View.VISIBLE);
                    }
                }

                try {
                    Badges.setBadge(MainActivity.this, mCartItemCount);
                }catch (BadgesNotSupportedException e){
                    //Toast.makeText(MainActivity.this,"that was an error!", Toast.LENGTH_SHORT).show();
                }
                //notificationNumber.setText(""+notificationsList.size());

            }

            private void handleError(Throwable error) {
                if (error instanceof HttpException) {

                    try {

                        String errorBody = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                            errorBody = Objects.requireNonNull(((HttpException) error).response().errorBody()).string();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, 0, 10000);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(item1);
            }
        });

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_local) {
            return true;
        }
        if (id == R.id.action_internet) {
            return true;
        }
        if (id == R.id.action_bluetooth) {
            return true;
        }
        if (id == R.id.action_notification) {
            Toast.makeText(MainActivity.this, "notifications", Toast.LENGTH_LONG).show();
            Fragment fragment = new NotificationsFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
            return true;
        }
        if (id == R.id.textView_notification) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        if (id == R.id.nav_homes) {
            fragment = new HomesFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_notifications) {
            fragment = new NotificationsFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }else if (id == R.id.nav_account) {
            fragment = new ProfileFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_signout) {
            UserPreferenceManager userSharedPref = new UserPreferenceManager(this);
            userSharedPref.disconnectUser();
            Intent intent = new Intent(MainActivity.this, SignActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
