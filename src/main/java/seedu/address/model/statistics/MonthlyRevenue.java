package seedu.address.model.statistics;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Monthly Revenue which contains the month year and total revenue earned on that specific month.
 */
public class MonthlyRevenue {

    private final Month month;
    private final Year year;
    private float totalMonthlyRevenue;

    /**
     * Every field must be present and not null.
     */
    public MonthlyRevenue(Month month, Year year, float totalMonthlyRevenue) {
        requireAllNonNull(month, year, totalMonthlyRevenue);
        this.month = month;
        this.year = year;
        this.totalMonthlyRevenue = totalMonthlyRevenue;
    }

    /**
     * Adds new revenue into the total monthly revenue.
     */
    public void addToRevenue(float revenue) {
        totalMonthlyRevenue += revenue;
    }

    /**
     * Gets the month.
     */
    public Month getMonth() {
        return month;
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
    public float getTotalMonthlyRevenue() {
        return totalMonthlyRevenue;
    }

    /**
     * Returns true if both monthlyRevenue have the same month and year.
     * This defines a weaker notion of equality between two monthlyRevenue.
     */
    public boolean isSameMonthlyRevenue(MonthlyRevenue otherMonthlyRevenue) {
        if (otherMonthlyRevenue == this) {
            return true;
        }

        return otherMonthlyRevenue != null && otherMonthlyRevenue.getMonth().equals(getMonth())
                && otherMonthlyRevenue.getYear().equals(getYear());
    }

    /**
     * Returns true if both monthlyRevenue have the same month, year and totalRevenue.
     * This defines a stronger notion of equality between two order items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MonthlyRevenue)) {
            return false;
        }

        MonthlyRevenue otherMonthlyRevenue = (MonthlyRevenue) other;
        return isSameMonthlyRevenue(otherMonthlyRevenue)
                && otherMonthlyRevenue.getTotalMonthlyRevenue() == getTotalMonthlyRevenue();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(month, year, totalMonthlyRevenue);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Total Revenue for ")
                .append(getMonth())
                .append(" ")
                .append(getYear())
                .append(" : $")
                .append(getTotalMonthlyRevenue());
        return builder.toString();
    }

}
