package com.appwallah.ideas;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.util.Log;


import com.appspot.ideas_staging.ideasapi.Ideasapi;
import com.appspot.ideas_staging.ideasapi.model.Idea;
import com.appspot.ideas_staging.ideasapi.model.IdeaProtoDescriptionDateHashtags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewIdeaController {

    private static final String TAG = NewIdeaController.class.getName();

    Context mContext;

    public NewIdeaController(Context ctx) {
        this.mContext = ctx;
    }

    public static List<String> getHashTags(String msg) {
        List<String> hashTags = new ArrayList<>();
        int hashIndex = msg.indexOf("#");
        int msgLen = msg.length();
        while (hashIndex >= 0 && hashIndex < msgLen) {
            String str = msg.substring(hashIndex, msgLen);
            String hashTag;
            int nextIndex = str.indexOf("#", 1);
            int spaceIndex = str.indexOf(" ");
            nextIndex = (spaceIndex >= 0) && (nextIndex > spaceIndex || nextIndex < 0) ? spaceIndex : nextIndex;
            if (nextIndex < 0) {
                hashTag = str.substring(1);
                hashIndex = msgLen;
            } else {
                hashTag = str.substring(1, nextIndex);
                hashTag = hashTag.trim();
                int newHashIndex = str.indexOf("#", 1);
                if (newHashIndex > 0) {
                    hashIndex += newHashIndex;
                } else {
                    hashIndex = msgLen;
                }
            }
            if (hashTag.length() > 1) {
                hashTags.add(hashTag);
            }

        }
        return hashTags;
    }

}
