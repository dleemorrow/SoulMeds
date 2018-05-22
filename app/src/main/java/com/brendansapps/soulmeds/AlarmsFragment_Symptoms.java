package com.brendansapps.soulmeds;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    // Members for the Spinners
    Spinner mSpinner1;
    Spinner mSpinner2;
    Spinner mSpinner3;

    // Members for the Symptom Data
    private PrescriptionManager mPrescriptionManager;
    private ArrayList<String> allSymptomsList; // List of all possible symptoms

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
        initSpinners();

        return view;
    }

    /** =================================================
     * Functions for Controlling Spinners
     * ===================================================== */

    private void initSpinners(){
        ArrayAdapter<String> mAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, allSymptomsList);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner1.setAdapter(mAdapter);
        mSpinner2.setAdapter(mAdapter);
        mSpinner3.setAdapter(mAdapter);
    }
}
