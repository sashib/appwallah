package com.appwallah.ideas;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by sbommakanty on 3/18/16.
 */
public class IdeaViewHolder extends RecyclerView.ViewHolder {
    TextView dateText;
    TextView ideaText;

    public IdeaViewHolder(View itemView) {
        super(itemView);
        dateText = (TextView)itemView.findViewById(R.id.date_text);
        ideaText = (TextView)itemView.findViewById(R.id.idea_text);
    }
}