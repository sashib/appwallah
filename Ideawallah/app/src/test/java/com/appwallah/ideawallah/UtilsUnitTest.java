package com.appwallah.ideawallah;

import org.junit.Test;
import com.appwallah.ideawallah.Utils;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by sbommakanty on 2/13/17.
 */

public class UtilsUnitTest {
    @Test
    public void getHasTags_isCorrect() throws Exception {
        String text = "#some test for #ideas and #tags";
        ArrayList<String> tagArr = Utils.getHashTags(text);

        assertEquals(tagArr.size(), 3);
        assertEquals(tagArr.get(0).trim(), "#some");
        assertEquals(tagArr.get(1).trim(), "#ideas");
        assertEquals(tagArr.get(2).trim(), "#tags");
    }

    @Test
    public void getHasTags_isInCorrect() throws Exception {
        String text = "#some test for #ideas and #tags";
        ArrayList<String> tagArr = Utils.getHashTags(text);

        assertNotEquals(tagArr.size(), 4);
        assertNotEquals(tagArr.get(0), "test");
        assertNotEquals(tagArr.get(1), "for");
        assertNotEquals(tagArr.get(2), "and");
    }

}
