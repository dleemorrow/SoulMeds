package com.brendansapps.soulmeds;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bt on 3/27/18.
 *
 * Class to handle the user's alarm prescription data
 *
 * Utilized in the AlarmsActivity
 */

// Object for representing either a symptom or a time for the Alarm prescriptions
class PrescriptionDataObject {
    public String name;
    public Boolean isActive;
}

// Class to handle the user's alarm prescription data
public class PrescriptionManager {

    // Properties
    private static final String TAG = "PrescriptionManager";
    private static DataManager symptomDataManager;
    private static SharedPreferences prescriptionSP;
    private static SharedPreferences.Editor prescriptionSPEditor;

    // Symptom Data
    public static ArrayList<String> allSymptomsList;
    public static ArrayList<PrescriptionDataObject> userSymptomsList;

    // Times Data
    public static ArrayList<PrescriptionDataObject> userTimesList;

    /** =================================================
     * Constructor
     * ===================================================== */
    public PrescriptionManager(Context context){

        // Initialize Managers
        symptomDataManager = new DataManager();
        allSymptomsList = symptomDataManager.getSymptomsList();
        prescriptionSP = context.getSharedPreferences("Prescriptions", MODE_PRIVATE);
        prescriptionSPEditor = prescriptionSP.edit();

        // Initialize User's Prescriptions
        userSymptomsList = new ArrayList<>();
        userTimesList = new ArrayList<>();
        if (firstTimeUser()){
            createDefaults();
        }
        else {
            loadUserPrescriptions();
        }
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

        // Clear list if empty
        if (userTimes.get(0).equals("")){
            userTimes.remove(0);
        }

        return userTimes;
    }

    // Returns all possible symptoms
    public ArrayList<String> getAllSymptoms(){
        return allSymptomsList;
    }

    /** =================================================
     * Manipulators
     * ===================================================== */

    public void addSymptom(String name){
        PrescriptionDataObject newSymptom = new PrescriptionDataObject();
        newSymptom.name = name;
        newSymptom.isActive = true;
        userSymptomsList.add(newSymptom);
        saveUserSymptoms();
    }

    public void addTime(String time){
        PrescriptionDataObject newTime = new PrescriptionDataObject();
        newTime.name = time;
        newTime.isActive = true;
        userTimesList.add(newTime);
        saveUserTimes();
    }

    public void deleteSymptom(int index){
        userSymptomsList.remove(index);
        saveUserSymptoms();
    }

    public void deleteTime(int index){
        userTimesList.remove(index);
        saveUserTimes();
    }

    public void editSymptom(int index, String newName){
        userSymptomsList.get(index).name = newName;
        saveUserSymptoms();
    }

    public void editTime(int index, String newTime){
        userTimesList.get(index).name = newTime;
        saveUserTimes();
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
            userTimesList.add(tempData);
        }
    }

    /** =================================================
     * Functions for managing first time user
     * ===================================================== */
    private Boolean firstTimeUser(){
        return prescriptionSP.getBoolean("hasVisited", false);
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
        prescriptionSPEditor.putBoolean("hasVisited", true);
        prescriptionSPEditor.apply();
    }
}
