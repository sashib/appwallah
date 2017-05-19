package com.appwallah.ideawallah.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbommakanty on 5/17/17.
 */

public class IdeaResponse {

    List<Idea> ideas;

    public IdeaResponse() {
        ideas = new ArrayList<Idea>();
    }

    public static IdeaResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        IdeaResponse ideaResponse = gson.fromJson(response, IdeaResponse.class);
        return ideaResponse;
    }

}
