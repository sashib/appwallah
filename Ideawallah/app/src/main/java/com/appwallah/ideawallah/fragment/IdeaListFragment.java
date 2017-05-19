package com.appwallah.ideawallah.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appwallah.ideawallah.R;
import com.appwallah.ideawallah.Utils;
import com.appwallah.ideawallah.adapters.IdeasAdapter;
import com.appwallah.ideawallah.api.IdeawallahApiService;
import com.appwallah.ideawallah.api.IdeawallahApiServiceInterface;
import com.appwallah.ideawallah.models.Idea;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sbommakanty on 2/13/17.
 */

public class IdeaListFragment extends Fragment {

    private static final String TAG = "IdeaListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private IdeasAdapter mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private TextView mDefaultText;

    private List<Idea> mIdeasList;

    private int mIdeasLimit = 25;
    private int mIdeasPage = 0;

    public IdeaListFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_ideas, container, false);

        mRecycler = (RecyclerView) rootView.findViewById(R.id.ideas_list);
        mRecycler.setHasFixedSize(true);

        mDefaultText = (TextView) rootView.findViewById(R.id.default_text);

        mIdeasList = new ArrayList<>();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        //mManager.setReverseLayout(true);
        //mManager.setStackFromEnd(true);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(getContext(),
                mManager.getOrientation());
        mRecycler.addItemDecoration(mDividerItemDecoration);
        mRecycler.setLayoutManager(mManager);

        loadIdeas();

    }

    public void loadIdeas() {

        IdeawallahApiServiceInterface apiService = IdeawallahApiService.getApiService();
        Call<List<Idea>> call = apiService.getIdeas(Utils.getToken(getContext()), Integer.toString(mIdeasLimit), Integer.toString(mIdeasPage));
        call.enqueue(new Callback<List<Idea>>() {
            @Override
            public void onResponse(Call<List<Idea>> call, Response<List<Idea>> response) {
                int statusCode = response.code();
                Log.d(TAG, "status is - " + statusCode);
                if (statusCode == 200) {
                    List<Idea> ideas = response.body();
                    int itemsCount = ideas.size();
                    if (itemsCount > 0) {
                        mDefaultText.setVisibility(View.GONE);
                        if (mIdeasPage==0) {
                            mIdeasList.clear();
                        }
                        if (itemsCount >= mIdeasLimit)
                            mIdeasPage++;

                        mIdeasList.addAll(ideas);
                        mAdapter = new IdeasAdapter(mIdeasList);
                        mAdapter.notifyDataSetChanged();
                        mRecycler.setAdapter(mAdapter);

                    }

                } else {

                    Log.e(TAG, "500 when creating user: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Idea>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, "getting ideas list failed: ");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}