package com.brendansapps.soulmeds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mBtnAlarm, mBtnQuote, mBtnFolder, mBtnHelp;

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

            }
        });

        mBtnQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBtnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBtnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
