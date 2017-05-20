package com.appwallah.ideawallah;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sbommakanty on 2/13/17.
 */

public class Utils {

    public static final String TAG = "Utils";

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

    public static String getIdeaDay(Context ctx, String dt) {
        Log.d(TAG, "dt is: " + dt);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date ideaDate;
        String days = "Today";

        try {

            ideaDate = dateFormat.parse(dt);
            Date today = new Date();

            Calendar c1 = Calendar.getInstance(); // today

            Calendar c2 = Calendar.getInstance();
            c2.setTime(ideaDate); // your date

            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                    && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                days = ctx.getString(R.string.today);
            } else {
                c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday
                if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                        && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                    days = ctx.getString(R.string.yesterday);
                } else {
                    int diff = c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);
                    days = Integer.toString(diff) + " " + ctx.getString(R.string.days_ago);
                    if (diff > 60) {
                        days = ctx.getString(R.string.while_back);
                    }
                }

            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in date parser " + e.getMessage());

        }

        Log.d(TAG, "returning days: " + days);

        return days;
    }
}
