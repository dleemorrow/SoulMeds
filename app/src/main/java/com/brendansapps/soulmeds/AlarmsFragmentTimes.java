package com.brendansapps.soulmeds;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/** =================================================
 * Created by bt on 3/14/18.
 *
 * Tab Of Alarms Activity for the Alarm Times
 * Manages the user's Prescription Alarm Times
 * ===================================================== */

public class AlarmsFragmentTimes extends Fragment {

    /** =================================================
     * Member Variables
     * ===================================================== */
    private static final String TAG = "AlarmTimesFragment";
    private FloatingActionButton addAlarmButton;

    // Members for the Alarm Times List View
    private PrescriptionManager prescriptionManager;
    private ListView timeListView;
    private AlarmsListAdapter timesListAdapter;
    private ArrayList<String> timesList;

    /** =================================================
     * Constructors
     * ===================================================== */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment2_alarms, container, false);

        // Setup the Symptom List View
        prescriptionManager = new PrescriptionManager(this.getContext());
        timesList = prescriptionManager.getUserTimes();
        initAlarmTimesListView(view);
//        prescriptionManager.printTimes();

        // Setup the Add Alarm Button
        addAlarmButton = view.findViewById(R.id.action_add_alarm);
        addAlarmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addAlarmTime();
            }
        });

        return view;
    }

    private void initAlarmTimesListView(View view){
        timeListView = view.findViewById(R.id.alarms_list_times);
        Log.d(TAG, "User's Times: " + String.valueOf(timesList));
        timesListAdapter = new AlarmsListAdapter(this.getContext(), R.layout.alarms_list_item, R.id.alarms_list_item_TextView, timesList);
        timeListView.setAdapter(timesListAdapter);
        registerForContextMenu(timeListView);
    }

    // Re-draw the ListView
    private void resetTimesList(){
        timesListAdapter.notifyDataSetChanged();
        timesListAdapter.notifyDataSetInvalidated();
        prescriptionManager.printTimes();
    }

    /** =================================================
     * Functions for Manipulating the ListView
     * ===================================================== */
    // Add a new Alarm Time
    private void addAlarmTime(){
        TimePickerDialog mAlarmTimePickerDialog;
        mAlarmTimePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timesList.add(getTimeInAMPM(hourOfDay, minute));
                        prescriptionManager.addTime(getTimeInAMPM(hourOfDay, minute));
                        resetTimesList();
                    }
                }, 12, 30, false);
        mAlarmTimePickerDialog.show();
    }

    private void deleteAlarmTime(int index){
        timesList.remove(index);
        prescriptionManager.deleteTime(index);
        resetTimesList();
    }

    // Edit the selected Alarm Time
    private void editAlarmTime(final int indexSelected){
        // Get what the time was to use as default value
        String currentTime = timesList.get(indexSelected);
        String currentHour_string;
        String currentMinute_string;
        Pattern timePattern = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]) (AM|PM)$");
        Matcher m = timePattern.matcher(currentTime);
        m.find();
        currentHour_string = m.group(1);
        currentMinute_string = m.group(2);
        Log.d(TAG, "Time: " + currentHour_string + ":" + currentMinute_string);
        int currentHour = Integer.parseInt(currentHour_string);
        int currentMinute = Integer.parseInt(currentMinute_string);

        // Get New Time from TimePickerDialog
        TimePickerDialog mAlarmTimePickerDialog;
        mAlarmTimePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timesList.set(indexSelected, getTimeInAMPM(hourOfDay, minute));
                        prescriptionManager.editTime(indexSelected, getTimeInAMPM(hourOfDay, minute));
                        resetTimesList();
                    }
                }, currentHour, currentMinute, false);
        mAlarmTimePickerDialog.show();
    }

    // Returns a String representing the time in AM|PM format
    public String getTimeInAMPM(int hour, int minute){
        String newTime;

        // Create Hour
        if (hour > 12) {
            hour -= 12;
        }
        newTime = hour + ":";

        // Create Minute
        if (minute < 10) {
            newTime = newTime + "0" + minute + " PM";
        } else {
            newTime = newTime + minute + " PM";
        }

        Log.d(TAG, "Time in AMPM = " + newTime);
        return newTime;
    }

    /** =================================================
     * Functions for each Item's ContextMenu
     * ===================================================== */
    // Create context menu for each item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = new MenuInflater(this.getContext());
        menuInflater.inflate(R.menu.alarms_time_context_menu, menu);
    }

    // Handle context menu action selected
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo obj = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int indexSelected = obj.position;

        switch (item.getItemId()){
            case R.id.action_delete_time:
                deleteAlarmTime(indexSelected);
                break;
            case R.id.action_edit_time:
                editAlarmTime(indexSelected);
                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }
}
