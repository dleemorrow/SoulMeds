package com.brendansapps.soulmeds;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by bt on 2/12/18.
 */

public class alarms_fragment1 extends Fragment {
    private static final String TAG = "alarms_fragment1";

    private Button mAlarmButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment1_alarms, container, false);
        mAlarmButton = view.findViewById(R.id.setAlarmBtn1);

        return view;
    }


}
