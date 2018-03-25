package com.brendansapps.soulmeds;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/** =================================================
 * Created by bt on 3/14/18.
 *
 * Tab Of Alarms Activity for the Symptoms
 * ===================================================== */

public class AlarmsFragmentSymptoms extends Fragment {

    /** =================================================
     * Member Variables
     * ===================================================== */
    private static final String TAG = "alarms_fragment1";
    private FloatingActionButton addSymptomButton;

    // Members for the Alarm Symptom List View
    private ListView symptomListView;
    private AlarmsListAdapter symptomsListAdapter;
    private ArrayList<String> allSymptomsList; // List of all possible symptoms
    private List<String> symptomsList; // List of the user's current symptoms

    /** =================================================
     * Constructors
     * ===================================================== */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment1_alarms, container, false);

        // Setup the Symptom List View
        DataManager symptomDataManager = new DataManager();
        allSymptomsList = symptomDataManager.getSymptomsList();
        initSymptomListView(view);

        // Setup the Add Symptom Button
        addSymptomButton = view.findViewById(R.id.action_add_symptom);
        addSymptomButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createSymptomPickerDialog(0, 0, 0); // Type 0 = Add new
            }
        });

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
        // Generating a fake list of user's  current Symptoms
        for (int i = 0; i < 5; i++){
            int randomIndex = new Random().nextInt(allSymptomsList.size());
            listOfCurrentSymptoms.add(allSymptomsList.get(randomIndex));
        }
        return listOfCurrentSymptoms;
    }

    /** =================================================
     * Functions for Manipulating the ListView
     * ===================================================== */
    // Adds the newSymptom to the ListView
    private void addSymptom(String newSymptom){
        symptomsList.add(newSymptom);
        symptomsListAdapter.notifyDataSetChanged();
        symptomsListAdapter.notifyDataSetInvalidated();
    }

    // Adds the newSymptom to the ListView
    private void editSymptom(int index, String newSymptom){
        symptomsList.set(index, newSymptom);
        symptomsListAdapter.notifyDataSetChanged();
        symptomsListAdapter.notifyDataSetInvalidated();
    }

    // Starts Dialog to add a new symptom
    private void createSymptomPickerDialog(final int currentSelected, final int type, final int indexInList){
        // Prepare the list of Symptoms Adapter
        final ArrayAdapter<String> symptomSelectAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.select_dialog_singlechoice);
        for (int i = 0; i < allSymptomsList.size(); i++){
            symptomSelectAdapter.add(allSymptomsList.get(i));
        }

        // Setup the Actual Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.title_symptom_picker);
        builder.setSingleChoiceItems(symptomSelectAdapter, currentSelected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (type){
                    case 0:
                        addSymptom(allSymptomsList.get(which));
                        break;
                    case 1:
                        editSymptom(indexInList, allSymptomsList.get(which));
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /** =================================================
     * Functions for each Item's ContextMenu
     * ===================================================== */
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
        final int indexSelected = obj.position;

        switch (item.getItemId()){
            case R.id.action_delete_symptom:
                symptomsList.remove(indexSelected);
                symptomsListAdapter.notifyDataSetChanged();
                symptomsListAdapter.notifyDataSetInvalidated();
                break;
            case R.id.action_edit_symptom:
                String oldSymptom = symptomsListAdapter.getItem(indexSelected).toString();
                int indexOfOldSymptom = allSymptomsList.indexOf(oldSymptom);
                createSymptomPickerDialog(indexOfOldSymptom, 1, indexSelected); // Type 1 = edit
                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }
}
