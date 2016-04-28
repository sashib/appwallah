package com.appwallah.ideas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appspot.ideas_staging.ideasapi.model.IdeaProtoDescriptionCreatedHashtags;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IdeasRecyclerAdapter extends RecyclerView.Adapter<IdeaViewHolder> {
    private List<IdeaProtoDescriptionCreatedHashtags> mDataset;


    // Provide a suitable constructor (depends on the kind of dataset)
    public IdeasRecyclerAdapter(List<IdeaProtoDescriptionCreatedHashtags> ideaList) {
        mDataset = ideaList;
    }

    public void addItemsToList(List<IdeaProtoDescriptionCreatedHashtags> items) {
        mDataset.clear();
        //mDataset.addAll(items);
        notifyDataSetChanged();
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
        IdeaProtoDescriptionCreatedHashtags item = mDataset.get(position);

        holder.ideaText.setText(item.getDescription());

        String itemDate = item.getCreated();

        String dt = DateManager.getDayString(itemDate,
                DateManager.DATE_FORMAT, DateManager.MONTH_DAY_FORMAT);

        holder.dateText.setText(dt);

        int resId = DateManager.isDateToday(itemDate) ? R.color.teal : R.color.light_gray;
        holder.dateText.setBackgroundResource(resId);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}