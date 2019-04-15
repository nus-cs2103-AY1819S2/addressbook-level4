package seedu.address.testutil;

import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Revenue;
import seedu.address.model.statistics.Year;

/**
 * A utility class to help with building Statistics objects.
 */
public class StatisticsBuilder {

    public static final String DEFAULT_DAY = "1";
    public static final String DEFAULT_MONTH = "1";
    public static final String DEFAULT_YEAR = "2019";
    public static final String DEFAULT_REVENUE = "0.00";

    private Day day;
    private Month month;
    private Year year;
    private float totalRevenue;

    public StatisticsBuilder() {
        day = new Day(DEFAULT_DAY);
        month = new Month(DEFAULT_MONTH);
        year = new Year(DEFAULT_YEAR);
        totalRevenue = Float.parseFloat(DEFAULT_REVENUE);
    }

    /**
     * Initializes the StatisticsBuilder with the data of {@code itemToCopy}.
     */
    public StatisticsBuilder(Revenue itemToCopy) {
        day = itemToCopy.getDay();
        month = itemToCopy.getMonth();
        year = itemToCopy.getYear();
        totalRevenue = itemToCopy.getTotalRevenue();
    }

    /**
     * Sets the {@code Day} of the {@code Revenue} that we are building.
     */
    public StatisticsBuilder withDay(String day) {
        this.day = new Day(day);
        return this;
    }

    /**
     * Sets the {@code Month} of the {@code Revenue} that we are building.
     */
    public StatisticsBuilder withMonth(String month) {
        this.month = new Month(month);
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code Revenue} that we are building.
     */
    public StatisticsBuilder withYear(String year) {
        this.year = new Year(year);
        return this;
    }

    /**
     * Sets the {@code totalRevenue} of the {@code Revenue} that we are building.
     */
    public StatisticsBuilder withTotalRevenue(String totalRevenue) {
        this.totalRevenue = Float.parseFloat(totalRevenue);
        return this;
    }

    public Revenue build() {
        return new Revenue(day, month, year, totalRevenue);
    }
}
