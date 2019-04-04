package seedu.travel.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.travel.model.ChartBook;
import seedu.travel.model.chart.RatingChart;

/**
 * A utility class containing a list of {@code RatingChart} objects to be used in tests.
 */
public class TypicalRatingChart {

    public static final RatingChart ONE_STAR = new RatingChartBuilder()
            .withRating("1")
            .withTotal("6")
            .build();

    public static final RatingChart TWO_STAR = new RatingChartBuilder()
            .withRating("2")
            .withTotal("7")
            .build();

    public static final RatingChart THREE_STAR = new RatingChartBuilder()
            .withRating("3")
            .withTotal("8")
            .build();

    public static final RatingChart FOUR_STAR = new RatingChartBuilder()
            .withRating("4")
            .withTotal("9")
            .build();

    public static final RatingChart FIVE_STAR = new RatingChartBuilder()
            .withRating("5")
            .withTotal("10")
            .build();

    private TypicalRatingChart() {} // prevents instantiation

    /**
     * Returns an {@code ChartBook} with all the typical rating chart.
     */
    public static ChartBook getTypicalChartBook() {
        ChartBook chartBook = new ChartBook();
        for (RatingChart ratingChart : getTypicalRatingChart()) {
            chartBook.addRatingChart(ratingChart);
        }
        return chartBook;
    }

    public static List<RatingChart> getTypicalRatingChart() {
        return new ArrayList<>(Arrays.asList(ONE_STAR, TWO_STAR, THREE_STAR, FOUR_STAR, FIVE_STAR));
    }
}
