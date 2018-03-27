package com.brendansapps.soulmeds;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.provider.Settings;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by bt on 2/12/18.
 */

public class AlarmHandler extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Play default ringtone
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();
        // Vibrate
        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(10000);
    }
}
