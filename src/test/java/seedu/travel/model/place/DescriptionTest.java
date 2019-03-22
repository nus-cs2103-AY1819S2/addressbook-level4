package seedu.travel.model.place;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.travel.testutil.Assert;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        Assert.assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // blank description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // invalid description
        assertFalse(Description.isValidDescription("1 love this place")); // begin with a digit
        assertFalse(Description.isValidDescription("@ love this place")); // begin with a symbol
        assertFalse(Description.isValidDescription(" I love this place")); // begin with a space

        // valid description
        assertTrue(Description.isValidDescription("I love this place")); // begin with uppercase alphabet
        assertTrue(Description.isValidDescription("i love this place")); // begin with lowercase alphabet
        assertTrue(Description.isValidDescription("I love this place.")); // ends with a symbol
        assertTrue(Description.isValidDescription("I love this place     ")); // ends with trailing spaces
        assertTrue(Description.isValidDescription("I love this place. It reminds me of home")); // Multiple sentences
    }
}
