package seedu.address.model.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;



public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MilestoneDescription(null));
    }

    @Test
    public void constructor_invalidClient_throwsIllegalArgumentException() {
        String invalidDescription = ""; //empty string
        Assert.assertThrows(IllegalArgumentException.class, () -> new MilestoneDescription(invalidDescription));

        String invaliddDescription = " "; //whitespace
        Assert.assertThrows(IllegalArgumentException.class, () -> new MilestoneDescription(invaliddDescription));

    }

    @Test
    public void isValidDescription() {
        // null client
        Assert.assertThrows(NullPointerException.class, () -> MilestoneDescription.isValidDescription(null));

        // invalid names
        assertFalse(MilestoneDescription.isValidDescription("")); // empty string
        assertFalse(MilestoneDescription.isValidDescription(" ")); // spaces only

        //valid names
        assertTrue(MilestoneDescription.isValidDescription("a")); //single letter
        assertTrue(MilestoneDescription.isValidDescription("apple")); //word
        assertTrue(MilestoneDescription.isValidDescription("apple pie")); //words
        assertTrue(MilestoneDescription.isValidDescription("apple 9 completed")); //alphanumeric
        assertTrue(MilestoneDescription.isValidDescription("apple & oranges")); //symbols
    }
}
