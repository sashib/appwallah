package com.appwallah.ideas;

import com.firebase.client.ServerValue;

import java.util.Date;
import java.util.Map;

/**
 * Created by sbommakanty on 2/27/16.
 */
public class Idea {

    String user_id;
    String desc;
    long timestamp;

    public Idea() {
        init();
    }

    public Idea(String userId, String description) {
        init();
        this.user_id = userId;
        this.desc = description;
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


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
