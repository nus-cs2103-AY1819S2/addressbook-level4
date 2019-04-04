package seedu.travel.model.chart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.travel.model.place.Rating;

public class RatingChartTest {

    @Test
    public void testChartRating() {

        Rating firstRating = new Rating("3");
        RatingChart firstRatingChart = new RatingChart(new Rating("3"), 4);
        RatingChart secondRatingChart = new RatingChart(new Rating("4"), 4);

        assertEquals(firstRating, (firstRatingChart.getChartRating()));
        assertNotEquals(firstRatingChart.getChartRating(), (secondRatingChart.getChartRating()));
    }

    @Test
    public void equals() {

        Rating firstRating = new Rating("3");
        Rating secondRating = new Rating("4");

        RatingChart firstRatingValueFour = new RatingChart(firstRating, 4);
        RatingChart firstRatingValueFive = new RatingChart(firstRating, 5);
        RatingChart secondRatingValueFour = new RatingChart(secondRating, 4);
        RatingChart secondRatingValueFive = new RatingChart(secondRating, 5);

        // same object -> returns true
        assertTrue(firstRatingValueFour.equals(firstRatingValueFour));

        // same values -> returns true
        RatingChart firstRatingValueFourCopy = new RatingChart(firstRating, 4);
        assertTrue(firstRatingValueFour.equals(firstRatingValueFourCopy));

        // different types -> returns false
        assertFalse(firstRatingValueFour.equals(1));

        // null -> returns false
        assertFalse(firstRatingValueFour.equals(null));

        // different country, same total -> returns false
        assertFalse(firstRatingValueFour.equals(secondRatingValueFour));

        // same country, different total -> returns false
        assertFalse(firstRatingValueFour.equals(firstRatingValueFive));

        // different country, different total -> returns false
        assertFalse(firstRatingValueFour.equals(secondRatingValueFive));
    }

    @Test
    public void testToString() {
        RatingChart testRating = new RatingChart(new Rating("4"), 4);

        String testSameOutputString = "Rating: 4 Total: 4";
        String testDifferentOutputString = "Rating: 3 Total: 6";

        assertEquals(testRating.toString(), testSameOutputString);
        assertNotEquals(testRating.toString(), testDifferentOutputString);

    }
}
