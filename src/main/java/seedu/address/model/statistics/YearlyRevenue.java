package seedu.address.model.statistics;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Yearly Revenue which contains the year and total revenue earned on that specific year.
 */
public class YearlyRevenue {
    private final Year year;
    private float totalYearlyRevenue;

    /**
     * Every field must be present and not null.
     */
    public YearlyRevenue(Year year, float totalMonthlyRevenue) {
        requireAllNonNull(year, totalMonthlyRevenue);
        this.year = year;
        this.totalYearlyRevenue = totalMonthlyRevenue;
    }

    /**
     * Adds new revenue into the total monthly revenue.
     */
    public void addToRevenue(float revenue) {
        totalYearlyRevenue += revenue;
    }

    /**
     * Gets the year.
     */
    public Year getYear() {
        return year;
    }

    /**
     * Gets the total revenue for the month.
     */
    public float getTotalYearlyRevenue() {
        return totalYearlyRevenue;
    }

    /**
     * Returns true if both yearlyRevenue have the same year.
     * This defines a weaker notion of equality between two yearlyRevenue.
     */
    public boolean isSameYearlyRevenue(YearlyRevenue otherYearlyRevenue) {
        if (otherYearlyRevenue == this) {
            return true;
        }

        return otherYearlyRevenue != null && otherYearlyRevenue.getYear().equals(getYear());
    }

    /**
     * Returns true if both yearlyRevenue have the same year and totalRevenue.
     * This defines a stronger notion of equality between two order items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof YearlyRevenue)) {
            return false;
        }

        YearlyRevenue otherYearlyRevenue = (YearlyRevenue) other;
        return isSameYearlyRevenue(otherYearlyRevenue)
                && otherYearlyRevenue.getTotalYearlyRevenue() == getTotalYearlyRevenue();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(year, totalYearlyRevenue);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Total Revenue for ")
                .append(getYear())
                .append(" : $")
                .append(getTotalYearlyRevenue());
        return builder.toString();
    }

}
