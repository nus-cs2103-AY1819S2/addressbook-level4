package seedu.address.model.restaurant.categories;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OccasionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Occasion(null));
    }

    @Test
    public void constructor_invalidOccasion_throwsIllegalArgumentException() {
        String invalidOccasion = "F&*dining";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Occasion(invalidOccasion));
    }

    @Test
    public void isValidOccasion() {
        // null occasion
        Assert.assertThrows(NullPointerException.class, () -> Occasion.isValidOccasion(null));

        // invalid occasion
        assertFalse(Occasion.isValidOccasion("")); // empty string
        assertFalse(Occasion.isValidOccasion(" ")); // spaces only
        assertFalse(Occasion.isValidOccasion("^")); // only non-alphanumeric characters
        assertFalse(Occasion.isValidOccasion("casual*")); // contains non-alphanumeric characters

        // valid occasion
        assertTrue(Occasion.isValidOccasion("fine dining")); // alphabets only
        assertTrue(Occasion.isValidOccasion("12345")); // numbers only
        assertTrue(Occasion.isValidOccasion("2nd best premium")); // alphanumeric characters
        assertTrue(Occasion.isValidOccasion("Premium Casual")); // with capital letters
        assertTrue(Occasion.isValidOccasion("Fine Dining but Also Accepts Casual Wear")); // long names
    }
}
