package com.appwallah.ideawallah.models;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class IdeaTag {

    public String name;
    public Object ideas;

    public IdeaTag() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public IdeaTag(String name, Object ideas) {
        this.name = name;
        this.ideas = ideas;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);

        return result;
    }

}