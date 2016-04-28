package com.appwallah.ideas;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateManagerTest {

    @Test
    public void getDayString_Test() {
        String createdDate = "2016-04-22T22:59:11.831859";
        String date = "2016-04-22";

        String actualCreated = DateManager.getDayString(
                createdDate, DateManager.CREATED_FORMAT, DateManager.MONTH_DAY_FORMAT);
        String actualDate = DateManager.getDayString(
                date, DateManager.DATE_FORMAT, DateManager.MONTH_DAY_FORMAT);
        String expected = "Apr 22";

        assertEquals(actualCreated, expected);
        assertEquals(actualDate, expected);
    }

    @Test
    public void isDateToday_Test() {
        String correctDate = "2016-04-23";
        String incorrectDate = "2018-01-22";

        assertTrue(DateManager.isDateToday(correctDate));
        assertFalse(DateManager.isDateToday(incorrectDate));
    }
}