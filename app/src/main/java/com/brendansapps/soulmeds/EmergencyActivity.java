package com.brendansapps.soulmeds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;

import java.util.ArrayList;

/** =================================================
 * Created by bt on 5/13/18.
 *
 * Emergency Activity for selecting an emergency symptoms
 * Selected symptom sent to MedsActivity where related verses are displayed
 * ===================================================== */

public class EmergencyActivity extends AppCompatActivity {

    /** =================================================
     * Member Variables
     * ===================================================== */
    private static final String TAG = "EmergencyActivity";

    private Button nextBtn, cancelBtn;

    // Members for the Emergency Symptom Picker
    private PrescriptionManager prescriptionManager;
    private ArrayList<String> allSymptomsList; // List of all possible symptoms
    private NumberPicker symptomsDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        // Setup the Symptom List View
        prescriptionManager = new PrescriptionManager(getApplicationContext());
        allSymptomsList = prescriptionManager.getAllSymptoms();
        initSymptomListView();

        // Setup Next Button
        nextBtn = findViewById(R.id.emergency_btn_next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMedsActivity();
            }
        });

        // Setup Cancel Button
        cancelBtn = findViewById(R.id.emergency_btn_back);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Populate the ListView by getting the data & setting the Adapter
    private void initSymptomListView(){
        // Setup List View
        symptomsDisplay = findViewById(R.id.emergency_symptom_np);

        String[] symptomListAsString = new String [allSymptomsList.size()];
        symptomListAsString = allSymptomsList.toArray(symptomListAsString);
        final String[] realList = symptomListAsString;

        // Number Picker 1
        symptomsDisplay.setMinValue(0);
        symptomsDisplay.setMaxValue(realList.length-1);
        symptomsDisplay.setDisplayedValues(realList);
    }

    private void goToMedsActivity(){
        Intent intent = new Intent(this, MedsActivity.class);
        intent.putExtra("symptom", allSymptomsList.get(symptomsDisplay.getValue())); // pass the symptom
        startActivity(intent);
        finish();
    }
}
