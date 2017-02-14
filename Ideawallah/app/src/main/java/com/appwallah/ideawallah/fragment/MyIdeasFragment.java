package com.appwallah.ideawallah.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by sbommakanty on 2/13/17.
 */

public class MyIdeasFragment extends IdeaListFragment {

    public MyIdeasFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-ideas")
                .child(getUid());
    }
}