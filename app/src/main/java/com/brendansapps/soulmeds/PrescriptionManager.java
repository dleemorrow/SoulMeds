package com.brendansapps.soulmeds;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 *  Created by bt on 03/27/18.
 *
 *  Manages the symptoms and times associated with the user's prescription data
 *
 *  Utilized by AlarmsFragmentSymptoms & AlarmsFragmentTimes
 */

public class PrescriptionManager {

    /** =================================================
     * Private Properties
     * ===================================================== */
    private static final String TAG = "PrescriptionManager";
    private static DataManager symptomDataManager;
    private static SharedPreferences prescriptionSP;
    private static SharedPreferences.Editor prescriptionSPEditor;
    private Context mContext;
    private static AlarmManager mAlarmManager;

    // Symptom Data
    private static ArrayList<String> allSymptomsList;
    private static ArrayList<PrescriptionDataObject> userSymptomsList;

    // Alarm Times Data
    private static ArrayList<PrescriptionDataObject> userTimesList;

    /** =================================================
     * Constructor
     * ===================================================== */
    public PrescriptionManager(Context context){
        mContext = context;

        // Initialize Managers
        mAlarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
        symptomDataManager = new DataManager();
        allSymptomsList = symptomDataManager.getSymptomsList();
        prescriptionSP = mContext.getSharedPreferences("Prescriptions", MODE_PRIVATE);
        prescriptionSPEditor = prescriptionSP.edit();

        // Initialize User's Prescriptions
        userSymptomsList = new ArrayList<>();
        userTimesList = new ArrayList<>();
        if (firstTimeUser()){
            Log.d(TAG, "First Time User");
            createDefaults();
        }
        else {
            loadUserPrescriptions();
        }

        printAlarms();
    }

    /** =================================================
     * Accessors
     * ===================================================== */
    // Returns the user's symptoms as a ArrayList of Strings
    public ArrayList<String> getUserSymptoms(){
        ArrayList<String> userSymptoms = new ArrayList<>();
        for (int i = 0; i < userSymptomsList.size(); i++){
            userSymptoms.add(userSymptomsList.get(i).name);
        }

        // Clear list if empty
        if (userSymptoms.get(0).equals("")){
            userSymptoms.remove(0);
        }

        return userSymptoms;
    }

    // Returns the user's times as a ArrayList of Strings
    public ArrayList<String> getUserTimes(){
        ArrayList<String> userTimes = new ArrayList<>();
        for (int i = 0; i < userTimesList.size(); i++){
            userTimes.add(userTimesList.get(i).name);
        }

        return userTimes;
    }

    // Returns all possible symptoms
    public ArrayList<String> getAllSymptoms(){
        return allSymptomsList;
    }

    // Returns the number of Verses for that symptom
    public int getNumVerses(String symptom){
        return symptomDataManager.getNumVerses(symptom);
    }

    // Returns the Verse for that symptom at that index
    public String getVerse(String symptom, int index){
        return symptomDataManager.getVerse(symptom, index);
    }

    // Returns the Reference for the Verse for that symptom at that index
    public String getReference(String symptom, int index){
        return symptomDataManager.getVerseReference(symptom, index);
    }

    public void printTimes(){
        for (int i = 0; i < userTimesList.size(); i++){
            Log.d(TAG, i + ": " + userTimesList.get(i).name + ", " + userTimesList.get(i).isActive + ", " + userTimesList.get(i).alarmID);
        }
    }

    /** =================================================
     * Manipulators
     * ===================================================== */

    public void addSymptom(String name){
        Log.d(TAG, "Adding Symptom");
        PrescriptionDataObject newSymptom = new PrescriptionDataObject();
        newSymptom.name = name;
        newSymptom.isActive = true;
        userSymptomsList.add(newSymptom);

        saveUserSymptoms();
    }

    public void addTime(String time){
        Log.d(TAG, "Adding Time: " + time);
        PrescriptionDataObject newTime = new PrescriptionDataObject();
        newTime.name = time;
        newTime.isActive = true;
        newTime.alarmID = getAlarmID(newTime.name);
        userTimesList.add(newTime);

        saveUserTimes();
        updateAlarms();
        printTimes();
    }

    public void deleteSymptom(int index){
        Log.d(TAG, "Deleting Symptom");
        userSymptomsList.remove(index);

        saveUserSymptoms();
    }

    public void deleteTime(int index){
        Log.d(TAG, "Deleting Time");
        userTimesList.remove(index);

        saveUserTimes();
        deleteAlarm(userTimesList.get(index).alarmID);
        printTimes();
    }

    public void editSymptom(int index, String newName){
        Log.d(TAG, "Editing Symptom");
        userSymptomsList.get(index).name = newName;

        saveUserSymptoms();
    }

    public void editTime(int index, String newTime){
        Log.d(TAG, "Editing Time");
        userTimesList.get(index).name = newTime;
        userTimesList.get(index).alarmID = getAlarmID(newTime);

        saveUserTimes();
        updateAlarms();
        printTimes();
    }

    /** =================================================
     * Save and Load User's Prescriptions
     * ===================================================== */

    // Saves the user's Symptoms to the Shared Preference
    private void saveUserSymptoms(){
        // Compress symptomsList into one string
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < userSymptomsList.size(); i++){
            stringBuilder.append(userSymptomsList.get(i).name).append(",");
        }
        Log.d(TAG, "Saved Symptoms: " + stringBuilder.toString());

        // Save SharedPreference
        prescriptionSPEditor = prescriptionSP.edit();
        prescriptionSPEditor.putString("symptomsList", stringBuilder.toString());
        prescriptionSPEditor.apply();
    }

    // Saves the user's Alarm Times to the Shared Preference
    private void saveUserTimes(){
        printTimes();

        // Compress userTimesList into one string
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < userTimesList.size(); i++){
            stringBuilder.append(userTimesList.get(i).name).append(",");
        }
        Log.d(TAG, "Saved Times: " + stringBuilder.toString());

        // Save SharedPreference
        prescriptionSPEditor = prescriptionSP.edit();
        prescriptionSPEditor.putString("timesList", stringBuilder.toString());
        prescriptionSPEditor.apply();
    }

    private void loadUserPrescriptions(){
        // Load SharedPreferences
        String compressedSymptomsString = prescriptionSP.getString("symptomsList", "");
//        String compressedSymptomsString = prescriptionSP.getString("symptomsList_Active", "");
        String compressedTimesString = prescriptionSP.getString("timesList", "");
//        String compressedTimesString = prescriptionSP.getString("timesList_Active", "");

        // Parse Loaded data into userSymptomsList
        ArrayList<String> listOfUserSymptoms = new ArrayList<>(Arrays.asList(compressedSymptomsString.split(",")));
        for (int i = 0; i < listOfUserSymptoms.size(); i++){
            PrescriptionDataObject tempData = new PrescriptionDataObject();
            tempData.name = listOfUserSymptoms.get(i);
//            tempData.isActive = listOfUserSymptoms_Active;
            userSymptomsList.add(tempData);
        }

        // Parse Loaded data into userTimesList
        ArrayList<String> listOfUserTimes = new ArrayList<>(Arrays.asList(compressedTimesString.split(",")));
        for (int i = 0; i < listOfUserTimes.size(); i++){
            PrescriptionDataObject tempData = new PrescriptionDataObject();
            tempData.name = listOfUserTimes.get(i);
//            tempData.isActive = listOfUserTimes;
            tempData.alarmID = getAlarmID(tempData.name);
            userTimesList.add(tempData);
        }

        // Handle if first empty
        if (userSymptomsList.get(0).name.equals("")){
            deleteSymptom(0);
        }
        if (userTimesList.get(0).name.equals("")){
            deleteTime(0);
        }

        printTimes();
    }

    /** =================================================
     * Functions for managing first time user
     * ===================================================== */
    private Boolean firstTimeUser(){
        Boolean firstTimeVisiting = prescriptionSP.getBoolean("firstTimeVisiting", true);
//        Log.d(TAG, "firstTimeVisiting = " + firstTimeVisiting);
        return firstTimeVisiting;
    }

    // Initializes Symptom data for first time users
    private void createDefaults(){
        // Initialize First Symptom
        PrescriptionDataObject firstSymptom = new PrescriptionDataObject();
        firstSymptom.name = allSymptomsList.get(0);
        firstSymptom.isActive = false;

        // Initialize First Time
        PrescriptionDataObject firstTime = new PrescriptionDataObject();
        firstTime.name = "8:00 AM";
        firstTime.isActive = false;

        // Save First Prescription
        userSymptomsList.add(firstSymptom);
        userTimesList.add(firstTime);
        saveUserSymptoms();
        saveUserTimes();

        // Mark as having visited
        prescriptionSPEditor.putBoolean("firstTimeVisiting", false);
        prescriptionSPEditor.apply();
    }

    /** =================================================
     * Functions for managing Alarms
     * ===================================================== */
    private long getTimeInMillis(String currentTime){
        // Get Hour & Minute from string
        Pattern timePattern = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]) (AM|PM)$");
        Matcher m = timePattern.matcher(currentTime);
        m.find();
        int currentHour = Integer.parseInt(m.group(1));
        int currentMinute = Integer.parseInt(m.group(2));

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

    // Returns an AlarmID created by appending the hour, minute, and amOrPm (0 | 1)
    private int getAlarmID(String currentTime){
        Pattern timePattern = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]) (AM|PM)$");
        Matcher m = timePattern.matcher(currentTime);
        m.find();
        int amOrPm;
        if (m.group(3).equals("AM")){
            amOrPm = 0;
        }else {
            amOrPm = 1;
        }
        return Integer.parseInt(m.group(1) + m.group(2) + amOrPm);
    }

    // Calls Set Alarm for each time in userTimesList
    private void updateAlarms(){
        for (int i = 0; i < userTimesList.size(); i++){
            long timeInMillis = getTimeInMillis(userTimesList.get(i).name);
            int alarmId = userTimesList.get(i).alarmID;
            setAlarm(timeInMillis, alarmId);
        }
    }

    // Sets an alarm for the selected timeInMillis
    private void setAlarm(long timeInMillis, int requestCode){
        Intent intent = new Intent(mContext, AlarmHandler.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent, 0);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        printAlarms();
    }

    // Deletes the alarm specified
    private void deleteAlarm(int requestCode){
        Intent intent = new Intent(mContext, AlarmHandler.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent, 0);
        mAlarmManager.cancel(pendingIntent);
    }

    private void printAlarms(){
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
        Log.d(TAG, "Next Alarm: " + String.valueOf(alarmManager.getNextAlarmClock()));
    }
}
