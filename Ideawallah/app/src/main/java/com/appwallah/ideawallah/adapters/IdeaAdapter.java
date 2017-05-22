package com.appwallah.ideawallah.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appwallah.ideawallah.R;
import com.appwallah.ideawallah.Utils;
import com.appwallah.ideawallah.models.Idea;

import java.util.List;

/**
 * Created by sbommakanty on 5/19/17.
 */

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.ViewHolder> {

    private static final String TAG = "IdeaAdapter";

    private List<Idea> mIdeasList;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView ideaText;
        public TextView dateText;
        public ViewHolder(LinearLayout v) {
            super(v);
            ideaText = (TextView) v.findViewById(R.id.idea_text);
            ideaText.setMovementMethod(LinkMovementMethod.getInstance());


            dateText = (TextView) v.findViewById(R.id.date_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public IdeaAdapter(Context ctx, List<Idea> ideas) {
        mContext = ctx;
        mIdeasList = ideas;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public IdeaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_idea, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Idea idea = mIdeasList.get(position);
        String days = Utils.getIdeaDay(mContext, idea.date);

        String htmlText = Utils.getLinkifiedIdea(idea.idea);

        holder.ideaText.setText(Html.fromHtml(htmlText));
        Utils.stripUnderlines(holder.ideaText);
        holder.ideaText.setLinkTextColor(Color.rgb(51,181,229));
        holder.dateText.setText(days);

    }

    @Override
    public int getItemCount() {
        return mIdeasList.size();
    }
}



