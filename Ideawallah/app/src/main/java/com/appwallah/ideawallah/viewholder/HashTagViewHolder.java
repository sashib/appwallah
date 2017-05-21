package com.appwallah.ideawallah.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.appwallah.ideawallah.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sbommakanty on 4/15/17.
 */

public class HashTagViewHolder extends RecyclerView.ViewHolder {

    public TextView tagView;

    public HashTagViewHolder(View itemView) {
        super(itemView);

        tagView = (TextView) itemView.findViewById(R.id.tag_text);
    }

    public void bindToTag(HashMap tag, View.OnClickListener starClickListener) {
        tagView.setText("");

    }
}