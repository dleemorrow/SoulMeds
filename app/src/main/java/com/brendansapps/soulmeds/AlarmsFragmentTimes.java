package com.brendansapps.soulmeds;

import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by bt on 3/14/18.
 *
 * Tab Of Alarms Activity for the Alarm Times
 */

public class AlarmsFragmentTimes extends Fragment {
    private static final String TAG = "alarms_fragment2";

    // Members for the Alarm Times List View
    private ListView timeListView;
    private AlarmsListAdapter timesListAdapter;
    private List<String> timesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment2_alarms, container, false);

        // Setup the Symptom List View
        Log.d(TAG, "Creating Alarm Times List View");
        timeListView = view.findViewById(R.id.alarms_list_times);
        initAlarmTimesData();
        timesListAdapter = new AlarmsListAdapter(this.getContext(), R.layout.alarms_list_item, R.id.alarms_list_item_TextView, timesList);
        timeListView.setAdapter(timesListAdapter);
        registerForContextMenu(timeListView);

        return view;
    }

    private void initAlarmTimesData(){
        timesList = getCurrentAlarmTimes();
        Log.d(TAG, String.valueOf(timesList));
    }

    private ArrayList<String> getCurrentAlarmTimes(){
        // TODO: Retrieve the saved list of the User's Alarm Times
        ArrayList<String> listOfCurrentTimes = new ArrayList<>();

        // Generating a fake list of user's  current Symptoms
        for (int i = 0; i < 3; i++){
            int randomHour = new Random().nextInt(24);
            int randomMinute = new Random().nextInt(50);
            randomMinute += 10;
            String randomTimeAsString = "" + randomHour + ":" + randomMinute;
            listOfCurrentTimes.add(randomTimeAsString);
        }

        return listOfCurrentTimes;
    }

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
                timesList.remove(indexSelected);
                timesListAdapter.notifyDataSetChanged();
                timesListAdapter.notifyDataSetInvalidated();
                Log.d(TAG, String.valueOf(timesList));
                break;
            case R.id.action_edit_time:
                Log.d(TAG, "Edit action pressed");

                // Get current data
                String oldHour = timesList.get(indexSelected);

                // Get New Time
                TimePickerDialog mAlarmTimePickerDialog;
                mAlarmTimePickerDialog = new TimePickerDialog(getContext(),
                    new TimePickerDialog.OnTimeSetListener(){
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            timesList.set(indexSelected, hourOfDay + ":" + minute);
                            timesListAdapter.notifyDataSetChanged();
                            timesListAdapter.notifyDataSetInvalidated();
                            Log.d(TAG, String.valueOf(timesList));
                    }
                }, 12, 30, false);
                mAlarmTimePickerDialog.show();

                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }


}
