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

    public ArrayList<String> getHashTags(String msg) {
        ArrayList<String> hashTags = new ArrayList<>();
        int hashIndex = msg.indexOf("#");
        while (hashIndex >= 0 && hashIndex < msg.length()) {
            String str = msg.substring(hashIndex, msg.length());
            int spaceIndex = str.indexOf(" ");
            spaceIndex = (spaceIndex <= 0) ? str.length() : spaceIndex;
            String hashTag = str.substring(1, spaceIndex);
            if (hashTag.length() > 1) {
                hashTags.add(hashTag);
            }
            msg = str.substring(1);
            hashIndex = (msg.indexOf("#"));

        }
        return hashTags;
    }

    public void saveIdea(String desc) {

        ArrayList<String> hashtags = getHashTags(desc);

        Log.d(TAG, "hastags are: " + hashtags);

        Firebase ref = new Firebase(FireBaseConstants.FIREBASE_URL);
        Firebase ideaRef = ref.child("ideas");
        Firebase newIdeaRef = ideaRef.push();

        Idea newIdea = new Idea(ref.getAuth().getUid(), desc);
        newIdeaRef.setValue(newIdea);

    }

}
