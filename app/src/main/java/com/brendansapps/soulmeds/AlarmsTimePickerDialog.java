package com.brendansapps.soulmeds;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by bt on 3/22/18.
 */

public class AlarmsTimePickerDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "AlarmsTimePickerDialog";
    private List<String> timesList;
    private int indexToEdit;

    public int currentHour;
    public int currentMinute;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        // Set default value to current time
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create TimePickerDialog instance
        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d(TAG, "Set time to: " + hourOfDay + " " + minute);
        currentHour = hourOfDay;
        currentMinute = minute;
    }

    public String getTime(){
        return (currentHour + ":" + currentMinute);
    }
}
