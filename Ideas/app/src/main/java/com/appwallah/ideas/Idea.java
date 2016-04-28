package com.appwallah.ideas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firebase.client.ServerValue;

import java.util.Date;
import java.util.Map;

/**
 * Created by sbommakanty on 2/27/16.
 */
public class Idea {

    @JsonProperty("user_id")
    String userId;
    String desc;
    long timestamp;
    Map<String, Boolean> tags;

    public Idea() {
        init();
    }

    public Idea(String userId, String description, Map<String, Boolean> hashTags) {
        init();
        this.userId = userId;
        this.desc = description;
        this.tags = hashTags;
    }

    public void init() {
        Date dt = new Date();
        this.timestamp = dt.getTime();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String description) {
        this.desc = description;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String uid) {
        this.userId = uid;
    }

    public Map<String, Boolean> getTags() {
        return tags;
    }

    public void setTags(Map<String, Boolean> tags) {
        this.tags = tags;
    }



}
