package com.brendansapps.soulmeds;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by bt on 3/11/18.
 */

public class PrescriptionsListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, PrescriptionObject> listHashMap;

    public PrescriptionsListAdapter (Context context, List<String> listDataHeader, HashMap<String, PrescriptionObject> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    // Set the Header for the current list item
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String)getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.prescriptions_list_group, null);
        }

        TextView prescriptionListHeader = convertView.findViewById(R.id.prescriptionListHeader);
        prescriptionListHeader.setTypeface(null, Typeface.BOLD);
        prescriptionListHeader.setText(headerTitle);
        return convertView;
    }

    // Set the Prescription Info for the current list item
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // Get Prescription Information for the current child
        PrescriptionObject currentPrescription = (PrescriptionObject) getChild(groupPosition, childPosition);
        final String symptomText = currentPrescription.getSymptom();
        final String timeText = currentPrescription.getAlarmTime();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.prescription_item, null);
        }

        // Set the Prescription Information for the current child
        TextView prescriptionListSymptomText = convertView.findViewById(R.id.prescriptionSymptom);
        prescriptionListSymptomText.setText(symptomText);
        TextView prescriptionListAlarmTime = convertView.findViewById(R.id.prescriptionAlarmTime);
        prescriptionListAlarmTime.setText(timeText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
