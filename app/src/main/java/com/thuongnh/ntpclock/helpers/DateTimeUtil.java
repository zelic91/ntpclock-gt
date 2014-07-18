package com.thuongnh.ntpclock.helpers;

import android.text.format.DateFormat;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zelic on 7/18/14.
 * This class is helper for getting date formatted string and date components
 */
public class DateTimeUtil {

    /**
     * Get display time with format HH:MM:SS. E.g: 12:00:00
     * @param timestamp
     * @return
     */
    public static String getDisplayTimeFromTimestamp(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(timestamp);
        return formatter.format(date);
    }

    /**
     * Get display time from milliseconds with format HH:MM:SS.
     * This method is different from the above, used to format number of milliseconds to string.
     * @param miliseconds
     * @return
     */
    public static String getDisplayTimeFromMiliseconds(long miliseconds) {
        long numSecs = miliseconds / 1000;
        long hours = numSecs / 3600;
        long mins = (numSecs % 3600)/60;
        long secs = numSecs - (hours * 3600 + mins * 60);
        return String.format("%02d:%02d:%02d", hours, mins, secs);
    }

    /**
     * Get display date with format DD - MMM - YYYY. E.g: 18 - Jul - 2014
     * @param timestamp
     * @return
     */
    public static String getDisplayDateFromTimestamp(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd - MMM - yyyy");
        Date date = new Date(timestamp);
        return formatter.format(date);
    }

    /**
     * Decrease time by 1 secs
     * @param timestamp
     * @return
     */
    public static long decreaseTimeByOneSecond(long timestamp) {
        Date date = new Date(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.SECOND, cal.get(Calendar.SECOND)-1);
        return cal.getTimeInMillis();
    }

    /**
     * Increase time by 1 secs
     * @param timestamp
     * @return
     */
    public static long increaseTimeByOneSecond(long timestamp) {
        Date date = new Date(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.SECOND, cal.get(Calendar.SECOND)+1);
        return cal.getTimeInMillis();
    }

    /**
     * Get seconds from timestamp
     * @param timestamp
     * @return
     */
    public static int getSecondFromTimestamp(long timestamp) {
        Date date = new Date(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.SECOND);
    }

    /**
     * Get minute from timestamp
     * @param timestamp
     * @return
     */
    public static int getMinuteFromTimestamp(long timestamp) {
        Date date = new Date(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    /**
     * Get hour from timestamp
     * @param timestamp
     * @return
     */
    public static int getHourFromTimestamp(long timestamp) {
        Date date = new Date(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR);
    }
}
