package seedu.address.model.restaurant.categories;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CuisineTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Cuisine(null));
    }

    @Test
    public void constructor_invalidCuisine_throwsIllegalArgumentException() {
        String invalidCuisine = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Cuisine(invalidCuisine));
    }

    @Test
    public void isValidCuisine() {
        // null cuisine
        Assert.assertThrows(NullPointerException.class, () -> Cuisine.isValidCuisine(null));

        // invalid cuisine
        assertFalse(Cuisine.isValidCuisine("")); // empty string
        assertFalse(Cuisine.isValidCuisine(" ")); // spaces only
        assertFalse(Cuisine.isValidCuisine("^")); // only non-alphanumeric characters
        assertFalse(Cuisine.isValidCuisine("food*")); // contains non-alphanumeric characters

        // valid cuisine
        assertTrue(Cuisine.isValidCuisine("fast food")); // alphabets only
        assertTrue(Cuisine.isValidCuisine("12345")); // numbers only
        assertTrue(Cuisine.isValidCuisine("type 2 fast food")); // alphanumeric characters
        assertTrue(Cuisine.isValidCuisine("Fast Food")); // with capital letters
        assertTrue(Cuisine.isValidCuisine("Fast Food that is Extremely Good")); // long names
    }
}
