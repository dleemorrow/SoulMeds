package com.brendansapps.soulmeds;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by bt on 2/12/18.
 */

public class alarms_fragment1 extends Fragment {
    private static final String TAG = "alarms_fragment1";

    private Button mAlarmButton;
    private TimePicker mTimePicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment1_alarms, container, false);

        // Find the Alarm
        mAlarmButton = view.findViewById(R.id.setAlarmBtn1);
        mTimePicker = view.findViewById(R.id.alarmTimePicker1);

      mAlarmButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Calendar calendar = Calendar.getInstance();

               if (Build.VERSION.SDK_INT >= 23) {
                   calendar.set(
                           calendar.get(Calendar.YEAR),
                           calendar.get(Calendar.MONTH),
                           calendar.get(Calendar.DAY_OF_YEAR),
                           mTimePicker.getHour(),
                           mTimePicker.getMinute(),
                           0
                   );
               }
               else {
                   calendar.set(
                           calendar.get(Calendar.YEAR),
                           calendar.get(Calendar.MONTH),
                           calendar.get(Calendar.DAY_OF_YEAR),
                           mTimePicker.getCurrentHour(),
                           mTimePicker.getCurrentMinute(),
                           0
                   );
               }

               setAlarm(calendar.getTimeInMillis());
           }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void setAlarm(long timeInMillis) {
        Context context = this.getContext();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context, "Alarm is Set", Toast.LENGTH_SHORT).show();
    }


}
