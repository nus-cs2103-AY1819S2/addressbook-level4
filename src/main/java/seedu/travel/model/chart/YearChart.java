package seedu.travel.model.chart;

import java.util.Objects;

/**
 * Represents a year chart in TravelBuddy.
 */
public class YearChart {
    private String year;
    private int total;

    public YearChart() {}

    public YearChart(String year, int total) {
        this();
        this.year = year;
        this.total = total;
    }

    public String getChartYear() {
        return year;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Returns true if both year charts have the same identity and data fields.
     * This defines a stronger notion of equality between two year charts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof YearChart)) {
            return false;
        }

        YearChart otherPlace = (YearChart) other;
        return otherPlace.getChartYear().equals(getChartYear())
                && otherPlace.getTotal() == getTotal();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(year, total);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Year: ")
                .append(getChartYear())
                .append(" Total: ")
                .append(getTotal());
        return builder.toString();
    }
}
