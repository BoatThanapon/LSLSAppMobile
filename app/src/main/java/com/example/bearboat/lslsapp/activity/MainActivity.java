package com.example.bearboat.lslsapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.bearboat.lslsapp.R;
import com.example.bearboat.lslsapp.adapter.SectionPageAdapter;
import com.example.bearboat.lslsapp.fragment.JobsFragment;
import com.example.bearboat.lslsapp.fragment.MapsFragment;
import com.example.bearboat.lslsapp.fragment.ProfileFragment;
import com.example.bearboat.lslsapp.tool.MySharedPreference;
import com.example.bearboat.lslsapp.tool.UserInterfaceUtils;
import com.example.bearboat.lslsapp.tool.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    private MapsFragment mapsFragment;
    private JobsFragment jobsFragment;
    private ProfileFragment profileFragment;
    private BottomNavigationView bottomNavigationView;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBottomNavigation();
        initViewPager();
        setupViewPager();
        initInstance();
    }

    private void initBottomNavigation() {

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_maps:
                                viewPager.setCurrentItem(0);
                                break;

                            case R.id.action_jobs:
                                viewPager.setCurrentItem(1);
                                break;

                            case R.id.action_profile:
                                viewPager.setCurrentItem(2);
                                break;

                        }
                        return true;
                    }
                });
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(R.string.action_bar_location);
                        break;
                    case 1:
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(R.string.action_bar_jobs);
                        break;
                    case 2:
                        if (getSupportActionBar() != null)
                            getSupportActionBar().setTitle(R.string.action_bar_profile);
                        break;
                }
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager() {
        mapsFragment = new MapsFragment();
        jobsFragment = new JobsFragment();
        profileFragment = new ProfileFragment();

        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

        adapter.addFragment(mapsFragment);
        adapter.addFragment(jobsFragment);
        adapter.addFragment(profileFragment);

        viewPager.setAdapter(adapter);
    }


    private void initInstance() {

        getSupportActionBar().setTitle(R.string.action_bar_location);
    }

    @Override
    public void onBackPressed() {
        //Checking for fragment count on backstack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;

            UserInterfaceUtils.showToast(getApplicationContext(),
                    getResources().getString(R.string.double_back_exit));

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        String current = simpleDateFormat.format(new Date());

        MySharedPreference.putPref(MySharedPreference.LAST_ACTIVE_TIME, current, getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");

        Boolean isActiveOverHalfHour = Validator.isActiveOverHalfHour(getApplicationContext());

        if (isActiveOverHalfHour){
            MySharedPreference.clearPref(getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}