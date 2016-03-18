package com.appwallah.ideas;

import com.firebase.client.ServerValue;

import java.util.Map;

/**
 * Created by sbommakanty on 2/27/16.
 */
public class Idea {

    String user_id;
    String description;
    Map<String, String> timestamp;

    public Idea() {
        init();
    }

    public Idea(String userId, String description) {
        init();
        this.user_id = userId;
        this.description = description;
    }

    public void init() {
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Map<String, String> timestamp) {
        this.timestamp = timestamp;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
