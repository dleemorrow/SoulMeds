package com.brendansapps.soulmeds;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/** =================================================
 * Created by bt on 3/14/18.
 *
 * Alarms Activity for managing symptoms and times
 * Tabbed Activity with changing Toolbar text & functionality
 * ===================================================== */

public class AlarmsActivity_Tabbed extends AppCompatActivity {

    /** =================================================
     * Member Variables
     * ===================================================== */

    // Page Management
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    // Toolbar UI Items
    private TextView pageTitle;
    private Button mBackBtn;
    private Button mNextBtn;

    /** =================================================
     * Constructor
     * ===================================================== */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms_tabbed);

        // Set up the ViewPager and the SectionsPagerAdapter.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        initButtonListeners();

    }

    /** =================================================
     * Button Listeners
     * ===================================================== */

    private void initButtonListeners(){

        mBackBtn = findViewById(R.id.alarms_btn_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mViewPager.getCurrentItem()){
                    case 0:
                        finish();
                        break;
                    case 1:
                        mViewPager.setCurrentItem(0);
                        break;
                    default:
                        break;
                }
            }
        });

        mNextBtn = findViewById(R.id.alarms_btn_next);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mViewPager.getCurrentItem()){
                    case 0:
                        mViewPager.setCurrentItem(1);
                        break;
                    case 1:
                        savePrescription();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    /** =================================================
     * Prescription Management
     * ===================================================== */

    private void savePrescription(){

    }

    /** =================================================
     * Page Management
     * ===================================================== */

    // Adds the fragments to the View Page / SectionsPagerAdapter / FragmentList
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        Fragment symptomsFragment = new AlarmsSymptomsFragment();
        Fragment symptomsFragment2 = new AlarmsSymptomsFragment();
//        Fragment timesFragment = new AlarmsFragmentTimes();

        adapter.addFragment(symptomsFragment);
        adapter.addFragment(symptomsFragment2);
//        adapter.addFragment(timesFragment);
        viewPager.setAdapter(adapter);
        setupPageManager();
    }

    private void setupPageManager(){
        pageTitle = findViewById(R.id.alarms_toolbar_tv);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        pageTitle.setText(R.string.title_alarms_symptoms);
                        mBackBtn.setText(R.string.cancel_btn);
                        mNextBtn.setText(R.string.next_btn);
                        break;
                    case 1:
                        pageTitle.setText(R.string.title_alarms_times);
                        mBackBtn.setText(R.string.back_btn);
                        mNextBtn.setText(R.string.done_btn);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
