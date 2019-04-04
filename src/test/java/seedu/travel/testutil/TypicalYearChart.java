package seedu.travel.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.travel.model.ChartBook;
import seedu.travel.model.chart.YearChart;

/**
 * A utility class containing a list of {@code YearChart} objects to be used in tests.
 */
public class TypicalYearChart {

    public static final YearChart TWO_ZERO_ONE_ZERO = new YearChartBuilder()
            .withYear("2010")
            .withTotal("6")
            .build();

    public static final YearChart TWO_ZERO_ONE_ONE = new YearChartBuilder()
            .withYear("2011")
            .withTotal("7")
            .build();

    public static final YearChart TWO_ZERO_ONE_TWO = new YearChartBuilder()
            .withYear("2012")
            .withTotal("8")
            .build();

    public static final YearChart TWO_ZERO_ONE_THREE = new YearChartBuilder()
            .withYear("2013")
            .withTotal("9")
            .build();

    public static final YearChart TWO_ZERO_ONE_FOUR = new YearChartBuilder()
            .withYear("2014")
            .withTotal("10")
            .build();

    private TypicalYearChart() {} // prevents instantiation

    /**
     * Returns an {@code ChartBook} with all the typical year chart.
     */
    public static ChartBook getTypicalChartBook() {
        ChartBook chartBook = new ChartBook();
        for (YearChart yearChart : getTypicalYearChart()) {
            chartBook.addYearChart(yearChart);
        }
        return chartBook;
    }

    public static List<YearChart> getTypicalYearChart() {
        return new ArrayList<>(Arrays.asList(TWO_ZERO_ONE_ZERO, TWO_ZERO_ONE_ONE,
                TWO_ZERO_ONE_TWO, TWO_ZERO_ONE_THREE, TWO_ZERO_ONE_FOUR));
    }
}
