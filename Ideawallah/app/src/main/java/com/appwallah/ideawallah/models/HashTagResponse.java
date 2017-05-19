package com.appwallah.ideawallah.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbommakanty on 5/18/17.
 */

public class HashTagResponse {
    List<HashTag> hashtags;

    public HashTagResponse() {
        hashtags = new ArrayList<HashTag>();
    }

    public static HashTagResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        HashTagResponse hashtagResponse = gson.fromJson(response, HashTagResponse.class);
        return hashtagResponse;
    }
}
