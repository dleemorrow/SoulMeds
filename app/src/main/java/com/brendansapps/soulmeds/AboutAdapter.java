package com.brendansapps.soulmeds;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.AboutViewHolder> {
    private String[] policyDataSet;

    public static class AboutViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextView;
        AboutViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.textView);
        }
        public TextView getTextView() {
            return mTextView;
        }
    }

    AboutAdapter(String[] dataSet) {
        policyDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public AboutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.policy_section, parent, false);

        return new AboutViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull AboutViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.getTextView().setText(Html.fromHtml(policyDataSet[position]));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return policyDataSet.length;
    }
}

