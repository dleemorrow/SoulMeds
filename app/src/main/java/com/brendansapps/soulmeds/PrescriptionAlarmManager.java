package com.brendansapps.soulmeds;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by bt on 4/3/18.
 */

public class PrescriptionAlarmManager {

    // Members
    private static final String TAG = "AlarmPrescriptManager";
    private AlarmManager mAlarmManager;
    private ArrayList<String> mAlarmIDs;
    private Context mContext;

    // Constructor
    public PrescriptionAlarmManager(Context context) {
        mContext = context;
        mAlarmIDs = new ArrayList<>();
    }

    // Returns the time in millis between now and the currentTime
    private long getTimeInMillis(String currentTime){
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

        // Prepare Time
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                currentHour,
                currentMinute,
                0
        );

        return calendar.getTimeInMillis();
    }

    // Calls Set Alarm for each time in userTimesList
    public void updateAlarms(ArrayList<String> allTimesList){
        for (int i = 0; i < allTimesList.size(); i++){
            String currentTime = allTimesList.get(i);
            setAlarm(getTimeInMillis(currentTime));
        }
    }

    // Sets an alarm for the selected timeInMillis
    private void setAlarm(long timeInMillis){
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlarmHandler.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        printAlarms();
    }

    // Deletes the alarm specified
    private void deleteAlarms(){
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlarmHandler.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    private void printAlarms(){
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
        Log.d(TAG, "Next Alarm: " + String.valueOf(alarmManager.getNextAlarmClock()));
    }


}
