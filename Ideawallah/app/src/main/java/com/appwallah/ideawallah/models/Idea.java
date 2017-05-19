package com.appwallah.ideawallah.models;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Idea {

    public String idea;
    public String uid;
    public String date;
    public boolean global;

    public String getUid() {
        return uid;
    }

    public String getIdea() {
        return idea;
    }

    public String getDate() {
        return date;
    }

    public boolean isGlobal() {
        return global;
    }


}