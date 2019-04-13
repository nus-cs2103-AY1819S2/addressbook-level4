package seedu.address.testutil;

import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.MonthlyRevenue;
import seedu.address.model.statistics.Year;

/**
 * A utility class to help with building MonthlyRevenue objects.
 */
public class MonthlyRevenueBuilder {

    public static final String DEFAULT_MONTH = "1";
    public static final String DEFAULT_YEAR = "2019";
    public static final String DEFAULT_DAILY_REVENUE = "0.00";

    private Month month;
    private Year year;
    private float totalMonthlyRevenue;

    public MonthlyRevenueBuilder() {
        month = new Month(DEFAULT_MONTH);
        year = new Year(DEFAULT_YEAR);
        totalMonthlyRevenue = Float.parseFloat(DEFAULT_DAILY_REVENUE);
    }

    /**
     * Initializes the MonthlyRevenueBuilder with the data of {@code itemToCopy}.
     */
    public MonthlyRevenueBuilder(MonthlyRevenue itemToCopy) {
        month = itemToCopy.getMonth();
        year = itemToCopy.getYear();
        totalMonthlyRevenue = itemToCopy.getTotalRevenue();
    }

    /**
     * Sets the {@code Month} of the {@code MonthlyRevenue} that we are building.
     */
    public MonthlyRevenueBuilder withMonth(String month) {
        this.month = new Month(month);
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code MonthlyRevenue} that we are building.
     */
    public MonthlyRevenueBuilder withYear(String year) {
        this.year = new Year(year);
        return this;
    }

    /**
     * Sets the {@code totalMonthlyRevenue} of the {@code MonthlyRevenue} that we are building.
     */
    public MonthlyRevenueBuilder withTotalMonthlyRevenue(String totalMonthlyRevenue) {
        this.totalMonthlyRevenue = Float.parseFloat(totalMonthlyRevenue);
        return this;
    }

    public MonthlyRevenue build() {
        return new MonthlyRevenue(month, year, totalMonthlyRevenue);
    }
}
