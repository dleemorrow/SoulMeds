package com.brendansapps.soulmeds;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/** ===========================================================
 * MedsActivity.java Created by bt on 3/31/18.
 *
 * Displays Verses when notification clicked or for EmergencyActivity
 *      1 symptom = all associated verses
 *      2 symptom = 3 associated verses from each
 *      3 symptom = 2 associated verses from each
 *
 * Also shares VerseCards to Facebook & Twitter
 * =========================================================== */

public class MedsActivity extends AppCompatActivity {

    /** ===========================================================
     * Member Variables
     * =========================================================== */

    // Layout Accessors
    TextView mSymptomTV, mVerseTV, mReferenceTV;
    Button mNextButton, mBackButton, mDoneButton;
    ImageButton mFacebookShareButton, mTwitterShareButton;

    // Data Model
    Integer currentVerseIndex;
    PrescriptionManager mPrescriptionManager;
    ArrayList<String> userSymptoms;
    ArrayList<Pair<String, Integer>> currentVersesList;

    /** ===========================================================
     * Constructor
     * =========================================================== */

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

    /** ===========================================================
     * State Managers
     * =========================================================== */

    // Show the verse at the index for that symptom
    private void showVerse(String symptom, int verseIndex){
        mSymptomTV.setText(symptom);
        mVerseTV.setText(mPrescriptionManager.getVerse(symptom, verseIndex));
        mReferenceTV.setText(mPrescriptionManager.getReference(symptom, verseIndex));
        mDoneButton.setVisibility(View.VISIBLE);

        // Manage Next & Done button visibility
        if (currentVerseIndex == currentVersesList.size() - 1){
            // mDoneButton.setVisibility(View.VISIBLE);
            mNextButton.setVisibility(View.INVISIBLE);
        }
        else {
            // mDoneButton.setVisibility(View.INVISIBLE);
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
        Intent intent = new Intent(MedsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /** ===========================================================
     * Verse List Generators
     * =========================================================== */

    /// Create a list of Symptom-VerseIndex pairs based off the specified format
    private ArrayList<Pair<String, Integer>> getVerses_formatted(){
        ArrayList<Pair<String, Integer>> newListOfVerses = new ArrayList<>();
        String nextSymptom;

        // generate a random list of all verses
        ArrayList<Integer> verseList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            verseList.add(i);
        }

        // remove None options
        ArrayList<String> symptomsList = new ArrayList<>(userSymptoms);
        while(true){
            if (!symptomsList.remove("None")) break;
        }

        switch (symptomsList.size()){
            case 1: // 1 symptom = all associated verses
                nextSymptom = symptomsList.get(0);
                for (int nextVerse = 0; nextVerse < 5; nextVerse++){
                    Pair<String, Integer> verse = new Pair<>(nextSymptom, verseList.get(nextVerse));
                    newListOfVerses.add(verse);
                }
                break;
            case 2: // 2 symptoms = 3 associated verses from each
                // Use first 3 verses of random list for the first symptom
                Collections.shuffle(verseList);
                nextSymptom = symptomsList.get(0);
                for (int nextVerse = 0; nextVerse < 3; nextVerse++){
                    //pick random verse of remaining verses
                    Pair<String, Integer> verse = new Pair<>(nextSymptom, verseList.get(nextVerse));
                    newListOfVerses.add(verse);
                }
                // Use remaining 3 verses for the second symptom
                Collections.shuffle(verseList);
                nextSymptom = symptomsList.get(1);
                for (int nextVerse = 0; nextVerse < 3; nextVerse++){
                    Pair<String, Integer> verse = new Pair<>(nextSymptom, verseList.get(nextVerse));
                    newListOfVerses.add(verse);
                }
                break;
            case 3: // 3 symptoms = 2 associated verses from each
                // Use first 2 verses for the first symptom
                Collections.shuffle(verseList);
                nextSymptom = symptomsList.get(0);
                for (int nextVerse = 0; nextVerse < 2; nextVerse++){
                    Pair<String, Integer> verse = new Pair<>(nextSymptom, verseList.get(nextVerse));
                    newListOfVerses.add(verse);
                }
                // Use next 2 verses for the second symptom
                Collections.shuffle(verseList);
                nextSymptom = symptomsList.get(1);
                for (int nextVerse = 0; nextVerse < 2; nextVerse++){
                    Pair<String, Integer> verse = new Pair<>(nextSymptom, verseList.get(nextVerse));
                    newListOfVerses.add(verse);
                }
                // Use final 2 verses for the third symptom
                Collections.shuffle(verseList);
                nextSymptom = symptomsList.get(2);
                for (int nextVerse = 0; nextVerse < 2; nextVerse++){
                    Pair<String, Integer> verse = new Pair<>(nextSymptom, verseList.get(nextVerse));
                    newListOfVerses.add(verse);
                }
                break;
            default:
                newListOfVerses = getVerses_random();
                break;
        }

        return newListOfVerses;
    }

    // Create a list of Symptom-VerseIndex pairs given a specific Symptom
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

    /** ===========================================================
    * Social Media Sharing
    * =========================================================== */

    // Send a toast saying "Option Not Yet Available"
    private void printNotYetAvailableToast(){
        Context context = getApplicationContext();
        CharSequence text = "Option Not Yet Available";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    // There is a better way to share to Facebook using the Facebook SDK
    // This version is just a copy of the Twitter version below
    private void shareToFacebook(){
        try {
            // Prepare Intent
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
            shareIntent.setType("image/jpeg");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Try to connect to facebook app
            PackageManager pm = this.getPackageManager();
            List<ResolveInfo> lract = pm.queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
            boolean resolved = false;
            for (ResolveInfo ri : lract) {
                if (ri.activityInfo.name.contains("facebook")) {
                    shareIntent.setClassName(ri.activityInfo.packageName,
                            ri.activityInfo.name);
                    resolved = true;
                    break;
                }
            }

            // Send it to facebook, else ask
            startActivity(resolved ?
                    shareIntent :
                    Intent.createChooser(shareIntent, "Choose one"));
        } catch (final ActivityNotFoundException e) {
            Toast.makeText(this, "You don't seem to have facebook installed on this device", Toast.LENGTH_SHORT).show();
        }
    }

    // https://stackoverflow.com/questions/19120036/add-image-to-twitter-share-intent-android
    private void shareToTwitter(){
        try {
            String share_text =  "\n\n\"" + mPrescriptionManager.getVerse(currentVersesList.get(currentVerseIndex).first, currentVersesList.get(currentVerseIndex).second)
                    + "\"" + " - " + mPrescriptionManager.getReference(currentVersesList.get(currentVerseIndex).first, currentVersesList.get(currentVerseIndex).second);

            // Prepare Intent
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text) + share_text);
            shareIntent.setType("image/jpeg");

            // Try to connect to twitter app
            PackageManager pm = this.getPackageManager();
            List<ResolveInfo> lract = pm.queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
            boolean resolved = false;
            for (ResolveInfo ri : lract) {
                if (ri.activityInfo.name.contains("twitter")) {
                    shareIntent.setClassName(ri.activityInfo.packageName,
                            ri.activityInfo.name);
                    resolved = true;
                    break;
                }
            }

            // Send it to twitter, else ask
            startActivity(resolved ?
                    shareIntent :
                    Intent.createChooser(shareIntent, "Choose one"));
        } catch (final ActivityNotFoundException e) {
            Toast.makeText(this, "You don't seem to have twitter installed on this device", Toast.LENGTH_SHORT).show();
        }
    }
}
