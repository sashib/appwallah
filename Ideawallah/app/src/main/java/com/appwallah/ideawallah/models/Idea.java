package com.appwallah.ideawallah.models;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Idea {

    public String uid;
    public String author;
    public String body;
    public long timestamp;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();
    public boolean global;

    public Idea() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Idea(String uid, String author, boolean global, String body) {
        this.uid = uid;
        this.author = author;
        this.body = body;
        this.global = global;
        this.timestamp = System.currentTimeMillis();
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("body", body);
        result.put("global", global);
        result.put("timestamp", timestamp);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }

}