package com.brendansapps.soulmeds;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by bt on 2/12/18.
 *
 * Handles what happens when an alarm goes off
 * Notification Documentation: https://developer.android.com/guide/topics/ui/notifiers/notifications
 * NotificationCompat.Builder Documentation: https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder
 */

public class AlarmHandler extends BroadcastReceiver {

    private static final String TAG = "AlarmHandler";
    private static final String notificationTitle = "Soul Meds";
    private static final String notificationMessage = "An alarm has gone off";
    private static final int NOTIFICATION_ID = 6578;

    // Prescription Info
    private Context mContext;
    private PrescriptionManager mPrescriptionManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Receiving an Alarm");
        mContext = context;

        showNotification();

        mPrescriptionManager = new PrescriptionManager(mContext);
//        mPrescriptionManager.setNextAlarms();
    }

    private void showNotification(){
        // Setup Intent for when Notification clicked
        Intent intent = new Intent(mContext, MedsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // See https://developer.android.com/training/notify-user/navigation for better navigation
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

        // Setup Ringtone & Vibrate
        Uri alarmSound = Settings.System.DEFAULT_NOTIFICATION_URI;

        // Setup Notification
        String channelID = mContext.getResources().getString(R.string.channel_id_alarms);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, channelID)
                .setContentText(notificationMessage)
                .setContentTitle(notificationTitle)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setSound(alarmSound, AudioManager.STREAM_NOTIFICATION)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .setLights(Color.YELLOW, 3000, 3000);

        // Send Notification
        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
