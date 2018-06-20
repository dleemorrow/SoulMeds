package com.brendansapps.soulmeds;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/** =================================================
 * Created by bt on 5/17/18.
 *
 * Alarms Activity for managing symptoms and times
 * Tabbed Activity with changing Toolbar text & functionality
 * ===================================================== */

public class AlarmsActivity_Tabbed extends AppCompatActivity {

    /** =================================================
     * Member Variables
     * ===================================================== */

    final String TAG = "AlarmsActivity_Tabbed";

    // Page Management
    private ViewPager mViewPager;
    Fragment symptomsFragment, timesFragment;

    // Toolbar UI Items
    private TextView pageTitle;
    private Button mBackBtn, mNextBtn;

    // Prescription Info
    private PrescriptionManager mPrescriptionManager;
    private String symptom1, symptom2, symptom3;
    private String time1, time2, time3;

    /** =================================================
     * Constructor
     * ===================================================== */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms_tabbed);

        mPrescriptionManager = new PrescriptionManager(this);

        // Set up the fragments
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
                    case 0: // cancel
                        finish();
                        break;
                    case 1: // back
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
                    case 0: // next
                        mViewPager.setCurrentItem(1);
                        break;
                    case 1: // save
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
        if (symptom1 != null){mPrescriptionManager.editSymptom(0, symptom1);}
        if (symptom2 != null){mPrescriptionManager.editSymptom(1, symptom2);}
        if (symptom3 != null){mPrescriptionManager.editSymptom(2, symptom3);}
        if (time1 != null){mPrescriptionManager.editTime(0, time1);}
        if (time2 != null){mPrescriptionManager.editTime(1, time2);}
        if (time3 != null){mPrescriptionManager.editTime(2, time3);}
        mPrescriptionManager.saveUserPrescriptions_Local();
        mPrescriptionManager.saveUserPrescriptions_Firebase();
        finish();
    }

    /** =================================================
     * Page Management
     * ===================================================== */

    // Adds the fragments to the View Page / SectionsPagerAdapter / FragmentList
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        symptomsFragment = new AlarmsFragment_Symptoms();
        timesFragment = new AlarmsFragment_Times();

        adapter.addFragment(symptomsFragment);
        adapter.addFragment(timesFragment);
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
                        mNextBtn.setText(R.string.save_btn);
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

    /** =============================================
    * Prescription Info Getters & Setters
    * ============================================= */

    public ArrayList<String> getUserTimes(){
        return mPrescriptionManager.getUserTimes();
    }

    public ArrayList<String> getUserSymptoms(){
        return mPrescriptionManager.getUserSymptoms();
    }

    public ArrayList<String> getAllSymptoms(){
        return mPrescriptionManager.getAllSymptoms();
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public void setSymptom1(String symptom1) {
        Log.d(TAG, "Set Symptom 1 as: " + symptom1);
        this.symptom1 = symptom1;
    }

    public void setSymptom2(String symptom2) {
        Log.d(TAG, "Set Symptom 2 as: " + symptom2);
        this.symptom2 = symptom2;
    }

    public void setSymptom3(String symptom3) {
        Log.d(TAG, "Set Symptom 3 as: " + symptom3);
        this.symptom3 = symptom3;
    }

    /** =============================================
     * Fragment Manager Class
     * ============================================= */
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
