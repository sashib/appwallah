package com.appwallah.ideawallah.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appwallah.ideawallah.R;
import com.appwallah.ideawallah.models.Idea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbommakanty on 5/19/17.
 */

public class IdeasAdapter extends RecyclerView.Adapter<IdeasAdapter.ViewHolder> {
    private List<Idea> mIdeasList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public IdeasAdapter(List<Idea> ideas) {
        mIdeasList = ideas;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public IdeasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.include_idea_text, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mIdeasList.get(position).idea);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mIdeasList.size();
    }
}



