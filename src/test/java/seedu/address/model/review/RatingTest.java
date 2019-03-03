package seedu.address.model.review;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RatingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Rating(null));
    }

    @Test
    public void constructor_invalidRating_throwsIllegalArgumentException() {
        String invalidRating = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Rating(invalidRating));
    }

    @Test
    public void isValidRating() {
        // null rating
        Assert.assertThrows(NullPointerException.class, () -> Rating.isValidRating(null));

        // invalid ratings
        assertFalse(Rating.isValidRating("")); // empty string
        assertFalse(Rating.isValidRating(" ")); // spaces only
        assertFalse(Rating.isValidRating("-5.0")); // negative number
        assertFalse(Rating.isValidRating("Rating")); // non-numeric
        assertFalse(Rating.isValidRating("4a8")); // alphabets within digits
        assertFalse(Rating.isValidRating("5 0")); // spaces within digits
        assertFalse(Rating.isValidRating("5.1")); // greater than 5
        assertFalse(Rating.isValidRating("4.88")); // more than 1 decimal place

        // valid Rating numbers
        assertTrue(Rating.isValidRating("2")); // single digit
        assertTrue(Rating.isValidRating("0")); // single digit within range [0,5]
        assertTrue(Rating.isValidRating("2.5")); // 1 decimal place within range [0,5]
        assertTrue(Rating.isValidRating("5.0")); // 1 decimal place within range [0,5]
    }
}
