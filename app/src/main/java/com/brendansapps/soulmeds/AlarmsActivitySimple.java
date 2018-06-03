package com.brendansapps.soulmeds;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlarmsActivitySimple extends AppCompatActivity {
    private static final String TAG = "AlarmsSimple";
    private Boolean isInitializing = true;

    // Members for UI elements
    private TextView symptom1_tv, symptom2_tv, symptom3_tv;
    private TextView time1_tv, time2_tv, time3_tv;

    // Members for the Symptom Data
    private PrescriptionManager mPrescriptionManager;
    private ArrayList<String> allSymptomsList; // List of all possible symptoms
    private ArrayList<String> userSymptomsList; // List of user's symptoms
    private ArrayList<String> userTimesList; // List of user's times

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms_simple);

        // Connect to interface elements
        symptom1_tv = findViewById(R.id.symptom1_tv);
        symptom2_tv = findViewById(R.id.symptom2_tv);
        symptom3_tv = findViewById(R.id.symptom3_tv);
        time1_tv = findViewById(R.id.time1_tv);
        time2_tv = findViewById(R.id.time2_tv);
        time3_tv = findViewById(R.id.time3_tv);

        // Get Prescription Data & initialize spinners
        mPrescriptionManager = new PrescriptionManager(getApplicationContext());
        allSymptomsList = mPrescriptionManager.getAllSymptoms();
        userSymptomsList = mPrescriptionManager.getUserSymptoms();
        userTimesList = mPrescriptionManager.getUserTimes();

        // Setup onClickListeners
        symptom1_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSymptom(0);
            }
        });
        symptom2_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSymptom(1);
            }
        });
        symptom3_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSymptom(2);
            }
        });
        time1_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTime(0);
            }
        });
        time2_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTime(1);
            }
        });
        time3_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTime(2);
            }
        });

        // Init Interface Data
        resetSymptoms();
        resetTimes();
    }

    /** =================================================
     * Functions for Controlling User Symptoms
     * ===================================================== */

    private void resetSymptoms() {
        userSymptomsList = mPrescriptionManager.getUserSymptoms();
        if (userSymptomsList.size() >= 3) {
//            Log.d(TAG, "Symptom 1 = " + userSymptomsList.get(0));
//            Log.d(TAG, "Symptom 2 = " + userSymptomsList.get(1));
//            Log.d(TAG, "Symptom 3 = " + userSymptomsList.get(2));
            symptom1_tv.setText(userSymptomsList.get(0));
            symptom2_tv.setText(userSymptomsList.get(1));
            symptom3_tv.setText(userSymptomsList.get(2));
        }
    }

    // Edits the symptom at index to be newSymptom
    private void editSymptom(int symptomIndex){

//        mPrescriptionManager.editSymptom(symptomIndex, newSymptom);
        userSymptomsList = mPrescriptionManager.getUserSymptoms();
        Log.d(TAG, "Symptoms Edited: " + userSymptomsList);
    }

    /** =================================================
     * Functions for Controlling User Alarm Times
     * ===================================================== */

    private void resetTimes() {
        userTimesList = mPrescriptionManager.getUserTimes();
        if (userTimesList.size() >= 3) {
//            Log.d(TAG, "Time 1 = " + userTimesList.get(0));
//            Log.d(TAG, "Time 2 = " + userTimesList.get(1));
//            Log.d(TAG, "Time 3 = " + userTimesList.get(2));
            time1_tv.setText(userTimesList.get(0));
            time2_tv.setText(userTimesList.get(1));
            time3_tv.setText(userTimesList.get(2));
        }
    }

    // Edit the selected Alarm Time
    private void editTime(final int alarmID) {
        Log.d(TAG, "Attempting to editTime(" + alarmID + ")");

        // Get what the time was to use as default value
        String currentTime = userTimesList.get(alarmID);
        String currentHour_string, currentMinute_string, currentAMvPM;
        Pattern timePattern = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]) (AM|PM)$");
        Matcher m = timePattern.matcher(currentTime);
        m.find();
        currentHour_string = m.group(1);
        currentMinute_string = m.group(2);
        currentAMvPM = m.group(3);
        Log.d(TAG, "Time: " + currentHour_string + ":" + currentMinute_string + " " + currentAMvPM);
        int currentHour = Integer.parseInt(currentHour_string);
        int currentMinute = Integer.parseInt(currentMinute_string);
        if (currentAMvPM.equals("PM")){
            currentHour += 12;
        }

        // Get New Time from TimePickerDialog
        TimePickerDialog mAlarmTimePickerDialog;
        mAlarmTimePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
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
