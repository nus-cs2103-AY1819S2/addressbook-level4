package seedu.travel.model.chart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.travel.model.chart.exceptions.RatingChartNotFoundException;
import seedu.travel.model.place.Rating;

public class RatingChartListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void add_validRating_addSuccessful() {
        RatingChartList ratingChartList = new RatingChartList();
        RatingChart firstRatingChart = new RatingChart(new Rating("4"), 1);
        RatingChart secondRatingChart = new RatingChart(new Rating("3"), 1);

        ratingChartList.add(firstRatingChart); // add new rating
        assertFalse(ratingChartList.asUnmodifiableObservableList().isEmpty());

        ratingChartList.add(firstRatingChart); // add duplicate valid rating
        assertEquals(ratingChartList.asUnmodifiableObservableList().get(0).getChartRating(), new Rating("4"));
        assertEquals(ratingChartList.asUnmodifiableObservableList().get(0).getTotal(), 2);

        ratingChartList.add(secondRatingChart); // add new valid rating to non-empty list
        assertEquals(ratingChartList.asUnmodifiableObservableList().get(1).getChartRating(), new Rating("3"));
        assertEquals(ratingChartList.asUnmodifiableObservableList().get(1).getTotal(), 1);
    }

    @Test
    public void remove_validRating_removeSuccessful() {
        RatingChartList ratingChartList = new RatingChartList();
        RatingChart firstRatingChart = new RatingChart(new Rating("4"), 1);
        RatingChart secondRatingChart = new RatingChart(new Rating("3"), 1);

        ratingChartList.add(firstRatingChart);
        ratingChartList.add(firstRatingChart);
        ratingChartList.add(firstRatingChart);
        ratingChartList.add(secondRatingChart);
        ratingChartList.add(secondRatingChart);

        ratingChartList.remove(secondRatingChart);

        RatingChartList expectedRatingChartList = new RatingChartList();
        expectedRatingChartList.add(firstRatingChart);
        expectedRatingChartList.add(firstRatingChart);
        expectedRatingChartList.add(firstRatingChart);
        expectedRatingChartList.add(secondRatingChart);

        assertEquals(ratingChartList, expectedRatingChartList);

    }

    @Test
    public void remove_invalidRating_throwsRatingChartNotFoundException() {
        RatingChartList ratingChartList = new RatingChartList();
        RatingChart firstRatingChart = new RatingChart(new Rating("4"), 1);
        RatingChart secondRatingChart = new RatingChart(new Rating("3"), 1);

        ratingChartList.add(firstRatingChart);
        ratingChartList.add(firstRatingChart);
        ratingChartList.add(firstRatingChart);
        ratingChartList.add(secondRatingChart);
        ratingChartList.add(secondRatingChart);

        thrown.expect(RatingChartNotFoundException.class);
        thrown.expectMessage(RatingChartList.MESSAGE_RATING_CHART_NOT_FOUND);

        ratingChartList.remove(new RatingChart(new Rating("5"), 4));
    }
}
