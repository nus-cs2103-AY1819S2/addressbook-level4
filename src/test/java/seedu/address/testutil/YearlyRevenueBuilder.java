package seedu.address.testutil;

import seedu.address.model.statistics.Year;
import seedu.address.model.statistics.YearlyRevenue;

/**
 * A utility class to help with building YearlyRevenue objects.
 */
public class YearlyRevenueBuilder {

    public static final String DEFAULT_YEAR = "2019";
    public static final String DEFAULT_YEARLY_REVENUE = "0.00";

    private Year year;
    private float totalYearlyRevenue;

    public YearlyRevenueBuilder() {
        year = new Year(DEFAULT_YEAR);
        totalYearlyRevenue = Float.parseFloat(DEFAULT_YEARLY_REVENUE);
    }

    /**
     * Initializes the YearlyRevenueBuilder with the data of {@code itemToCopy}.
     */
    public YearlyRevenueBuilder(YearlyRevenue itemToCopy) {
        year = itemToCopy.getYear();
        totalYearlyRevenue = itemToCopy.getTotalRevenue();
    }
    /**
     * Sets the {@code Year} of the {@code DailyRevenue} that we are building.
     */
    public YearlyRevenueBuilder withYear(String year) {
        this.year = new Year(year);
        return this;
    }

    /**
     * Sets the {@code totalYearlyRevenue} of the {@code YearlyRevenue} that we are building.
     */
    public YearlyRevenueBuilder withTotalYearlyRevenue(String totalYearlyRevenue) {
        this.totalYearlyRevenue = Float.parseFloat(totalYearlyRevenue);
        return this;
    }

    public YearlyRevenue build() {
        return new YearlyRevenue(year, totalYearlyRevenue);
    }
}

