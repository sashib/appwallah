package com.appwallah.ideawallah;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.util.Log;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
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

    public static String getLinkifiedIdea(String idea) {
        String linkifiedIdea = idea;

        String pattern = "(^|\\s)(#[a-z\\d-]+)";

        Matcher m = Pattern.compile(pattern).matcher(idea);
        while (m.find()) {
            //String link = "<font color='#33b5e5'>" + m.group() + "</font>";
            String hashtag = m.group();
            String hashtagVal = hashtag.substring(2);
            String link = "<font color='#33b5e5'><a href='ideawallah://hashtagideas?hashtag=" + hashtagVal + "'>" + m.group() + "</a></font>";
            linkifiedIdea = linkifiedIdea.replaceFirst(m.group(), link);
        }
        return linkifiedIdea;

    }

    private static class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }
        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

    // http://stackoverflow.com/questions/4096851/remove-underline-from-links-in-textview-android
    public static void stripUnderlines(TextView textView) {
        Spannable s = (Spannable)textView.getText();
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span: spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            s.setSpan(span, start, end, 0);
        }
        textView.setText(s);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date ideaDate;
        String days = "Today";

        try {

            ideaDate = dateFormat.parse(dt);

            Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("UTC")); // today

            Calendar c2 = Calendar.getInstance();
            c2.setTime(ideaDate); // your date

            //Log.d(TAG, "c1 is: " + c1.toString());
            //Log.d(TAG, "c2 is: " + c2.toString());

            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                    && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                days = ctx.getString(R.string.today);
            } else {
                c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday
                if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                        && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                    days = ctx.getString(R.string.yesterday);
                } else {
                    c1.add(Calendar.DAY_OF_YEAR, +1);
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

        //Log.d(TAG, "returning days: " + days);

        return days;
    }
}
