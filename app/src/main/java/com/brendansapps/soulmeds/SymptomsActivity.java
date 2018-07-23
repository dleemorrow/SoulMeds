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

public class SymptomsActivity extends AppCompatActivity {
    /** =================================================
     * Member Variables
     * ===================================================== */
    private static final String TAG = "AlarmsFragment_Times";

    // Prescription Info
    private PrescriptionManager mPrescriptionManager;
    private String symptom1, symptom2, symptom3;

    // Members for the Symptom Picker UI elements
    public NumberPicker mSymptomNP1, mSymptomNP2, mSymptomNP3;

    // Members for the Symptom Data
    private ArrayList<String> allSymptomsList; // List of all possible symptoms
    private ArrayList<String> allSymptomsListNone; // List of all possible symptoms with None option
    private ArrayList<String> userSymptomsList; // List of user's symptoms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        mPrescriptionManager = new PrescriptionManager(getApplicationContext()); // or builtin this, is this the same thing?

        // Connect to Activity for user Prescriptions
        allSymptomsList = mPrescriptionManager.getAllSymptoms();
        allSymptomsListNone = mPrescriptionManager.getAllSymptoms();
        userSymptomsList = mPrescriptionManager.getUserSymptoms();

        // Connect to spinners in the interface
        mSymptomNP1 = findViewById(R.id.symptom_picker_1);
        mSymptomNP2 = findViewById(R.id.symptom_picker_2);
        mSymptomNP3 = findViewById(R.id.symptom_picker_3);

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
                saveSymptoms();
                Toast.makeText(SymptomsActivity.this, "Symptoms Saved", Toast.LENGTH_SHORT).show();
            }
        });
        initSymptomPickers();
    }

    /** =================================================
     * Functions for Controlling Spinners
     * ===================================================== */

    private void initSymptomPickers(){
        // Convoluted way to get the list as a final String[]
        String[] symptomListAsString = new String [allSymptomsList.size()];
        symptomListAsString = allSymptomsList.toArray(symptomListAsString);
        final String[] realList = symptomListAsString;

        // Convoluted way to get the list as a final String[] with None option
        allSymptomsListNone.add("None");
        String[] symptomListNoneAsString = new String [allSymptomsListNone.size()];
        symptomListNoneAsString = allSymptomsListNone.toArray(symptomListNoneAsString);
        final String[] realListNone = symptomListNoneAsString;

        // Number Picker 1
        mSymptomNP1.setMinValue(0);
        mSymptomNP1.setMaxValue(realList.length-1);
        mSymptomNP1.setDisplayedValues(realList);
        for (int i = 0; i < realList.length; i++){
            if (userSymptomsList.get(0).equals(realList[i])){
                mSymptomNP1.setValue(i);
            }
        }
        mSymptomNP1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                symptom1 = realList[newVal];
            }
        });

        // Number Picker 2
        mSymptomNP2.setMinValue(0);
        mSymptomNP2.setMaxValue(allSymptomsListNone.size()-1);
        mSymptomNP2.setDisplayedValues(realListNone);
        for (int i = 0; i < realListNone.length; i++){
            if (userSymptomsList.get(1).equals(realListNone[i])){
                mSymptomNP2.setValue(i);
            }
        }
        mSymptomNP2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                symptom2 = realListNone[newVal];
            }
        });

        // Number Picker 3
        mSymptomNP3.setMinValue(0);
        mSymptomNP3.setMaxValue(allSymptomsListNone.size()-1);
        mSymptomNP3.setDisplayedValues(realListNone);
        for (int i = 0; i < realListNone.length; i++){
            if (userSymptomsList.get(2).equals(realListNone[i])){
                mSymptomNP3.setValue(i);
            }
        }
        mSymptomNP3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                symptom3 = realListNone[newVal];
            }
        });
    }


    /** =================================================
     * Prescription Management
     * ===================================================== */

    private void saveSymptoms(){
        if (symptom1 != null){mPrescriptionManager.editSymptom(0, symptom1);}
        if (symptom2 != null){mPrescriptionManager.editSymptom(1, symptom2);}
        if (symptom3 != null){mPrescriptionManager.editSymptom(2, symptom3);}
        mPrescriptionManager.saveUserPrescriptions_Local();
        mPrescriptionManager.saveUserPrescriptions_Firebase();
        finish();
    }
}
