package com.appwallah.ideas;


import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NewIdeaControllerTest {

    @Test
    public void getHashTags_single_hashtag() {
        List<String> actualBeg = NewIdeaController.getHashTags("#hashtag some text");
        List<String> actualSpaceBeg = NewIdeaController.getHashTags(" #hashtag some text");
        List<String> actualMiddle = NewIdeaController.getHashTags("some #hashtag text");
        List<String> actualMiddleNoSpaceBeg = NewIdeaController.getHashTags("some#hashtag text");
        List<String> actualEnd = NewIdeaController.getHashTags("some text #hashtag ");
        List<String> actualEndNoSpaceBegSpaceEnd = NewIdeaController.getHashTags("some text#hashtag ");
        List<String> actualEndNoSpaceBegNoSpaceEnd = NewIdeaController.getHashTags("some text#hashtag");
        List<String> actualEndSpaceEnd = NewIdeaController.getHashTags("some text #hashtag ");
        List<String> actualEndNoSpaceEnd = NewIdeaController.getHashTags("some text #hashtag");
        List<String> actualOnlyHashTag = NewIdeaController.getHashTags("#hashtag");
        List<String> actualOnlyHashTagSpaceBeg = NewIdeaController.getHashTags(" #hashtag");
        List<String> actualOnlyHashTagSpaceEnd = NewIdeaController.getHashTags("#hashtag ");
        List<String> actualOnlyHashTagSpaceBoth = NewIdeaController.getHashTags(" #hashtag ");



        List<String> expected = new ArrayList<String>();
        expected.add(0, "hashtag");

        assertEquals(expected, actualBeg);
        assertEquals(expected, actualSpaceBeg);
        assertEquals(expected, actualMiddle);
        assertEquals(expected, actualMiddleNoSpaceBeg);
        assertEquals(expected, actualEnd);
        assertEquals(expected, actualEndNoSpaceBegSpaceEnd);
        assertEquals(expected, actualEndNoSpaceBegNoSpaceEnd);
        assertEquals(expected, actualEndSpaceEnd);
        assertEquals(expected, actualEndNoSpaceEnd);
        assertEquals(expected, actualOnlyHashTag);
        assertEquals(expected, actualOnlyHashTagSpaceBeg);
        assertEquals(expected, actualOnlyHashTagSpaceEnd);
        assertEquals(expected, actualOnlyHashTagSpaceBoth);
    }


    @Test
    public void getHashTags_multiple_hashtag() {

        List<String> actualBeg = NewIdeaController.getHashTags("#hashtag #anotherhashtag some text");
        List<String> actualSpaceBeg = NewIdeaController.getHashTags(" #hashtag #anotherhashtag  some text");
        List<String> actualMiddle = NewIdeaController.getHashTags("some #hashtag #anotherhashtag  text");
        List<String> actualMiddleNoSpaceBeg = NewIdeaController.getHashTags("some#hashtag #anotherhashtag text");
        List<String> actualEnd = NewIdeaController.getHashTags("some text #hashtag #anotherhashtag ");
        List<String> actualEndNoSpaceBegSpaceEnd = NewIdeaController.getHashTags("some text#hashtag #anotherhashtag ");
        List<String> actualEndNoSpaceBegNoSpaceEnd = NewIdeaController.getHashTags("some text#hashtag #anotherhashtag");
        List<String> actualEndSpaceEnd = NewIdeaController.getHashTags("some text #hashtag #anotherhashtag ");
        List<String> actualEndNoSpaceEnd = NewIdeaController.getHashTags("some text #hashtag #anotherhashtag");
        List<String> actualTwoStraight = NewIdeaController.getHashTags("#hashtag #anotherhashtag");
        List<String> actualTwoStraightNoSpace = NewIdeaController.getHashTags("#hashtag#anotherhashtag");
        List<String> actualTwoStraightSpaceBeg = NewIdeaController.getHashTags(" #hashtag#anotherhashtag");
        List<String> actualTwoStraightSpaceEnd = NewIdeaController.getHashTags("#hashtag#anotherhashtag ");
        List<String> actualTwoStraightSpaceBeg2 = NewIdeaController.getHashTags(" #hashtag #anotherhashtag");
        List<String> actualTwoStraightSpaceEnd2 = NewIdeaController.getHashTags("#hashtag #anotherhashtag ");
        List<String> actualVariation = NewIdeaController.getHashTags("#hashtag this one #anotherhashtag#onemore #ideas and some more #great");

        List<String> expected = new ArrayList<String>();
        expected.add(0, "hashtag");
        expected.add(1, "anotherhashtag");

        List<String> expectedVariation = new ArrayList<String>();
        expectedVariation.add(0, "hashtag");
        expectedVariation.add(1, "anotherhashtag");
        expectedVariation.add(2, "onemore");
        expectedVariation.add(3, "ideas");
        expectedVariation.add(4, "great");

        assertEquals(expected, actualBeg);
        assertEquals(expected, actualSpaceBeg);
        assertEquals(expected, actualMiddle);
        assertEquals(expected, actualMiddleNoSpaceBeg);
        assertEquals(expected, actualEnd);
        assertEquals(expected, actualEndNoSpaceBegSpaceEnd);
        assertEquals(expected, actualEndNoSpaceBegNoSpaceEnd);
        assertEquals(expected, actualEndSpaceEnd);
        assertEquals(expected, actualEndNoSpaceEnd);
        assertEquals(expected, actualTwoStraight);
        assertEquals(expected, actualTwoStraightNoSpace);
        assertEquals(expected, actualTwoStraightSpaceBeg);
        assertEquals(expected, actualTwoStraightSpaceEnd);
        assertEquals(expected, actualTwoStraightSpaceBeg2);
        assertEquals(expected, actualTwoStraightSpaceEnd2);
        assertEquals(expectedVariation, actualVariation);
    }
}
