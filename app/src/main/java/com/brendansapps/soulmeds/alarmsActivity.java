package com.brendansapps.soulmeds;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

public class alarmsActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter; // Manages the Pages in Memory
    private ViewPager mViewPager; // Hosts the Section Contents
    private Button mSetAlarmButton;
    private TimePicker mTimePicker;

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
        tabLayout.getTabAt(0).setText("1");
        tabLayout.getTabAt(1).setText("2");
        tabLayout.getTabAt(2).setText("3");

        // Find the Alarm Buttons
        mSetAlarmButton = findViewById(R.id.setAlarmBtn1);
        mTimePicker = findViewById(R.id.alarmTimePicker1);

//      mSetAlarmButton.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               Calendar calendar = Calendar.getInstance();
//
//               if (Build.VERSION.SDK_INT >= 23) {
//                   calendar.set(
//                           calendar.get(Calendar.YEAR),
//                           calendar.get(Calendar.MONTH),
//                           calendar.get(Calendar.DAY_OF_YEAR),
//                           mTimePicker.getHour(),
//                           mTimePicker.getMinute(),
//                           0
//                   );
//               } else {
//                   calendar.set(
//                           calendar.get(Calendar.YEAR),
//                           calendar.get(Calendar.MONTH),
//                           calendar.get(Calendar.DAY_OF_YEAR),
//                           mTimePicker.getCurrentHour(),
//                           mTimePicker.getCurrentMinute(),
//                           0
//                   );
//
//               }
//
//               setAlarm(calendar.getTimeInMillis());
//           }
//        });

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new alarms_fragment1());
        adapter.addFragment(new alarms_fragment2());
        adapter.addFragment(new alarms_fragment3());
        viewPager.setAdapter(adapter);

    }

    private void setAlarm(long timeInMillis) {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, MyAlarm.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
//
//        Toast.makeText(this, "Alarm is Set", Toast.LENGTH_SHORT).show();

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

    // Fragment Manager
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
