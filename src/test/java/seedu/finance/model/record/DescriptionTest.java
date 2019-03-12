package seedu.finance.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
}
