package com.appwallah.ideas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by sbommakanty on 4/23/16.
 */
public class DateManager {

    public static final String CREATED_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String MONTH_DAY_FORMAT = "MMM d";

    public static Date getDateFromString(String dtStart, String dtFormat) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(dtFormat);
        format.setTimeZone(TimeZone.getDefault());
        try {
            date = format.parse(dtStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String getDayString(String dt, String dtFormat, String retFormat) {
        Date date = getDateFromString(dt, dtFormat);
        SimpleDateFormat sdf = new SimpleDateFormat(retFormat);
        sdf.setTimeZone(TimeZone.getDefault());
        String newDate = sdf.format(date);
        return newDate;
    }

    public static boolean isDateToday(String dt) {
        Date today = new Date();//Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());

        String todayStr = sdf.format(today);

        Date date = getDateFromString(dt, DATE_FORMAT);
        String dateStr = sdf.format(date);

        return (todayStr.equals(dateStr));
    }
}
