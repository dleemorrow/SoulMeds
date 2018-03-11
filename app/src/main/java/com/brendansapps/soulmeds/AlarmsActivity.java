package com.brendansapps.soulmeds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlarmsActivity extends AppCompatActivity {

    private static final String TAG = "Alarm Activity";

    // Members for the Prescription List View
    private ExpandableListView prescriptionsListView;
    private PrescriptionsListAdapter prescriptionsListAdapter;
    private List<String> prescriptionListDataHeader;
    private HashMap<String, PrescriptionObject> prescriptionHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);

        // Setup the ExpandableListView
        prescriptionsListView = findViewById(R.id.prescriptionListView);
        initPrescriptionData();
        prescriptionsListAdapter = new PrescriptionsListAdapter(this, prescriptionListDataHeader, prescriptionHashMap);
        prescriptionsListView.setAdapter(prescriptionsListAdapter);

        // Listen for Prescription Clicked
        prescriptionsListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.d(TAG, "Child " + groupPosition + " was clicked");
                return true;
            }
        });

    }

    // Map listOfPrescriptions & the headers
    private void initPrescriptionData() {
        prescriptionListDataHeader = new ArrayList<>();
        prescriptionHashMap = new HashMap<>();

        List<PrescriptionObject> listOfPrescriptions = getAllPrescriptions();

        // Map the prescription headers with the PrescriptionObjects
        for (int i = 0; i < listOfPrescriptions.size(); i++){
            prescriptionListDataHeader.add("Prescription " + i + ": " + listOfPrescriptions.get(i).getSymptom());
            prescriptionHashMap.put(prescriptionListDataHeader.get(i), listOfPrescriptions.get(i));
        }
    }

    // Gets all of the prescriptions for the current user
    private List<PrescriptionObject> getAllPrescriptions(){
        ArrayList<PrescriptionObject> listOfPrescriptions = new ArrayList<>();

        // Generate fake prescriptions
        for (int i = 0; i < 5; i++){
            String alarmTime = "12:30";
            PrescriptionObject prescription = new PrescriptionObject("Symptom " + i, alarmTime);
            listOfPrescriptions.add(prescription);
        }

        return listOfPrescriptions;
    }
}
