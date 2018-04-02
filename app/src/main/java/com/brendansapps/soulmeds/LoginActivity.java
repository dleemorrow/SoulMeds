package com.brendansapps.soulmeds;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // Set up the ViewPager with the sections adapter.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new LoginFragment());
        mSectionsPagerAdapter.addFragment(new LoginFragment_Register());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    // Fragment Manager Class
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        // Create a List of fragments
        private final List<Fragment> mFragmentList = new ArrayList<>();

        // Constructor
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Add the fragments to the mFragmentList
        public void addFragment(Fragment fragment){
            mFragmentList.add(fragment);
        }

        // Get the Fragment at the position
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        // Get the number of fragments
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
