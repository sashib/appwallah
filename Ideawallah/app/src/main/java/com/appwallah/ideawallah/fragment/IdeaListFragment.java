package com.appwallah.ideawallah.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appwallah.ideawallah.MainActivity;
import com.appwallah.ideawallah.R;
import com.appwallah.ideawallah.SignInActivity;
import com.appwallah.ideawallah.Utils;
import com.appwallah.ideawallah.adapters.IdeaAdapter;
import com.appwallah.ideawallah.api.IdeawallahApiService;
import com.appwallah.ideawallah.api.IdeawallahApiServiceInterface;
import com.appwallah.ideawallah.models.Idea;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sbommakanty on 2/13/17.
 */

public abstract class IdeaListFragment extends Fragment {

    private static final String TAG = "IdeaListFragment";

    private IdeaAdapter mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private TextView mDefaultText;

    private AVLoadingIndicatorView mAvi;

    private List<Idea> mIdeasList;

    public int mIdeasLimit = 10;
    public int mIdeasPage = 0;

    public String mToken;

    private boolean mLoading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;



    public IdeaListFragment() {}


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_ideas, container, false);

        mRecycler = (RecyclerView) rootView.findViewById(R.id.ideas_list);
        mRecycler.setHasFixedSize(true);

        mDefaultText = (TextView) rootView.findViewById(R.id.default_text);

        mAvi = (AVLoadingIndicatorView) rootView.findViewById(R.id.avi);

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

        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //Log.v(TAG, "onscroll: " + dy);
                if(dy > 0) {
                    visibleItemCount = mManager.getChildCount();
                    totalItemCount = mManager.getItemCount();
                    pastVisiblesItems = mManager.findFirstVisibleItemPosition();

                    if (mLoading){
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount){
                            mLoading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            loadIdeas();

                        } else {
                            mLoading = true;
                        }
                    }
                }
            }
        });

        loadIdeasFromStart();

    }

    public void loadIdeasFromStart() {
        mAvi.setVisibility(View.VISIBLE);
        mIdeasPage = 0;
        mLoading = true;
        loadIdeas();
    }

    public void loadIdeas() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.getToken(false).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    mToken = task.getResult().getToken();

                    Log.d(TAG, "page is: " + mIdeasPage);

                    Call<List<Idea>> call = getIdeas();
                    call.enqueue(new Callback<List<Idea>>() {
                        @Override
                        public void onResponse(Call<List<Idea>> call, Response<List<Idea>> response) {
                            int statusCode = response.code();
                            Log.d(TAG, "status is - " + statusCode);
                            if (statusCode == 200) {
                                List<Idea> ideas = response.body();
                                int itemsCount = ideas.size();
                                if (itemsCount > 0) {
                                    //mDefaultText.setVisibility(View.GONE);
                                    if (mIdeasPage==0) {
                                        mIdeasList.clear();
                                    }
                                    if (itemsCount > mIdeasLimit) {
                                        mIdeasPage++;
                                        ideas.remove(itemsCount-1);
                                    }

                                    if (mIdeasList.size() == 0) {
                                        mIdeasList.addAll(ideas);
                                        mAdapter = new IdeaAdapter(getContext(), mIdeasList);
                                        mRecycler.setAdapter(mAdapter);

                                    } else {
                                        mIdeasList.addAll(ideas);
                                        mAdapter.notifyDataSetChanged();

                                    }


                                }

                            } else {

                                Log.e(TAG, "500 when getting ideas: " + response.body());
                            }
                            mAvi.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<List<Idea>> call, Throwable t) {
                            // Log error here since request failed
                            Log.e(TAG, "getting ideas list failed: ");
                            mAvi.setVisibility(View.GONE);

                        }
                    });

                }
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public abstract Call<List<Idea>> getIdeas();
}