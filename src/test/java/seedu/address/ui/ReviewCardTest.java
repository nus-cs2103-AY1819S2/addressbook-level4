package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysReview;

import java.sql.Timestamp;

import org.junit.Test;

import guitests.guihandles.ReviewCardHandle;
import seedu.address.model.review.Review;
import seedu.address.testutil.ReviewBuilder;

public class ReviewCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // Review with earlier TimeStamp
        Review firstReview = new ReviewBuilder().withTimestamp(new Timestamp(System.currentTimeMillis())).build();
        ReviewCard reviewCard = new ReviewCard(firstReview, 1);
        uiPartRule.setUiPart(reviewCard);
        assertCardDisplay(reviewCard, firstReview, 1);

        // Review with later TimeStamp
        Review secondReview = new ReviewBuilder().withTimestamp(new Timestamp(System.currentTimeMillis())).build();
        reviewCard = new ReviewCard(secondReview, 2);
        uiPartRule.setUiPart(reviewCard);
        assertCardDisplay(reviewCard, secondReview, 2);
    }

    @Test
    public void equals() {
        Review review = new ReviewBuilder().withTimestamp(new Timestamp(System.currentTimeMillis())).build();
        ReviewCard reviewCard = new ReviewCard(review, 0);

        // same review, same index -> returns true
        ReviewCard copy = new ReviewCard(review, 0);
        assertTrue(reviewCard.equals(copy));

        // same object -> returns true
        assertTrue(reviewCard.equals(reviewCard));

        // null -> returns false
        assertFalse(reviewCard.equals(null));

        // different types -> returns false
        assertFalse(reviewCard.equals(0));

        // different review, same index -> returns false
        Review differentReview = new ReviewBuilder().withTimestamp(new Timestamp(System.currentTimeMillis())).build();
        assertFalse(reviewCard.equals(new ReviewCard(differentReview, 0)));

        // same review, different index -> returns false
        assertFalse(reviewCard.equals(new ReviewCard(review, 1)));
    }

    /**
     * Asserts that {@code ReviewCard} displays the details of {@code expectedRestaurant} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ReviewCard reviewCard, Review expectedReview, int expectedId) {
        guiRobot.pauseForHuman();

        ReviewCardHandle reviewCardHandle = new ReviewCardHandle(reviewCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", reviewCardHandle.getId());

        // verify review details are displayed correctly
        assertCardDisplaysReview(expectedReview, reviewCardHandle);
    }
}
