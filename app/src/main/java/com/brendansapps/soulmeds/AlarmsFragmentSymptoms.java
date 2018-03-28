package com.brendansapps.soulmeds;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/** =================================================
 * Created by bt on 3/14/18.
 *
 * Tab Of Alarms Activity for the Symptoms
 * Manages the user's Prescription Symptoms
 * ===================================================== */

public class AlarmsFragmentSymptoms extends Fragment {

    /** =================================================
     * Member Variables
     * ===================================================== */
    private static final String TAG = "AlarmSymptomsFragment";
    private static final int MAX_NUM_PRESCRIPTIONS = 20;
    private FloatingActionButton addSymptomButton;

    // Members for the Alarm Symptom List View
    private PrescriptionManager prescriptionManager;
    private ListView symptomListView;
    private AlarmsListAdapter symptomsListAdapter;
    private ArrayList<String> allSymptomsList; // List of all possible symptoms
    private ArrayList<String> symptomsList; // List of the user's current symptoms

    /** =================================================
     * Constructors
     * ===================================================== */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment1_alarms, container, false);

        // Setup the Symptom List View
        prescriptionManager = new PrescriptionManager(this.getContext());
        allSymptomsList = prescriptionManager.getAllSymptoms();
        symptomsList = prescriptionManager.getUserSymptoms();
        initSymptomListView(view);

        // Setup the Add Symptom Button
        addSymptomButton = view.findViewById(R.id.action_add_symptom);
        addSymptomButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createSymptomPickerDialog(-1, 0, 0); // Type 0 = Add new
            }
        });

        return view;
    }

    // Populate the ListView by getting the Data & setting the Adapter
    private void initSymptomListView(View view){
        symptomListView = view.findViewById(R.id.alarms_list_symptoms);
        Log.d(TAG, "User's Symptoms: " + String.valueOf(symptomsList));
//        if (Objects.equals(symptomsList.get(0), "")){ symptomsList.clear(); }
        symptomsListAdapter = new AlarmsListAdapter(this.getContext(), R.layout.alarms_list_item, R.id.alarms_list_item_TextView, symptomsList);
        symptomListView.setAdapter(symptomsListAdapter);
        registerForContextMenu(symptomListView);
    }

    // Re-draw the ListView
    private void resetSymptomList(){
        symptomsListAdapter.notifyDataSetChanged();
        symptomsListAdapter.notifyDataSetInvalidated();
        Log.d(TAG, String.valueOf(symptomsList));
    }

    /** =================================================
     * Functions for Manipulating the ListView
     * ===================================================== */
    // Adds the newSymptom to the ListView
    private void addSymptom(String newSymptom){
        symptomsList.add(newSymptom);
        prescriptionManager.addSymptom(newSymptom);
        resetSymptomList();
    }

    // Deletes the symptom at the index
    private void deleteSymptom(int index){
        symptomsList.remove(index);
        prescriptionManager.deleteSymptom(index);
        resetSymptomList();
    }

    // Edits the symptom at index to be newSymptom
    private void editSymptom(int index, String newSymptom){
        symptomsList.set(index, newSymptom);
        prescriptionManager.editSymptom(index, newSymptom);
        resetSymptomList();
    }

    // Creates Dialog to pick new symptom for adding or editing
    private void createSymptomPickerDialog(final int currentSelected, final int type, final int indexInList){
        // Prepare the list of Symptoms to select
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
                deleteSymptom(indexSelected);
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
