package com.brendansapps.soulmeds;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class alarmsActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter; // Manages the Pages in Memory
    private ViewPager mViewPager; // Hosts the Section Contents

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);

        // Set up the ViewPager and the SectionsPagerAdapter.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        // Setup the tab toolbar
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setText("Symptoms");
        tabLayout.getTabAt(1).setText("Alarms");

    }

    // Adds the fragments to the View Page / SectionsPagerAdapter / FragmentList
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new alarms_fragment1());
        adapter.addFragment(new alarms_fragment2());
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
