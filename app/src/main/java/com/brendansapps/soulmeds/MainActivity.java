package com.brendansapps.soulmeds;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

public class MainActivity extends AppCompatActivity {

    Button mBtnAlarm, mBtnQuote, mBtnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Buttons
        mBtnAlarm = findViewById(R.id.btn_Alarm);
        mBtnQuote = findViewById(R.id.btn_Quote);
        mBtnHelp = findViewById(R.id.btn_Help);

        // Button OnClickListener
        mBtnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAlarmsPage();
            }
        });
        mBtnQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEmergencyPage();
            }
        });
        mBtnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSettingsPage();
            }
        });

        createNotificationChannel();
        checkForUpdates();
        goToLoginPage();
    }

    // Creates a channel for alarms notifications (API 26+, https://developer.android.com/training/notify-user/build-notification)
    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelID = getResources().getString(R.string.channel_id_alarms);
            String name = getResources().getString(R.string.channel_name);
            String description = getResources().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH; // sound & heads-up
            NotificationChannel channel = new NotificationChannel(channelID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }

    // ============================================================================
    // Navigation
    // ============================================================================

    // Login
    private void goToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Alarms
    private void goToAlarmsPage(){
        Intent intent = new Intent(this, AlarmsActivity_Tabbed.class);
        startActivity(intent);
    }

    // Emergency
    private void goToEmergencyPage(){
        Intent intent = new Intent(this, EmergencyActivity.class);
        startActivity(intent);
    }

    // Setting
    private void goToSettingsPage(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    // ============================================================================
    // Lifecycle events for HockeyApp CrashReporting & Beta-Distrobution
    // ============================================================================
    @Override
    public void onResume() {
        super.onResume();
        checkForCrashes();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }

    // HockeyApp CrashReporting & Beta-Distrobution@Override
    private void checkForCrashes() {
        CrashManager.register(this);
    }

    // HockeyApp CrashReporting & Beta-Distrobution@Override
    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    // HockeyApp CrashReporting & Beta-Distrobution@Override
    private void unregisterManagers() {
        UpdateManager.unregister();
    }
}
