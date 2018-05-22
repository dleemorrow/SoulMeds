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
    private Boolean isInitializing = true;

    // Members for the Spinners
    private Spinner mSpinner1, mSpinner2, mSpinner3;

    // Members for the Symptom Data
    private PrescriptionManager mPrescriptionManager;
    private ArrayList<String> allSymptomsList; // List of all possible symptoms
    private ArrayList<String> userSymptomsList; // List of user's symptoms

    /** =================================================
     * Constructors
     * ===================================================== */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_alarms_symptoms, container, false);

        // Connect to spinners in the interface
        mSpinner1 = view.findViewById(R.id.spinner_1);
        mSpinner2 = view.findViewById(R.id.spinner_2);
        mSpinner3 = view.findViewById(R.id.spinner_3);

        // Get Prescription Data & initialize spinners
        mPrescriptionManager = new PrescriptionManager(this.getContext());
        allSymptomsList = mPrescriptionManager.getAllSymptoms();
        userSymptomsList = mPrescriptionManager.getUserSymptoms();

        initSpinners();
        return view;
    }

    /** =================================================
     * Functions for Controlling Spinners
     * ===================================================== */

    private void initSpinners(){
        Log.d(TAG, "Initializing spinners...");
        isInitializing = true;
        ArrayAdapter<String> mAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, allSymptomsList);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner1.setAdapter(mAdapter);
        mSpinner2.setAdapter(mAdapter);
        mSpinner3.setAdapter(mAdapter);

        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isInitializing){
                    editSymptom(0, allSymptomsList.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isInitializing){
                    editSymptom(1, allSymptomsList.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isInitializing){
                    editSymptom(2, allSymptomsList.get(position));
                }
                else {
                    isInitializing = false;
                    Log.d(TAG, "Done Initializing");

                    mSpinner1.setSelection(allSymptomsList.indexOf(userSymptomsList.get(0)));
                    mSpinner2.setSelection(allSymptomsList.indexOf(userSymptomsList.get(1)));
                    mSpinner3.setSelection(allSymptomsList.indexOf(userSymptomsList.get(2)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /** =================================================
     * Functions for Controlling User Symptoms
     * ===================================================== */

    // Edits the symptom at index to be newSymptom
    private void editSymptom(int index, String newSymptom){
        mPrescriptionManager.editSymptom(index, newSymptom);
        userSymptomsList = mPrescriptionManager.getUserSymptoms();
        Log.d(TAG, "Symptoms Edited: " + userSymptomsList);
    }
}
