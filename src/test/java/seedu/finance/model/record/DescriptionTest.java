package seedu.finance.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.finance.testutil.Assert;

public class DescriptionTest {

    @Test
    public void equals() {
        Description description = new Description("description");

        // Description object with same values -> returns true
        Description test = new Description(description.value);
        assertTrue(description.equals(test));

        // Same object -> returns true
        assertTrue(description.equals(description));

        // Different types -> return false
        assertFalse(description.equals(2));

        // null -> returns false
        assertFalse(description.equals(null));

        // different description -> returns false
        Description test2 = new Description("other description");
        assertFalse(description.equals(test2));
    }

    @Test
    public void isValid() {
        // null amount
        Assert.assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid amounts
        assertFalse(Description.isValidDescription("12345678901234567890123456789012345678901")); //41 characters
        // valid amounts
        assertTrue(Description.isValidDescription("abc")); //only characters
        assertTrue(Description.isValidDescription("abc123!@#")); //mixture of characters, numbers and symbols
        assertTrue(Description.isValidDescription("1234567890123456789012345678901234567890")); // 40 character
        assertTrue(Description.isValidDescription("")); // empty description
    }
}
