package seedu.address.testutil;

import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Year;

/**
 * A utility class to help with building Statistics objects.
 */
public class StatisticsBuilder {

    public static final String DEFAULT_DAY = "1";
    public static final String DEFAULT_MONTH = "1";
    public static final String DEFAULT_YEAR = "2019";
    public static final String DEFAULT_DAILY_REVENUE = "0.00";

    private Day day;
    private Month month;
    private Year year;
    private float totalDailyRevenue;

    public StatisticsBuilder() {
        day = new Day(DEFAULT_DAY);
        month = new Month(DEFAULT_MONTH);
        year = new Year(DEFAULT_YEAR);
        totalDailyRevenue = Float.parseFloat(DEFAULT_DAILY_REVENUE);
    }

    /**
     * Initializes the StatisticsBuilder with the data of {@code itemToCopy}.
     */
    public StatisticsBuilder(DailyRevenue itemToCopy) {
        day = itemToCopy.getDay();
        month = itemToCopy.getMonth();
        year = itemToCopy.getYear();
        totalDailyRevenue = itemToCopy.getTotalDailyRevenue();
    }

    /**
     * Sets the {@code Day} of the {@code DailyRevenue} that we are building.
     */
    public StatisticsBuilder withDay(String day) {
        this.day = new Day(day);
        return this;
    }

    /**
     * Sets the {@code Month} of the {@code DailyRevenue} that we are building.
     */
    public StatisticsBuilder withMonth(String month) {
        this.month = new Month(month);
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code DailyRevenue} that we are building.
     */
    public StatisticsBuilder withYear(String year) {
        this.year = new Year(year);
        return this;
    }

    /**
     * Sets the {@code totalDailyRevenue} of the {@code DailyRevenue} that we are building.
     */
    public StatisticsBuilder withTotalDailyRevenue(String totalDailyRevenue) {
        this.totalDailyRevenue = Float.parseFloat(totalDailyRevenue);
        return this;
    }

    public DailyRevenue build() {
        return new DailyRevenue(day, month, year, totalDailyRevenue);
    }
}
