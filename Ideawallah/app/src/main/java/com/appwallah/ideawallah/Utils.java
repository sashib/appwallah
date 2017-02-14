package com.appwallah.ideawallah;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sbommakanty on 2/13/17.
 */

public class Utils {

    public static ArrayList<String> getHashTags(String idea) {
        String pattern = "(^|\\s)(#[a-z\\d-]+)";

        ArrayList<String> tags = new ArrayList<String>();
        Matcher m = Pattern.compile(pattern).matcher(idea);
        while (m.find()) {
            tags.add(m.group());
        }
        return tags;

    }
}
