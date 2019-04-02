package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWMESSAGE_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEWTITLE_CS;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.address.testutil.ReviewBuilder;

public class ReviewTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Review(null, new BookName(VALID_BOOKNAME_CS), VALID_DATE, VALID_REVIEWMESSAGE_CS));
        assertThrows(NullPointerException.class, () -> new Review(
                new ReviewTitle(VALID_REVIEWTITLE_CS), null, VALID_DATE, VALID_REVIEWMESSAGE_CS));
        assertThrows(NullPointerException.class, () -> new Review(new ReviewTitle(VALID_REVIEWTITLE_CS),
                new BookName(VALID_BOOKNAME_CS), null, VALID_REVIEWMESSAGE_CS));
        assertThrows(NullPointerException.class, () -> new Review(
                new ReviewTitle(VALID_REVIEWTITLE_CS), new BookName(VALID_BOOKNAME_CS), VALID_DATE, null));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Review review = new ReviewBuilder().build();
        Review reviewCopy = new ReviewBuilder(review).build();
        assertTrue(review.equals(reviewCopy));

        // same object -> returns true
        assertTrue(review.equals(review));

        // null -> returns false
        assertFalse(review.equals(null));

        // different type -> returns false
        assertFalse(review.equals(5));

        // different review -> returns false
        assertFalse(review.equals(new Review(new ReviewTitle("A great fairytale"),
                new BookName("Alice in Wonderland"), VALID_DATE, "While...")));

        // different book name -> returns false
        Review editedReview = new ReviewBuilder(review).withBookName(VALID_BOOKNAME_CS).build();
        assertFalse(review.equals(editedReview));

        // different review title -> returns false
        editedReview = new ReviewBuilder(review).withReviewTitle(VALID_REVIEWTITLE_CS).build();
        assertFalse(review.equals(editedReview));

        // different message -> returns false
        editedReview = new ReviewBuilder(review).withReviewMessage(VALID_REVIEWMESSAGE_CS).build();
        assertFalse(review.equals(editedReview));
    }
}
