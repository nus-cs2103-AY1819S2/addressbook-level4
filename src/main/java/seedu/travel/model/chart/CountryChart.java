package seedu.travel.model.chart;

import java.util.Objects;

import seedu.travel.model.place.CountryCode;

/**
 * Represents a country chart in TravelBuddy.
 */
public class CountryChart {
    private CountryCode countryCode;
    private int total;

    public CountryChart(CountryCode countryCode, int total) {
        this.countryCode = countryCode;
        this.total = total;
    }

    public CountryCode getChartCountryCode() {
        return countryCode;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Returns true if both country charts have the same identity and data fields.
     * This defines a stronger notion of equality between two country charts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CountryChart)) {
            return false;
        }

        CountryChart otherPlace = (CountryChart) other;
        return otherPlace.getChartCountryCode().equals(getChartCountryCode())
                && otherPlace.getTotal() == getTotal();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(countryCode, total);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Country Code: ")
                .append(getChartCountryCode())
                .append(" Total: ")
                .append(getTotal());
        return builder.toString();
    }
}
