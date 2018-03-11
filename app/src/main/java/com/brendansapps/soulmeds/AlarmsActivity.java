package com.brendansapps.soulmeds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlarmsActivity extends AppCompatActivity {

    private static final String TAG = "Alarm Activity";

    // Members for the Prescription List View
    private ExpandableListView prescriptionsListView;
    private PrescriptionsListAdapter prescriptionsListAdapter;
    private List<String> prescriptionListDataHeader;
    private HashMap<String, List<String>> prescriptionHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);

        prescriptionsListView = findViewById(R.id.prescriptionListView);
        initPrescriptionData();
        prescriptionsListAdapter = new PrescriptionsListAdapter(this, prescriptionListDataHeader, prescriptionHashMap);
        prescriptionsListView.setAdapter(prescriptionsListAdapter);

    }

    private void initPrescriptionData() {
        prescriptionListDataHeader = new ArrayList<>();
        prescriptionHashMap = new HashMap<>();

        prescriptionListDataHeader.add("Prescription1");
        prescriptionListDataHeader.add("Prescription2");
        prescriptionListDataHeader.add("Prescription3");
        prescriptionListDataHeader.add("Prescription4");
        prescriptionListDataHeader.add("Prescription5");
        prescriptionListDataHeader.add("Prescription6");

        List<String> prescription1 = new ArrayList<>();
        prescription1.add("Property1");
        prescription1.add("Property2");

        List<String> prescription2 = new ArrayList<>();
        prescription2.add("Property1");
        prescription2.add("Property2");
        prescription2.add("Property3");

        List<String> prescription3 = new ArrayList<>();
        prescription3.add("Property1");
        prescription3.add("Property2");
        prescription3.add("Property3");
        prescription3.add("Property4");

        List<String> prescription4 = new ArrayList<>();
        prescription4.add("Property1");
        prescription4.add("Property2");

        List<String> prescription5 = new ArrayList<>();
        prescription5.add("Property1");
        prescription5.add("Property2");
        prescription5.add("Property3");
        prescription5.add("Property4");

        List<String> prescription6 = new ArrayList<>();
        prescription6.add("Property1");

        prescriptionHashMap.put(prescriptionListDataHeader.get(0), prescription1);
        prescriptionHashMap.put(prescriptionListDataHeader.get(1), prescription2);
        prescriptionHashMap.put(prescriptionListDataHeader.get(2), prescription3);
        prescriptionHashMap.put(prescriptionListDataHeader.get(3), prescription4);
        prescriptionHashMap.put(prescriptionListDataHeader.get(4), prescription5);
        prescriptionHashMap.put(prescriptionListDataHeader.get(5), prescription6);
    }
}
