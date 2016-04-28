package com.appwallah.ideas;

import java.util.Date;

/**
 * Created by sashi on 19/03/16.
 */
public class HashTag {

    String name;
    String ideaId;
    String userId;
    long timestamp;

    public HashTag() {
        init();
    }

    public HashTag(String uid, String name) {
        init();
        this.userId = uid;
        this.name = name;
    }

    public void init() {
        Date dt = new Date();
        this.timestamp = dt.getTime();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(String ideaId) {
        this.ideaId = ideaId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String user_id) {
        this.userId = user_id;
    }

}
