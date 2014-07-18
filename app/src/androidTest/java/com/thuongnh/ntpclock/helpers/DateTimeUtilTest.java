package com.thuongnh.ntpclock.helpers;

import junit.framework.TestCase;

public class DateTimeUtilTest extends TestCase {

    public void testGetDisplayTimeFromTimestamp() throws Exception {
        long timestamp = 1405670381000L;
        String expectedResult = "14:59:41";
        assertEquals("Must be equal", expectedResult, DateTimeUtil.getDisplayTimeFromTimestamp(timestamp));
    }

    public void testGetDisplayTimeFromMiliseconds() throws Exception {
        long milliseconds = 31*60*1000;
        String expectedResult = "00:31:00";
        assertEquals("Must be equal", expectedResult, DateTimeUtil.getDisplayTimeFromMiliseconds(milliseconds));
    }

    public void testGetDisplayDateFromTimestamp() throws Exception {
        long timestamp = 1405670381000L;
        String expectedResult = "18 - Jul - 2014";
        assertEquals("Must be equal", expectedResult, DateTimeUtil.getDisplayDateFromTimestamp(timestamp));
    }

    public void testDecreaseTimeByOneSecond() throws Exception {
        long timestamp = 1405670381000L;
        long expectedResult = 1405670380000L;
        assertEquals("Must be equal", expectedResult, DateTimeUtil.decreaseTimeByOneSecond(timestamp));
    }

    public void testIncreaseTimeByOneSecond() throws Exception {
        long timestamp = 1405670381000L;
        long expectedResult = 1405670382000L;
        assertEquals("Must be equal", expectedResult, DateTimeUtil.increaseTimeByOneSecond(timestamp));
    }

    public void testGetSecondFromTimestamp() throws Exception {
        long timestamp = 1405670381000L;
        int expectedResult = 41;
        assertEquals("Must be equal", expectedResult, DateTimeUtil.getSecondFromTimestamp(timestamp));
    }

    public void testGetMinuteFromTimestamp() throws Exception {
        long timestamp = 1405670381000L;
        int expectedResult = 59;
        assertEquals("Must be equal", expectedResult, DateTimeUtil.getMinuteFromTimestamp(timestamp));
    }

    public void testGetHourFromTimestamp() throws Exception {
        long timestamp = 1405670381000L;
        int expectedResult = 2;
        assertEquals("Must be equal", expectedResult, DateTimeUtil.getHourFromTimestamp(timestamp));
    }
}