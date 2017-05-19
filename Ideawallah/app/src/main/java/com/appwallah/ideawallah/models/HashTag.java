package com.appwallah.ideawallah.models;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class HashTag {


    public String hashtag;
    public String userId;
    public int count;
    public String date;

    public HashTag() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public String getHashtag() {
        return hashtag;
    }

    public String getUserId() {
        return userId;
    }

    public int getCount() {
        return count;
    }

    public String getDate() {
        return date;
    }

}