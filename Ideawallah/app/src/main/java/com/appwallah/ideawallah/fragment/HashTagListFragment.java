package com.appwallah.ideawallah.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appwallah.ideawallah.R;
import com.appwallah.ideawallah.Utils;
import com.appwallah.ideawallah.adapters.HashTagAdapter;
import com.appwallah.ideawallah.adapters.IdeaAdapter;
import com.appwallah.ideawallah.api.IdeawallahApiService;
import com.appwallah.ideawallah.api.IdeawallahApiServiceInterface;
import com.appwallah.ideawallah.models.HashTag;
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
 * Created by sbommakanty on 5/19/17.
 */

public class HashTagListFragment extends Fragment {

    private static final String TAG = "HashTagListFragment";

    private HashTagAdapter mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private TextView mDefaultText;
    private AVLoadingIndicatorView mAvi;

    private List<HashTag> mHashTagsList;

    private int mLimit = 12;
    private int mPage = 0;

    private boolean mLoading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;


    public HashTagListFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_tags, container, false);

        mRecycler = (RecyclerView) rootView.findViewById(R.id.tags_list);
        mRecycler.setHasFixedSize(true);


        mAvi = (AVLoadingIndicatorView) rootView.findViewById(R.id.avi);

        mHashTagsList = new ArrayList<>();

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
                if(dy > 0) {
                    visibleItemCount = mManager.getChildCount();
                    totalItemCount = mManager.getItemCount();
                    pastVisiblesItems = mManager.findFirstVisibleItemPosition();

                    if (mLoading){
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount){
                            mLoading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            loadHashTags();

                        }
                    }
                }
            }
        });

        mAvi.setVisibility(View.VISIBLE);

        loadHashTags();

    }

    public void loadHashTags() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.getToken(false).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();

                    IdeawallahApiServiceInterface apiService = IdeawallahApiService.getApiService();
                    Call<List<HashTag>> call = apiService.getHashTags(token, Integer.toString(mLimit+1), Integer.toString(mPage));
                    call.enqueue(new Callback<List<HashTag>>() {
                        @Override
                        public void onResponse(Call<List<HashTag>> call, Response<List<HashTag>> response) {
                            int statusCode = response.code();
                            Log.d(TAG, "status is - " + statusCode);
                            if (statusCode == 200) {

                                List<HashTag> hashtags = response.body();
                                int itemsCount = hashtags.size();
                                if (itemsCount > 0) {
                                    if (mPage==0) {
                                        mHashTagsList.clear();
                                    }
                                    if (itemsCount > mLimit) {
                                        mPage++;
                                        hashtags.remove(itemsCount-1);
                                    }

                                    if (mHashTagsList.size() == 0) {
                                        mHashTagsList.addAll(hashtags);
                                        mAdapter = new HashTagAdapter(getContext(), mHashTagsList);
                                        mRecycler.setAdapter(mAdapter);

                                    } else {
                                        mHashTagsList.addAll(hashtags);
                                        mAdapter.notifyDataSetChanged();

                                    }

                                    if (itemsCount > mLimit)
                                        mPage++;

                                }

                            } else {

                                Log.e(TAG, "500 when creating user: " + response.body());
                            }
                            mAvi.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<List<HashTag>> call, Throwable t) {
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


}
