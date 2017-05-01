package com.appwallah.ideawallah.viewholder;

/**
 * Created by sbommakanty on 2/13/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appwallah.ideawallah.R;
import com.appwallah.ideawallah.models.Idea;

import java.text.SimpleDateFormat;
import java.util.Date;


public class IdeaViewHolder extends RecyclerView.ViewHolder {

    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public IdeaViewHolder(View itemView) {
        super(itemView);

        authorView = (TextView) itemView.findViewById(R.id.idea_author);
        //starView = (ImageView) itemView.findViewById(R.id.star);
        //numStarsView = (TextView) itemView.findViewById(R.id.idea_num_stars);
        bodyView = (TextView) itemView.findViewById(R.id.idea_body);
    }

    public void bindToIdea(Idea idea, View.OnClickListener starClickListener) {

        String author = "";// = idea.author;

        if (idea.timestamp != 0) {
            Date dt = new Date(idea.timestamp);
            SimpleDateFormat format = new SimpleDateFormat("d MMM");
            //author += " - " + format.format(dt);
            author = format.format(dt);
        }

        authorView.setText(author);
        //numStarsView.setText(String.valueOf(idea.starCount));
        //numStarsView.setVisibility(View.GONE);

        bodyView.setText(idea.body);

        //starView.setOnClickListener(starClickListener);
    }
}