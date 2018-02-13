package com.brendansapps.soulmeds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

/**
 * Created by bt on 2/12/18.
 */

public class MyAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Play default ringtone
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();
    }
}
