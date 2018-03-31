package com.brendansapps.soulmeds;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by bt on 2/12/18.
 *
 * Handles what happens when an alarm goes off
 */

public class AlarmHandler extends BroadcastReceiver {

    private static final String notificationTitle = "Soul Meds";
    private static final String notificationMessage = "An alarm has gone off";
    private static final int REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 6578;

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;

        // Ringtone & Vibrate
        playNoiseAndVibrate();

        // Send Notification
        showNotification();
    }

    private void playNoiseAndVibrate(){

        // Play default ringtone
        final MediaPlayer mediaPlayer = MediaPlayer.create(mContext, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();

        // Vibrate
        Vibrator vibrator = (Vibrator) mContext.getSystemService(mContext.VIBRATOR_SERVICE);
        vibrator.vibrate(10000);

        // Stop Ringtone
        CountDownTimer timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Nothing to do
            }

            @Override
            public void onFinish() {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }
        };
        timer.start();
    }

    private void showNotification(){
        Intent intent = new Intent(mContext, MedsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(mContext)
                .setContentText(notificationMessage)
                .setContentTitle(notificationTitle)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .build();

        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, notification);
    }
}
