package com.brendansapps.soulmeds;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Prescription Info
        PrescriptionManager mPrescriptionManager = new PrescriptionManager(context);

        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
            // set repeating alarm
            mPrescriptionManager.resetAlarms();
        }
    }
}
