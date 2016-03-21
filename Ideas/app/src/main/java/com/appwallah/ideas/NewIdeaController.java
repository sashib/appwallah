package com.appwallah.ideas;

import android.content.Context;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewIdeaController {

    private static final String TAG = NewIdeaController.class.getName();

    Context mContext;

    public NewIdeaController(Context ctx) {
        this.mContext = ctx;
    }


    public void saveIdea(String desc) {

        ArrayList<String> hashtags = HashtagManager.getHashTags(desc);

        Log.d(TAG, "hastags are: " + hashtags);

        Firebase ref = new Firebase(FireBaseConstants.FIREBASE_URL);
        Firebase ideaRef = ref.child("ideas");
        Firebase newIdeaRef = ideaRef.push();

        Idea newIdea = new Idea(ref.getAuth().getUid(), desc);
        newIdeaRef.setValue(newIdea);

    }

}
