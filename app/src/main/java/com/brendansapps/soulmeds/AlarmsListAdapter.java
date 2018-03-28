package com.brendansapps.soulmeds;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bt on 3/14/18.
 *
 * The adaptor for the Alarm's Activity which handles setting the data for both of the ListView tabs
 */

public class AlarmsListAdapter extends ArrayAdapter {

    private static final String TAG = "AlarmsListAdapter";
    private Context mContext;
    private ArrayList<String> mListData;
    private int mResource;
    private int mTextViewResourceId;

    public AlarmsListAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List objects) {
        super(context, resource, textViewResourceId, objects);
        this.mContext = context;
        this.mListData = (ArrayList<String>) objects;
        this.mResource = resource;
        this.mTextViewResourceId = textViewResourceId;
//        Log.d(TAG, String.valueOf(mListData));
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get the data
        String currentItemText = mListData.get(position);
        // Log.d(TAG, currentItemText);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, null);
        }

        // Set the Text
        TextView alarmItemTextView = convertView.findViewById(mTextViewResourceId);
        alarmItemTextView.setTypeface(null, Typeface.BOLD);
        alarmItemTextView.setText(currentItemText);

        return convertView;
    }

    // Change the data list source
//    public void changeList(ArrayList<String> newList){
//        mListData = newList;
//    }


}
