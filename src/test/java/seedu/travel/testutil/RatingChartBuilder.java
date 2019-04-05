package seedu.travel.testutil;

import seedu.travel.model.chart.RatingChart;
import seedu.travel.model.place.Rating;

/**
 * A utility class to help with building RatingChart objects.
 */
public class RatingChartBuilder {

    public static final String DEFAULT_RATING = "5";
    public static final String DEFAULT_TOTAL = "5";

    private Rating rating;
    private int total;

    public RatingChartBuilder() {
        this.rating = new Rating(DEFAULT_RATING);
        this.total = Integer.parseInt(DEFAULT_TOTAL);
    }

    /**
     * Sets the {@code Rating} of the {@code RatingChart} that we are building.
     */
    public RatingChartBuilder withRating(String rating) {
        this.rating = new Rating(rating);
        return this;
    }

    /**
     * Sets the {@code int} of the {@code RatingChart} that we are building.
     */
    public RatingChartBuilder withTotal(String total) {
        this.total = Integer.parseInt(total);
        return this;
    }

    public RatingChart build() {
        return new RatingChart(rating, total);
    }
}
