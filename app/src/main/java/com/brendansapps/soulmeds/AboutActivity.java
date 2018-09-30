package com.brendansapps.soulmeds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class AboutActivity extends AppCompatActivity {
    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    private String[] mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initDataSet();

        mRecyclerView = (RecyclerView) findViewById(R.id.policy_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new AboutAdapter(mDataSet);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initDataSet() {
        mDataSet = new String[100];
        for (int i = 0; i < 100; i++) {
            mDataSet[i] = "This is element #" + i;
        }
    }
}
