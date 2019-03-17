package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ReviewTitleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ReviewTitle(null));
    }

    @Test
    public void constructor_invalidReviewTitle_throwsIllegalArgumentException() {
        String invalidReviewTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ReviewTitle(invalidReviewTitle));
    }

    @Test
    public void isValidReviewTitle() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> ReviewTitle.isValidReviewTitle(null));

        // invalid name
        assertFalse(ReviewTitle.isValidReviewTitle("")); // empty string
        assertFalse(ReviewTitle.isValidReviewTitle(" ")); // spaces only
        assertFalse(ReviewTitle.isValidReviewTitle("^")); // only non-alphanumeric characters
        assertFalse(ReviewTitle.isValidReviewTitle("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(ReviewTitle.isValidReviewTitle("peter jack")); // alphabets only
        assertTrue(ReviewTitle.isValidReviewTitle("12345")); // numbers only
        assertTrue(ReviewTitle.isValidReviewTitle("peter the 2nd")); // alphanumeric characters
        assertTrue(ReviewTitle.isValidReviewTitle("Capital Tan")); // with capital letters
        assertTrue(ReviewTitle.isValidReviewTitle("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
