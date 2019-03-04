package seedu.address.model.review;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

public class ReviewTest {

    private static final Entry FIRST_ENTRY = new Entry("Great atmosphere!");
    private static final Rating FIRST_RATING = new Rating("4.6");
    private static final Entry SECOND_ENTRY = new Entry("Love the service!");
    private static final Rating SECOND_RATING = new Rating("4");
    private static final Timestamp TIMESTAMP = new Timestamp(new Date(2323223232L).getTime());
    private static final Review REVIEW = new Review(FIRST_ENTRY, FIRST_RATING);

    @Test
    public void isSameReview() {

        // same object -> returns true
        assertTrue(REVIEW.isSameReview(REVIEW));

        // null -> returns false
        assertFalse(REVIEW.isSameReview(null));

        // different timestamps -> returns false
        Review editedReview = new Review(FIRST_ENTRY, FIRST_RATING, TIMESTAMP);
        assertFalse(REVIEW.isSameReview(editedReview));

        // same timestamps, same entry, different rating -> returns true
        Review anotherEditedReview = new Review(FIRST_ENTRY, SECOND_RATING, TIMESTAMP);
        assertTrue(editedReview.isSameReview(anotherEditedReview));

        // same timestamps, same rating, different entries -> returns false
        Review otherEditedReview = new Review(SECOND_ENTRY, FIRST_RATING, TIMESTAMP);
        assertFalse(editedReview.isSameReview(otherEditedReview));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Review changeTimeStamp = new Review(FIRST_ENTRY, FIRST_RATING, TIMESTAMP);
        Review changeTimeStampCopy = new Review(FIRST_ENTRY, FIRST_RATING, TIMESTAMP);
        assertTrue(changeTimeStamp.equals(changeTimeStampCopy));

        // same object -> returns true
        assertTrue(REVIEW.equals(REVIEW));

        // null -> returns false
        assertFalse(REVIEW.equals(null));

        // different type -> returns false
        assertFalse(REVIEW.equals(5));

        // different Review -> returns false
        assertFalse(REVIEW.equals(changeTimeStamp));

        // different timestamps -> returns false
        assertFalse(REVIEW.equals(changeTimeStamp));

        // different entries -> returns false
        Review changeEntry = new Review(SECOND_ENTRY, FIRST_RATING, TIMESTAMP);
        assertFalse(changeTimeStamp.equals(changeEntry));

        // different ratings -> returns false
        Review changeRating = new Review(FIRST_ENTRY, SECOND_RATING, TIMESTAMP);
        assertFalse(changeRating.equals(changeTimeStamp));
    }
}
