package com.appwallah.ideawallah.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by sbommakanty on 4/15/17.
 */

public class MyTagsFragment extends TagListFragment {

    public MyTagsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-tags")
                .child(getUid());
    }
}