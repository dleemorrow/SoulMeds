package com.brendansapps.soulmeds;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

public class BootReceiver extends BroadcastReceiver {

    //rebuild from prescription manager / other apps? maybe some private fields not necessary
    private Context mContext;

    // Prescription Info
    private PrescriptionManager mPrescriptionManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;
        mPrescriptionManager = new PrescriptionManager(mContext);

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // set repeating alarm
            mContext = context;
            mPrescriptionManager.resetAlarms();
//            temp.set(context);
        }
    }
}
