package seedu.address.model.restaurant;

import static org.junit.Assert.assertTrue;
import static seedu.address.model.restaurant.Summary.NO_RATING;

import java.text.DecimalFormat;
import java.util.List;

import org.junit.Test;

import seedu.address.model.review.Review;
import seedu.address.testutil.Assert;
import seedu.address.testutil.RestaurantBuilder;
import seedu.address.testutil.TypicalReviews;

public class SummaryTest {
    private static final List<Review> TYPICAL_REVIEWS = TypicalReviews.getTypicalReviews();
    private static final DecimalFormat ONE_DP = new DecimalFormat("#.#");

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Summary(null));
    }

    @Test
    public void test_emptyReviewList_success() {
        List<Review> emptyReviewList = new RestaurantBuilder().build().getReviews();
        Summary emptySummary = new Summary(emptyReviewList);

        assertTrue(emptySummary.getAvgRating().equals(NO_RATING));
        assertTrue(emptySummary.getTotalVisits().equals(0));
    }

    @Test
    public void test_nonEmptyReviewList_success() {
        Summary nonEmptySummary = new Summary(TYPICAL_REVIEWS);
        Integer totalVisits = TYPICAL_REVIEWS.size();
        Float sumRating = Float.valueOf(0);

        for (Review r : TYPICAL_REVIEWS) {
            sumRating += r.getRating().toFloat();
        }

        Float avgRating = sumRating / totalVisits;

        assertTrue(nonEmptySummary.getTotalVisits().equals(TYPICAL_REVIEWS.size()));
        assertTrue(nonEmptySummary.getAvgRating().equals(Float.valueOf(ONE_DP.format(avgRating))));
    }
}
