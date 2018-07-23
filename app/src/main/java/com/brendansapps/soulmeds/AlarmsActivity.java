package com.brendansapps.soulmeds;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlarmsActivity extends AppCompatActivity {
    /** =================================================
     * Member Variables
     * ===================================================== */
    private static final String TAG = "AlarmsFragment_Times";

    // Prescription Info
    private PrescriptionManager mPrescriptionManager;
    private String time1, time2, time3;

    // Members for the TimePickers
    private TimePicker mTP1, mTP2, mTP3;

    // Members for the Symptom Data
    private ArrayList<String> userTimesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);

        mPrescriptionManager = new PrescriptionManager(getApplicationContext()); // or builtin this, is this the same thing?

        // Connect to Activity for user Prescriptions
        userTimesList = mPrescriptionManager.getUserTimes();

        // Connect to spinners in the interface
        mTP1 = findViewById(R.id.tp_clock_1);
        mTP2 = findViewById(R.id.tp_clock_2);
        mTP3 = findViewById(R.id.tp_clock_3);
        initTimePickers();

        // Toolbar UI Items
        Button cancelBtn = findViewById(R.id.alarms_btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button saveBtn = findViewById(R.id.alarms_btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAlarms();
                Toast.makeText(AlarmsActivity.this, "Alarms Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /** =================================================
     * Prescription Management
     * ===================================================== */

    private void saveAlarms(){
        if (time1 != null){mPrescriptionManager.editTime(0, time1);}
        if (time2 != null){mPrescriptionManager.editTime(1, time2);}
        if (time3 != null){mPrescriptionManager.editTime(2, time3);}
        mPrescriptionManager.saveUserPrescriptions_Local();
        mPrescriptionManager.saveUserPrescriptions_Firebase();
        finish();
    }

    /** =================================================
     * Functions for Controlling Alarm Times
     * ===================================================== */

    private void initTimePickers(){
        Pattern timePattern = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]) (AM|PM)$");
        Matcher match1, match2, match3;

        match1 = timePattern.matcher(userTimesList.get(0));
        if (match1.find()){
            int hour1 = Integer.parseInt(match1.group(1));
            if (match1.group(3).equals("PM")){
                hour1 += 12;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mTP1.setHour(hour1);
                mTP1.setMinute(Integer.parseInt(match1.group(2)));
            }
            else {
                mTP1.setCurrentHour(hour1);
                mTP1.setCurrentMinute(Integer.parseInt(match1.group(2)));
            }
        }

        match2 = timePattern.matcher(userTimesList.get(1));
        if (match2.find()){
            int hour2 = Integer.parseInt(match2.group(1));
            if (match2.group(3).equals("PM")){
                hour2 += 12;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mTP2.setHour(hour2);
                mTP2.setMinute(Integer.parseInt(match2.group(2)));
            }
            else {
                mTP2.setCurrentHour(hour2);
                mTP2.setCurrentMinute(Integer.parseInt(match2.group(2)));
            }
        }

        match3 = timePattern.matcher(userTimesList.get(2));
        if (match3.find()){
            int hour3 = Integer.parseInt(match3.group(1));
            if (match3.group(3).equals("PM")){
                hour3 += 12;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mTP3.setHour(hour3);
                mTP3.setMinute(Integer.parseInt(match3.group(2)));
            }
            else {
                mTP3.setCurrentHour(hour3);
                mTP3.setCurrentMinute(Integer.parseInt(match3.group(2)));
            }
        }

        mTP1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time1 = getTimeInAMPM(hourOfDay, minute);
            }
        });

        mTP2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time2 = getTimeInAMPM(hourOfDay, minute);
            }
        });

        mTP3.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                time3 = getTimeInAMPM(hourOfDay, minute);
            }
        });
    }

    // Returns a String representing the time in AM|PM format
    public String getTimeInAMPM(int hour, int minute){
        String newTime;
//        Log.d(TAG, "Getting time in AMPM with hour " + hour);
        Boolean isPM = false;

        // Create Hour
        if (hour > 12) {
            hour -= 12;
            isPM = true;
        }
        newTime = hour + ":";

        // Create Minute
        if (minute < 10) {
            newTime = newTime + "0" + minute;
        } else {
            newTime = newTime + minute;
        }

        // Append AM vs PM
        if (isPM){
            newTime = newTime + " PM";
        } else {
            newTime = newTime + " AM";
        }

//        Log.d(TAG, "Time in AMPM = " + newTime);
        return newTime;
    }
}
