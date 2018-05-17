package com.brendansapps.soulmeds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EmergencyActivity extends AppCompatActivity {

    /** =================================================
     * Member Variables
     * ===================================================== */
    private static final String TAG = "EmergencyActivity";

    // Members for the Emergency Symptom List View
    private PrescriptionManager prescriptionManager;
    private ArrayList<String> allSymptomsList; // List of all possible symptoms
    private ListView symptomListView;
    private AlarmsListAdapter symptomsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        // Setup the Symptom List View
        prescriptionManager = new PrescriptionManager(getApplicationContext());
        allSymptomsList = prescriptionManager.getAllSymptoms();
        initSymptomListView();
    }

    // Populate the ListView by getting the data & setting the Adapter
    private void initSymptomListView(){
        // Setup List View
        symptomListView = findViewById(R.id.emergency_symptoms_lv);

        // List Item On Click Listener
        symptomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToMedsActivity(position);
            }
        });

        // Setup List Data Model
        symptomsListAdapter = new AlarmsListAdapter(getApplicationContext(), R.layout.alarms_list_item, R.id.alarms_list_item_TextView, allSymptomsList);
        symptomListView.setAdapter(symptomsListAdapter);
        registerForContextMenu(symptomListView);
    }

    private void goToMedsActivity(int indexOfSelection){
        Intent intent = new Intent(this, MedsActivity.class);
        intent.putExtra("symptom", prescriptionManager.getSymptom(indexOfSelection)); // pass the symptom
        startActivity(intent);
    }
}
