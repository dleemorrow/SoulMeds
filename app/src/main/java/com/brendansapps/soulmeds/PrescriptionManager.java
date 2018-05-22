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
 *  Utilized by AlarmsFragment_Symptoms & AlarmsFragment_Times
 *  Capable of managing dynamic lists of Symptoms & Times but only using 3 of each
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

    public String getSymptom(int index){
        return symptomDataManager.getSymptomAtIndex(index);
    }

    // Returns the number of Verses for that symptom
    public int getNumSymptoms(){
        return symptomDataManager.size();
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
        Log.d(TAG, "Adding Symptom " + name);
        PrescriptionDataObject newSymptom = new PrescriptionDataObject();
        newSymptom.name = name;
        newSymptom.isActive = true;
        userSymptomsList.add(newSymptom);

        saveUserSymptoms();
    }

    public void addTime(String time){
        Log.d(TAG, "Adding Time " + time);
        PrescriptionDataObject newTime = new PrescriptionDataObject();
        newTime.name = time;
        newTime.isActive = true;
        newTime.alarmID = getAlarmID(newTime.name);
        userTimesList.add(newTime);
        saveUserTimes();
        // printTimes();

        // Add Alarm
        setAlarm(getTimeInMillis(newTime.name), newTime.alarmID);
    }

    public void deleteSymptom(int index){
        Log.d(TAG, "Deleting Symptom " + userSymptomsList.get(index).name);
        userSymptomsList.remove(index);

        saveUserSymptoms();
    }

    public void deleteTime(int index){
        Log.d(TAG, "Deleting Time " + userTimesList.get(index).name);
        deleteAlarm(userTimesList.get(index).alarmID);
        userTimesList.remove(index);

        saveUserTimes();
        // printTimes();
    }

    public void editSymptom(int index, String newName){
        Log.d(TAG, "Changing Symptom " + userSymptomsList.get(index).name + " to " + newName);
        userSymptomsList.get(index).name = newName;

        saveUserSymptoms();
    }

    public void editTime(int index, String newTime){
        // Delete Old Alarm
        deleteAlarm(userTimesList.get(index).alarmID);

        // Edit Time
        Log.d(TAG, "Changing Time " + userTimesList.get(index).name + " to " + newTime);
        userTimesList.get(index).name = newTime;
        userTimesList.get(index).alarmID = getAlarmID(newTime);
        saveUserTimes();
        // printTimes();

        // Add New Alarm
        setAlarm(getTimeInMillis(userTimesList.get(index).name), userTimesList.get(index).alarmID);
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
            PrescriptionDataObject loadedSymptom = new PrescriptionDataObject();
            loadedSymptom.name = listOfUserSymptoms.get(i);

            if (!(loadedSymptom.name.equals(""))){
                // loadedSymptom.isActive = listOfUserSymptoms_Active;
                userSymptomsList.add(loadedSymptom);
            }
        }

        // Parse Loaded data into userTimesList
        ArrayList<String> listOfUserTimes = new ArrayList<>(Arrays.asList(compressedTimesString.split(",")));
        for (int i = 0; i < 3; i++){
            PrescriptionDataObject loadedTime = new PrescriptionDataObject();

            if (i < listOfUserTimes.size()){
                loadedTime.name = listOfUserTimes.get(i);
            }
            else {
                loadedTime.name = "8:00 AM";
            }

            if (!(loadedTime.name.equals(""))){
                // loadedTime.isActive = listOfUserTimes_Active;
                loadedTime.alarmID = getAlarmID(loadedTime.name);
                userTimesList.add(loadedTime);
            }
        }

        Log.d(TAG, "Loaded Times:");
        printTimes();
    }

    /** =================================================
     * Functions for managing first time user
     * ===================================================== */
    private Boolean firstTimeUser(){
//        Boolean firstTimeVisiting = prescriptionSP.getBoolean("firstTimeVisiting", true);
//        Log.d(TAG, "firstTimeVisiting = " + firstTimeVisiting);
        return prescriptionSP.getBoolean("firstTimeVisiting", true);
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
        Matcher m;
        try {
            Pattern timePattern = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]) (AM|PM)$");
            m = timePattern.matcher(currentTime);
            m.find();
            int currentHour = Integer.parseInt(m.group(1));
            int currentMinute = Integer.parseInt(m.group(2));

            if (m.group(3).equals("PM")){
                currentHour += 12;
            }

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

            long alarmTIM = calendar.getTimeInMillis();
            Log.d(TAG, "Got Time in Millis as " + alarmTIM);
            return calendar.getTimeInMillis();
        }
        catch (IllegalStateException e){
            Log.d(TAG, "Pattern Matcher Failed: " + e);
            return -1;
        }
    }

    // Returns an AlarmID created by appending the hour, minute, and amOrPm (0 | 1)
    private int getAlarmID(String currentTime){
        Matcher m;
        try {
            Pattern timePattern = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]) (AM|PM)$");
            m = timePattern.matcher(currentTime);
            m.find();

            int amOrPm;
            if (m.group(3).equals("AM")){
                amOrPm = 0;
            }else {
                amOrPm = 1;
            }
            return Integer.parseInt(m.group(1) + m.group(2) + amOrPm);
        }
        catch (IllegalStateException e){
            Log.d(TAG, "Pattern Matcher Failed: " + e);
            return -1;
        }
    }

    // Sets an alarm for the selected timeInMillis
    private void setAlarm(long timeInMillis, int alarmID){
        Intent intent = new Intent(mContext, AlarmHandler.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, alarmID, intent, 0);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d(TAG, "Set alarm for " + timeInMillis + " millis");
        printAlarms();
    }

    // Deletes the alarm specified
    private void deleteAlarm(int alarmID){
        Intent intent = new Intent(mContext, AlarmHandler.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, alarmID, intent, 0);
        mAlarmManager.cancel(pendingIntent);
    }

    private void printAlarms(){
        Log.d(TAG, "Next Alarm: " + String.valueOf(mAlarmManager.getNextAlarmClock()));
    }
}
