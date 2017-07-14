package com.appwallah.filmlocations;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appwallah.filmlocations.FilmLocationFragment.OnListFragmentInteractionListener;

import java.util.List;

public class FilmLocationRecyclerViewAdapter extends RecyclerView.Adapter<FilmLocationRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = FilmLocationRecyclerViewAdapter.class.getName();

    private final List<FilmLocation> mList;
    private final OnListFragmentInteractionListener mListener;

    public FilmLocationRecyclerViewAdapter(List<FilmLocation> list, OnListFragmentInteractionListener listener) {
        mList = list;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_filmlocation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        FilmLocation item = mList.get(position);

        /*
        String locations = "";

        for (String loc:item.getAllLocations()) {
            if (locations.isEmpty()) {
                locations = loc;
            } else {
                locations += "\n" + loc;

            }
        }
        if (locations.isEmpty()) {
            holder.mLocationView.setVisibility(View.GONE);
        } else {
            holder.mLocationView.setVisibility(View.VISIBLE);
            holder.mLocationView.setText(locations);
        }

        */
        holder.mLocationView.setText(item.getLocations());

        holder.mItem = item;
        holder.mFilmView.setText(item.getTitle());
        holder.mDateView.setText(item.getReleaseYear());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mLocationView;
        public final TextView mFilmView;
        public final TextView mDateView;
        public FilmLocation mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mLocationView = (TextView) view.findViewById(R.id.location);
            mFilmView = (TextView) view.findViewById(R.id.film);
            mDateView = (TextView) view.findViewById(R.id.date);
        }

    }
}
