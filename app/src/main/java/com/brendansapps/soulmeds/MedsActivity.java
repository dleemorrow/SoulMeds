package com.brendansapps.soulmeds;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class MedsActivity extends AppCompatActivity {

    // ===========================================================
    // Member Variables
    // ===========================================================

    // Layout Accessors
    TextView mSymptomTV;
    TextView mVerseTV;
    TextView mReferenceTV;
    FloatingActionButton mRefreshBtn;

    // Data Model
    Integer currentVerseIndex;
    PrescriptionManager mPrescriptionManager;
    ArrayList<String> userSymptoms;
    ArrayList<Pair<String, Integer>> currentVersesList;

    // ===========================================================
    // Constructor
    // ===========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meds);

        // Get symptom & verse data
        mPrescriptionManager = new PrescriptionManager(this);

        // Setup list of verses
        if (getIntent().getStringExtra("symptom") != null){
            // if symptom specified (coming from EmergencyActivity), use the symptom to generate the list of verses
            currentVersesList = getVerses_specified(getIntent().getStringExtra("symptom"));
        }
        else {
            // else generate list of verses based off userSymptoms
            userSymptoms = mPrescriptionManager.getUserSymptoms();
            currentVersesList = getVerses_formatted();
        }

        // Sync local references to layout
        mSymptomTV = findViewById(R.id.tv_symptom);
        mVerseTV = findViewById(R.id.tv_verse);
        mReferenceTV = findViewById(R.id.tv_reference);
        mRefreshBtn = findViewById(R.id.action_new_verse);

        // Refresh Btn OnClickListener
        mRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNextButton();
            }
        });

        // Show First Verse
        currentVerseIndex = 0;
        showVerse(currentVersesList.get(currentVerseIndex).first, currentVersesList.get(currentVerseIndex).second);
    }

    // ===========================================================
    // State Managers
    // ===========================================================

    // Show the verse at the index for that symptom
    private void showVerse(String symptom, int verseIndex){
        mSymptomTV.setText(symptom);
        mVerseTV.setText(mPrescriptionManager.getVerse(symptom, verseIndex));
        mReferenceTV.setText(mPrescriptionManager.getReference(symptom, verseIndex));
    }

    private void handleNextButton(){
        currentVerseIndex++;
        if (currentVerseIndex < currentVersesList.size()){
            showVerse(currentVersesList.get(currentVerseIndex).first, currentVersesList.get(currentVerseIndex).second);
        }
        else {
            finish();
        }
    }

    // ===========================================================
    // Verse List Generators
    // ===========================================================

    /// Create a list of Symptom-VerseIndex pairs based off the specified format
        // 1 symptom = all associated verses
        // 2 symptoms = 3 associated verses from each
        // 3 symptoms = 2 associated verses from each
    private ArrayList<Pair<String, Integer>> getVerses_formatted(){
        ArrayList<Pair<String, Integer>> newListOfVerses = new ArrayList<>();
        String nextSymptom;

        switch (userSymptoms.size()){
            case 1:
                nextSymptom = userSymptoms.get(0);
                for (int nextVerseIndex = 0; nextVerseIndex < mPrescriptionManager.getNumVerses(nextSymptom); nextVerseIndex++){
                    Pair<String, Integer> nextVerse = new Pair<>(nextSymptom, nextVerseIndex);
                    newListOfVerses.add(nextVerse);
                }
                break;
            case 2:
                // Use first 3 verses for the first symptom
                nextSymptom = userSymptoms.get(0);
                for (int nextVerseIndex = 0; nextVerseIndex < 3; nextVerseIndex++){
                    Pair<String, Integer> nextVerse = new Pair<>(nextSymptom, nextVerseIndex);
                    newListOfVerses.add(nextVerse);
                }
                // Use first 3 verses for the second symptom
                nextSymptom = userSymptoms.get(1);
                for (int nextVerseIndex = 0; nextVerseIndex < 3; nextVerseIndex++){
                    Pair<String, Integer> nextVerse = new Pair<>(nextSymptom, nextVerseIndex);
                    newListOfVerses.add(nextVerse);
                }
                break;
            case 3:
                // Use first 2 verses for the first symptom
                nextSymptom = userSymptoms.get(0);
                for (int nextVerseIndex = 0; nextVerseIndex < 2; nextVerseIndex++){
                    Pair<String, Integer> nextVerse = new Pair<>(nextSymptom, nextVerseIndex);
                    newListOfVerses.add(nextVerse);
                }
                // Use first 2 verses for the second symptom
                nextSymptom = userSymptoms.get(1);
                for (int nextVerseIndex = 0; nextVerseIndex < 2; nextVerseIndex++){
                    Pair<String, Integer> nextVerse = new Pair<>(nextSymptom, nextVerseIndex);
                    newListOfVerses.add(nextVerse);
                }
                // Use first 2 verses for the third symptom
                nextSymptom = userSymptoms.get(2);
                for (int nextVerseIndex = 0; nextVerseIndex < 2; nextVerseIndex++){
                    Pair<String, Integer> nextVerse = new Pair<>(nextSymptom, nextVerseIndex);
                    newListOfVerses.add(nextVerse);
                }
                break;
            default:
                newListOfVerses = getVerses_random();
                break;
        }

        return newListOfVerses;
    }

    // Create a list given a specific symptom
    private ArrayList<Pair<String, Integer>> getVerses_specified(String symptom){
        ArrayList<Pair<String, Integer>> newListOfVerses = new ArrayList<>();

        // List the verses in order for the specified symptom
        for (int i = 0; i < mPrescriptionManager.getNumVerses(symptom); i++){
            Pair<String, Integer> nextVerse = new Pair<>(symptom, i);
            newListOfVerses.add(nextVerse);
        }

        return newListOfVerses;
    }

    // Create a list of Symptom-VerseIndex pairs that are random and don't have duplicates
    private ArrayList<Pair<String, Integer>> getVerses_random(){
        ArrayList<Pair<String, Integer>> newListOfVerses = new ArrayList<>();

        // Generate a random list without duplicates
        for (int i = 0; i < 6; i++){
            int symptomIndex = new Random().nextInt(mPrescriptionManager.getNumSymptoms());
            int verseIndex = new Random().nextInt(mPrescriptionManager.getNumVerses(userSymptoms.get(symptomIndex)));
            Pair<String, Integer> nextVerse = new Pair<>(mPrescriptionManager.getSymptom(symptomIndex), verseIndex);
            if (!newListOfVerses.contains(nextVerse)) {
                newListOfVerses.add(nextVerse);
            }
            else {
                i--;
            }
        }

        return newListOfVerses;
    }
}
