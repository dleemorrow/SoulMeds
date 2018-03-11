package com.brendansapps.soulmeds;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button mBtnAlarm, mBtnQuote, mBtnFolder, mBtnHelp; // UI Buttons

    FirebaseAuth mFirebaseAuth; // used for checking if user is logged in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Buttons
        mBtnAlarm = findViewById(R.id.btn_Alarm);
        mBtnQuote = findViewById(R.id.btn_Quote);
        mBtnFolder = findViewById(R.id.btn_Folder);
        mBtnHelp = findViewById(R.id.btn_Help);

        // Alarm Button Listener
        mBtnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAlarmsPage();

            }
        });

        // Quote Button Listener
        mBtnQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Send a toast saying Option Not Yet Available
                Context context = getApplicationContext();
                CharSequence text = "Option Not Yet Available";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });

        // Folder Button Listener
        mBtnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Send a toast saying Option Not Yet Available
                Context context = getApplicationContext();
                CharSequence text = "Option Not Yet Available";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });

        // Help Button Listener
        mBtnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Send a toast saying Option Not Yet Available
                Context context = getApplicationContext();
                CharSequence text = "Option Not Yet Available";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // If not logged in launch LoginActivity
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    // Function that navigates to a new page
    private void goToAlarmsPage(){
        Intent intent = new Intent(this, AlarmsActivity.class);
        startActivity(intent);
    }


}
