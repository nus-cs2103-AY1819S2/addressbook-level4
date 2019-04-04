package seedu.travel.testutil;

import seedu.travel.model.chart.YearChart;

/**
 * A utility class to help with building YearChart objects.
 */
public class YearChartBuilder {

    public static final String DEFAULT_YEAR = "2019";
    public static final String DEFAULT_TOTAL = "5";

    private String year;
    private int total;

    public YearChartBuilder() {
        this.year = DEFAULT_YEAR;
        this.total = Integer.parseInt(DEFAULT_TOTAL);
    }

    /**
     * Sets the {@code String} of the {@code YearChart} that we are building.
     */
    public YearChartBuilder withYear(String year) {
        this.year = year;
        return this;
    }

    /**
     * Sets the {@code int} of the {@code YearChart} that we are building.
     */
    public YearChartBuilder withTotal(String total) {
        this.total = Integer.parseInt(total);
        return this;
    }

    public YearChart build() {
        return new YearChart(year, total);
    }
}
