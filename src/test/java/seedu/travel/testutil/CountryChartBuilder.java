package seedu.travel.testutil;

import seedu.travel.model.chart.CountryChart;
import seedu.travel.model.place.CountryCode;

/**
 * A utility class to help with building CountryChart objects.
 */
public class CountryChartBuilder {

    public static final String DEFAULT_COUNTRY_CODE = "SGP";
    public static final String DEFAULT_TOTAL = "5";

    private CountryCode countryCode;
    private int total;

    public CountryChartBuilder() {
        this.countryCode = new CountryCode(DEFAULT_COUNTRY_CODE);
        this.total = Integer.parseInt(DEFAULT_TOTAL);
    }

    /**
     * Sets the {@code CountryCode} of the {@code CountryChart} that we are building.
     */
    public CountryChartBuilder withCountryCode(String countryCode) {
        this.countryCode = new CountryCode(countryCode);
        return this;
    }

    /**
     * Sets the {@code int} of the {@code CountryChart} that we are building.
     */
    public CountryChartBuilder withTotal(String total) {
        this.total = Integer.parseInt(total);
        return this;
    }

    public CountryChart build() {
        return new CountryChart(countryCode, total);
    }
}
