package com.brendansapps.soulmeds;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bt on 5/17/18.
 *
 * Tab Of Alarms Activity for the Symptoms - Static Version
 * Manages the user's Prescription Symptoms
 */

public class AlarmsFragment_Times extends Fragment {

    /** =================================================
     * Member Variables
     * ===================================================== */
    private static final String TAG = "AlarmsFragment_Times";

    // Members for the Spinners
    TextView mClock1, mClock2, mClock3;

    // Members for the Symptom Data
    private PrescriptionManager mPrescriptionManager;
    private ArrayList<String> userTimesList;

    /** =================================================
     * Constructors
     * ===================================================== */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_alarms_times, container, false);

        // Connect to spinners in the interface
        mClock1 = view.findViewById(R.id.tv_clock_1);
        mClock2 = view.findViewById(R.id.tv_clock_2);
        mClock3 = view.findViewById(R.id.tv_clock_3);

        // Get Prescription Data & initialize spinners
        mPrescriptionManager = new PrescriptionManager(this.getContext());
        resetTimes();

        mClock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTime(0);
            }
        });

        mClock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTime(1);
            }
        });

        mClock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTime(2);
            }
        });

        return view;
    }

    /** =================================================
     * Functions for Controlling Alarm Times
     * ===================================================== */

    private void resetTimes() {
        userTimesList = mPrescriptionManager.getUserTimes();
        if (userTimesList.size() >= 3) {
            Log.d(TAG, "Clock 1 = " + userTimesList.get(0));
            Log.d(TAG, "Clock 2 = " + userTimesList.get(1));
            Log.d(TAG, "Clock 3 = " + userTimesList.get(2));
            mClock1.setText(userTimesList.get(0));
            mClock2.setText(userTimesList.get(1));
            mClock3.setText(userTimesList.get(2));
        }
    }

    // Edit the selected Alarm Time
    private void editTime(final int alarmID) {
        Log.d(TAG, "Attempting to editTime(" + alarmID + ")");

        // Get what the time was to use as default value
        String currentTime = userTimesList.get(alarmID);
        String currentHour_string;
        String currentMinute_string;
        Pattern timePattern = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]) (AM|PM)$");
        Matcher m = timePattern.matcher(currentTime);
        m.find();
        currentHour_string = m.group(1);
        currentMinute_string = m.group(2);
        Log.d(TAG, "Time: " + currentHour_string + ":" + currentMinute_string);
        int currentHour = Integer.parseInt(currentHour_string);
        int currentMinute = Integer.parseInt(currentMinute_string);

        // Get New Time from TimePickerDialog
        TimePickerDialog mAlarmTimePickerDialog;
        mAlarmTimePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        userTimesList.set(alarmID, getTimeInAMPM(hourOfDay, minute));
                        mPrescriptionManager.editTime(alarmID, getTimeInAMPM(hourOfDay, minute));
                        resetTimes();
                    }
                }, currentHour, currentMinute, false);
        mAlarmTimePickerDialog.show();
    }

    // Returns a String representing the time in AM|PM format
    public String getTimeInAMPM(int hour, int minute){
        String newTime;

        // Create Hour
        if (hour > 12) {
            hour -= 12;
        }
        newTime = hour + ":";

        // Create Minute
        if (minute < 10) {
            newTime = newTime + "0" + minute + " PM";
        } else {
            newTime = newTime + minute + " PM";
        }

        Log.d(TAG, "Time in AMPM = " + newTime);
        return newTime;
    }

}
