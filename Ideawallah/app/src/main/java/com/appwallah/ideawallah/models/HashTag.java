package com.appwallah.ideawallah.models;


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