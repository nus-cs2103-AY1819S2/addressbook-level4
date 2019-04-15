package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysReview;

import org.junit.Test;

import guitests.guihandles.ReviewCardHandle;
import seedu.address.model.book.Review;
import seedu.address.testutil.ReviewBuilder;


public class ReviewCardTest extends GuiUnitTest {
    @Test
    public void display() {
        Review review = new ReviewBuilder().build();
        ReviewCard reviewCard = new ReviewCard(review, 1);
        uiPartRule.setUiPart(reviewCard);
        assertCardDisplay(reviewCard, review);
    }

    @Test
    public void equals() {
        Review review = new ReviewBuilder().build();
        ReviewCard reviewCard = new ReviewCard(review, 0);

        // same person, same index -> returns true
        ReviewCard copy = new ReviewCard(review, 0);
        assertTrue(reviewCard.equals(copy));

        // same object -> returns true
        assertTrue(reviewCard.equals(reviewCard));

        // null -> returns false
        assertFalse(reviewCard.equals(null));

        // different types -> returns false
        assertFalse(reviewCard.equals(0));

        // different review, same index -> returns false
        Review differentReview = new ReviewBuilder().withReviewTitle("differentTitle").build();
        assertFalse(reviewCard.equals(new ReviewCard(differentReview, 0)));

        // same person, different index -> returns false
        assertFalse(reviewCard.equals(new ReviewCard(review, 1)));
    }

    /**
     * Asserts that {@code reviewCard} displays the details of {@code expectedReview} correctly and matches
     * {@code expectedName}.
     */
    private void assertCardDisplay(ReviewCard reviewCard, Review expectedReview) {
        guiRobot.pauseForHuman();

        ReviewCardHandle reviewCardHandle = new ReviewCardHandle(reviewCard.getRoot());

        // verify person details are displayed correctly
        assertCardDisplaysReview(expectedReview, reviewCardHandle);
    }
}
