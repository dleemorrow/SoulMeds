package com.brendansapps.soulmeds;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button mBtnAlarm, mBtnQuote, mBtnFolder, mBtnHelp;
    static private int numVisits = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Buttons
        mBtnAlarm = findViewById(R.id.btn_Alarm);
        mBtnQuote = findViewById(R.id.btn_Quote);
        mBtnFolder = findViewById(R.id.btn_Folder);
        mBtnHelp = findViewById(R.id.btn_Help);

        mBtnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAlarmsPage();

            }
        });

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
        numVisits++;
        Log.d(TAG, String.valueOf(numVisits));

        // Launch LoginActivity
        if (numVisits == 1){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
//            finish();
        }
    }

    // Function that navigates to a new page
    private void goToAlarmsPage(){
        Intent intent = new Intent(this, AlarmsActivity.class);
        startActivity(intent);
    }


}
