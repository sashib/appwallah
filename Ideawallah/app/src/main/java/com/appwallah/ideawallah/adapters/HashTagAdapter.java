package com.appwallah.ideawallah.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appwallah.ideawallah.Extras;
import com.appwallah.ideawallah.HashTagIdeasActivity;
import com.appwallah.ideawallah.R;
import com.appwallah.ideawallah.models.HashTag;
import com.appwallah.ideawallah.models.Idea;

import java.util.List;

/**
 * Created by sbommakanty on 5/19/17.
 */

public class HashTagAdapter extends RecyclerView.Adapter<HashTagAdapter.ViewHolder> {

    private static final String TAG = "HashTagAdapter";

    private List<HashTag> mHashTagList;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;
        public HashTag mHashTag;
        public Context mCtx;

        public ViewHolder(Context ctx, TextView v) {
            super(v);
            mCtx = ctx;
            mTextView = v;
            v.setOnClickListener(this);
        }

        public void setItem(HashTag item) {
            mHashTag = item;
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick " + getPosition() + " " + mHashTag.hashtag);
            Intent intent = new Intent(mCtx, HashTagIdeasActivity.class);
            intent.putExtra(Extras.HASHTAG_EXTRA, mHashTag.hashtag);
            mCtx.startActivity(intent);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HashTagAdapter(Context ctx, List<HashTag> hashtags) {

        mContext = ctx;
        mHashTagList = hashtags;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HashTagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tag, parent, false);

        ViewHolder vh = new ViewHolder(mContext, v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        HashTag ht = mHashTagList.get(position);
        holder.mTextView.setText(ht.hashtag);
        holder.setItem(ht);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mHashTagList.size();
    }


}

