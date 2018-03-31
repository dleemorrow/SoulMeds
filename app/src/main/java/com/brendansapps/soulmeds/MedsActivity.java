package com.brendansapps.soulmeds;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MedsActivity extends AppCompatActivity {

    // Layout Accessors
    TextView mSymptomTV;
    TextView mVerseTV;
    TextView mReferenceTV;
    FloatingActionButton mRefreshBtn;

    // Prescription Manager
    PrescriptionManager mPrescriptionManager;
    ArrayList<String> userSymptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meds);

        // Get Prescription
        mPrescriptionManager = new PrescriptionManager(this);
        userSymptoms = mPrescriptionManager.getUserSymptoms();

        // Sync local references to layout
        mSymptomTV = findViewById(R.id.tv_symptom);
        mVerseTV = findViewById(R.id.tv_verse);
        mReferenceTV = findViewById(R.id.tv_reference);
        mRefreshBtn = findViewById(R.id.action_new_verse);

        // Refresh Btn OnClickListener
        mRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRandomVerse();
            }
        });

        showRandomVerse();
    }

    // Show Random Symptom with a random associated Verse & Reference
    private void showRandomVerse(){
        int symptomIndex = new Random().nextInt(userSymptoms.size());
        int verseIndex = new Random().nextInt(mPrescriptionManager.getNumVerses(userSymptoms.get(symptomIndex)));
        mSymptomTV.setText(userSymptoms.get(symptomIndex));
        mVerseTV.setText(mPrescriptionManager.getVerse(userSymptoms.get(symptomIndex), verseIndex));
        mReferenceTV.setText(mPrescriptionManager.getReference(userSymptoms.get(symptomIndex), verseIndex));
    }
}
