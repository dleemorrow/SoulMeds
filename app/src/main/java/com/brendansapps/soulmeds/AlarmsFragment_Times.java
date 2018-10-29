package com.brendansapps.soulmeds;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    AlarmsActivity_Tabbed theActivity;

    // Members for the TimePickers
    private TimePicker mTP1, mTP2, mTP3;

    // Members for the Symptom Data
    private ArrayList<String> userTimesList;

    /** =================================================
     * Constructors
     * ===================================================== */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_alarms_times, container, false);

        // Connect to Activity for user Prescriptions
        theActivity = (AlarmsActivity_Tabbed) getActivity();
        assert theActivity != null;
        userTimesList = theActivity.getUserTimes();

        // getActivity().setVolumeControlStream(AudioManager.STREAM_ALARM);

        // Connect to spinners in the interface
        mTP1 = view.findViewById(R.id.tp_clock_1);
        mTP2 = view.findViewById(R.id.tp_clock_2);
        mTP3 = view.findViewById(R.id.tp_clock_3);
        initTimePickers();

        return view;
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
                theActivity.setTime1(getTimeInAMPM(hourOfDay, minute));
//                Log.d(TAG, "Set Time 1 to " + getTimeInAMPM(hourOfDay, minute));
            }
        });

        mTP2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                theActivity.setTime2(getTimeInAMPM(hourOfDay, minute));
//                Log.d(TAG, "Set Time 2 to " + getTimeInAMPM(hourOfDay, minute));
            }
        });

        mTP3.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                theActivity.setTime3(getTimeInAMPM(hourOfDay, minute));
//                Log.d(TAG, "Set Time 3 to " + getTimeInAMPM(hourOfDay, minute));
            }
        });
    }

    // Returns a String representing the time in AM|PM format
    public String getTimeInAMPM(int hour, int minute){
        String newTime;
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

        return newTime;
    }
}
