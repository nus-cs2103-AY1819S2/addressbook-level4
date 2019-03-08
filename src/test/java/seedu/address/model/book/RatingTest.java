package seedu.address.model.book;

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

        // blank rating
        assertFalse(Rating.isValidRating("")); // empty string
        assertFalse(Rating.isValidRating(" ")); // spaces only

        // invalid number
        assertFalse(Rating.isValidRating("15")); // missing local part
        assertFalse(Rating.isValidRating("100")); // missing '@' symbol
        assertFalse(Rating.isValidRating("6162333")); // missing domain name

        // invalid parts
        assertFalse(Rating.isValidRating("10-")); // invalid symbol -
        assertFalse(Rating.isValidRating("-6")); // invalid symbol -
        assertFalse(Rating.isValidRating("10.5")); // invalid symbol .
        assertFalse(Rating.isValidRating("peterjack")); // invalid alphabet character
        assertFalse(Rating.isValidRating("..;3")); // invalid symbol
        assertFalse(Rating.isValidRating(" 10")); // leading space
        assertFalse(Rating.isValidRating("10 ")); // trailing space

        // valid rating
        assertTrue(Rating.isValidRating("5"));
        assertTrue(Rating.isValidRating("0")); // minimal
        assertTrue(Rating.isValidRating("10")); // maximal
        assertTrue(Rating.isValidRating("6"));
    }
}
