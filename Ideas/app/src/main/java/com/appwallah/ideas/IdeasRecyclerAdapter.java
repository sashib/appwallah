package com.appwallah.ideas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appspot.ideas_staging.ideasapi.model.IdeaProtoDescriptionDateHashtags;

import java.util.ArrayList;
import java.util.List;

public class IdeasRecyclerAdapter extends RecyclerView.Adapter<IdeaViewHolder> {
    private List<IdeaProtoDescriptionDateHashtags> mDataset;


    // Provide a suitable constructor (depends on the kind of dataset)
    public IdeasRecyclerAdapter(List<IdeaProtoDescriptionDateHashtags> ideaList) {
        mDataset = ideaList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public IdeaViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.idea_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        IdeaViewHolder vh = new IdeaViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(IdeaViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.ideaText.setText(mDataset.get(position).getDescription());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}