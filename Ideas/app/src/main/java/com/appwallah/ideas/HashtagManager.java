package com.appwallah.ideas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sashi on 19/03/16.
 */
public class HashtagManager {


    public static ArrayList<String> getHashTags(String msg) {
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

    public static Map<String,Boolean> getHashTagsMap(String msg) {
        Map<String, Boolean> hashTags = new HashMap<>();
        int hashIndex = msg.indexOf("#");
        while (hashIndex >= 0 && hashIndex < msg.length()) {
            String str = msg.substring(hashIndex, msg.length());
            int spaceIndex = str.indexOf(" ");
            spaceIndex = (spaceIndex <= 0) ? str.length() : spaceIndex;
            String hashTag = str.substring(1, spaceIndex);
            if (hashTag.length() > 1) {
                hashTags.put(hashTag, true);;
            }
            msg = str.substring(1);
            hashIndex = (msg.indexOf("#"));

        }
        return hashTags;
    }
}
