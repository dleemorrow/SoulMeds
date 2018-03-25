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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by bt on 3/14/18.
 *
 * Tab Of Alarms Activity for the Symptoms
 */

public class alarms_fragment1 extends Fragment {
    private static final String TAG = "alarms_fragment1";

    // Members for the Alarm Symptom List View
    private ListView symptomListView;
    private AlarmsListAdapter symptomsListAdapter;
    private List<String> symptomsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment1_alarms, container, false);

        initSymptomListView(view);

        return view;
    }

    // Populate the ListView by getting the Data & setting the Adapter
    private void initSymptomListView(View view){
        Log.d(TAG, "Creating Symptom List View");
        symptomListView = view.findViewById(R.id.alarms_list_symptoms);
        initSymptomData();
        symptomsListAdapter = new AlarmsListAdapter(this.getContext(), R.layout.alarms_list_item, R.id.alarms_list_item_TextView, symptomsList);
        symptomListView.setAdapter(symptomsListAdapter);
        registerForContextMenu(symptomListView);
    }

    // Get the symptom data for the current user
    private void initSymptomData(){
        symptomsList = getCurrentSymptoms();
        Log.d(TAG, String.valueOf(symptomsList));
    }

    // Generating a list of symptoms
    private ArrayList<String> getCurrentSymptoms(){
        // TODO: Retrieve the saved list of the User's symptoms
        ArrayList<String> listOfCurrentSymptoms = new ArrayList<>();

        // Fake list of possible symptoms
        DataManager symptomDataManager = new DataManager();

        // Generating a fake list of user's  current Symptoms
        for (int i = 0; i < 5; i++){
            int randomIndex = new Random().nextInt(symptomDataManager.size());
//            listOfCurrentSymptoms.add(symptomDataManager.getSymptomAtIndex(randomIndex));
        }

        return listOfCurrentSymptoms;
    }

    // Create context menu for each item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = new MenuInflater(this.getContext());
        menuInflater.inflate(R.menu.alarms_symptom_context_menu, menu);

    }

    // Handle context menu action selected
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo obj = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.action_delete_symptom:
                symptomsList.remove(obj.position);
                symptomsListAdapter.notifyDataSetChanged();
                symptomsListAdapter.notifyDataSetInvalidated();
                Log.d(TAG, String.valueOf(symptomsList));
                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }
}
