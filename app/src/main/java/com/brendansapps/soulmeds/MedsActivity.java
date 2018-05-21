package com.brendansapps.soulmeds;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    private static final String TAG = "MedsActivity";

    // Layout Accessors
    TextView mSymptomTV, mVerseTV, mReferenceTV;
    Button mNextButton, mBackButton, mDoneButton;
    ImageButton mFacebookShareButton, mTwitterShareButton;

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
        mNextButton = findViewById(R.id.next_btn_meds);
        mBackButton = findViewById(R.id.back_btn_meds);
        mDoneButton = findViewById(R.id.done_btn_meds);
        mFacebookShareButton = findViewById(R.id.facebook_share_btn);
        mTwitterShareButton = findViewById(R.id.twitter_share_btn);

        // Next Btn OnClickListener
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNextButton();
            }
        });

        // Back Btn OnClickListener
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBackButton();
            }
        });

        // Done Btn OnClickListener
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDoneButton();
            }
        });

        // Facebook Share OnClickListener
        mFacebookShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToFacebook();
            }
        });

        // Twitter Share OnClickListener
        mTwitterShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToTwitter();
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

        // Manage Next & Done button visibility
        if (currentVerseIndex == currentVersesList.size() - 1){
            mDoneButton.setVisibility(View.VISIBLE);
            mNextButton.setVisibility(View.INVISIBLE);
        }
        else {
            mDoneButton.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);

            // Manage Back button visibility
            if (currentVerseIndex == 0){
                mBackButton.setVisibility(View.INVISIBLE);
            }
            else {
                mBackButton.setVisibility(View.VISIBLE);
            }
        }
    }

    private void handleNextButton(){
        if (currentVerseIndex < currentVersesList.size() - 1){
            currentVerseIndex++;
            showVerse(currentVersesList.get(currentVerseIndex).first, currentVersesList.get(currentVerseIndex).second);
        }
    }

    private void handleBackButton(){
        if (currentVerseIndex > 0){
            currentVerseIndex--;
            showVerse(currentVersesList.get(currentVerseIndex).first, currentVersesList.get(currentVerseIndex).second);
        }
    }

    private void handleDoneButton(){
        finish();
    }

    // ===========================================================
    // Verse List Generators
    // ===========================================================

    /// Create a list of Symptom-VerseIndex pairs based off the specified format
    private ArrayList<Pair<String, Integer>> getVerses_formatted(){
        ArrayList<Pair<String, Integer>> newListOfVerses = new ArrayList<>();
        String nextSymptom;

        switch (userSymptoms.size()){
            case 1: // 1 symptom = all associated verses
                nextSymptom = userSymptoms.get(0);
                for (int nextVerseIndex = 0; nextVerseIndex < mPrescriptionManager.getNumVerses(nextSymptom); nextVerseIndex++){
                    Pair<String, Integer> nextVerse = new Pair<>(nextSymptom, nextVerseIndex);
                    newListOfVerses.add(nextVerse);
                }
                break;
            case 2: // 2 symptoms = 3 associated verses from each
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
            case 3: // 3 symptoms = 2 associated verses from each
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

    // ===========================================================
    // Social Media Sharing
    // ===========================================================
    private void shareToFacebook(){

    }

    private void shareToTwitter(){

    }
}
