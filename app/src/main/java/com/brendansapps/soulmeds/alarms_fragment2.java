package com.brendansapps.soulmeds;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by bt on 3/14/18.
 *
 * Tab Of Alarms Activity for the Alarm Times
 */

public class alarms_fragment2 extends Fragment {
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


}
