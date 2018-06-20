package com.brendansapps.soulmeds;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by bt on 5/17/18.
 *
 * Tab Of Alarms Activity for the Symptoms - Static Version
 * Manages the user's Prescription Symptoms
 */

public class AlarmsFragment_Symptoms extends Fragment {

    /** =================================================
     * Member Variables
     * ===================================================== */
    private static final String TAG = "AlarmsFragment_Symptoms";
    AlarmsActivity_Tabbed theActivity;

    // Members for the Symptom Picker UI elements
    public NumberPicker mSymptomNP1, mSymptomNP2, mSymptomNP3;

    // Members for the Symptom Data
    private ArrayList<String> allSymptomsList; // List of all possible symptoms
    private ArrayList<String> userSymptomsList; // List of user's symptoms

    /** =================================================
     * Constructors
     * ===================================================== */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_alarms_symptoms, container, false);

        // Connect to Activity for user Prescriptions
        theActivity = (AlarmsActivity_Tabbed) getActivity();
        allSymptomsList = theActivity.getAllSymptoms();
        userSymptomsList = theActivity.getUserSymptoms();

        // Connect to spinners in the interface
        mSymptomNP1 = view.findViewById(R.id.symptom_picker_1);
        mSymptomNP2 = view.findViewById(R.id.symptom_picker_2);
        mSymptomNP3 = view.findViewById(R.id.symptom_picker_3);

        initSymptomPickers();
        return view;
    }

    /** =================================================
     * Functions for Controlling Spinners
     * ===================================================== */

    private void initSymptomPickers(){
        // Convoluted way to get the list as a final String[]
        String[] symptomListAsString = new String [allSymptomsList.size()];
        symptomListAsString = allSymptomsList.toArray(symptomListAsString);
        final String[] realList = symptomListAsString;

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
                theActivity.setSymptom1(realList[newVal]);
            }
        });

        // Number Picker 2
        mSymptomNP2.setMinValue(0);
        mSymptomNP2.setMaxValue(allSymptomsList.size()-1);
        mSymptomNP2.setDisplayedValues(realList);
        for (int i = 0; i < realList.length; i++){
            if (userSymptomsList.get(1).equals(realList[i])){
                mSymptomNP2.setValue(i);
            }
        }
        mSymptomNP2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                theActivity.setSymptom2(realList[newVal]);
            }
        });

        // Number Picker 3
        mSymptomNP3.setMinValue(0);
        mSymptomNP3.setMaxValue(allSymptomsList.size()-1);
        mSymptomNP3.setDisplayedValues(realList);
        for (int i = 0; i < realList.length; i++){
            if (userSymptomsList.get(2).equals(realList[i])){
                mSymptomNP3.setValue(i);
            }
        }
        mSymptomNP3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                theActivity.setSymptom3(realList[newVal]);
            }
        });
    }
}
