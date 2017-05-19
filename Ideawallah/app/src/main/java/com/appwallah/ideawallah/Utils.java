package com.appwallah.ideawallah;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sbommakanty on 2/13/17.
 */

public class Utils {

    public static final String PREFS_NAME = "IdeawallahPrefs";
    public static final String TOKEN_PREF = "authToken";

    public static ArrayList<String> getHashTags(String idea) {
        String pattern = "(^|\\s)(#[a-z\\d-]+)";

        ArrayList<String> tags = new ArrayList<String>();
        Matcher m = Pattern.compile(pattern).matcher(idea);
        while (m.find()) {
            tags.add(m.group());
        }
        return tags;

    }

    public static void saveToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TOKEN_PREF, token);
        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPref.getString(TOKEN_PREF, null);
        return token;
    }
}
