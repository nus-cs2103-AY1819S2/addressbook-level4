package seedu.address.model.statistics;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Revenue which contains the total revenue earned on that specific day.
 */
public class Revenue {

    private final Day day;
    private final Month month;
    private final Year year;
    private float totalRevenue;

    /**
     * Every field must be present and not null.
     */
    public Revenue(Day day, Month month, Year year, float totalRevenue) {
        requireAllNonNull(day, month, year, totalRevenue);
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalRevenue = totalRevenue;
    }

    /**
     * Creates a monthly revenue
     * Every parameters must be present and not null.
     */
    public Revenue(Month month, Year year, float totalRevenue) {
        requireAllNonNull(month, year, totalRevenue);
        this.day = null;
        this.month = month;
        this.year = year;
        this.totalRevenue = totalRevenue;
    }

    /**
     * Creates a yearly revenue
     * Every parameters must be present and not null.
     */
    public Revenue(Year year, float totalRevenue) {
        requireAllNonNull(year, totalRevenue);
        this.day = null;
        this.month = null;
        this.year = year;
        this.totalRevenue = totalRevenue;
    }

    /**
     * Creates a revenue that accepts null as it's field.
     * Mainly used for revenueCommand
     */
    public Revenue(Day day, Month month, Year year) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalRevenue = 0;
    }

    /**
     * Gets the day.
     */
    public Day getDay() {
        return day;
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
     * Gets the total revenue for the day.
     */
    public float getTotalRevenue() {
        return totalRevenue;
    }

    /**
     * Adds the float earnings to into the revenue.
     */
    public void addToRevenue(float earnings) {
        totalRevenue += earnings;
    }

    /**
     * Returns true if both Revenue have the same day, month and year.
     * This defines a weaker notion of equality between two Revenue.
     */

    public boolean isSameRevenue(Revenue otherRevenue) {
        if (otherRevenue == this) {
            return true;
        }

        return otherRevenue != null && otherRevenue.getDay().equals(getDay()) && otherRevenue
                .getMonth().equals(getMonth()) && otherRevenue.getYear().equals(getYear());
    }

    /**
     * Returns true if both Revenue have the same day, month, year and totalRevenue.
     * This defines a stronger notion of equality between two order items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Revenue)) {
            return false;
        }

        Revenue otherRevenue = (Revenue) other;
        return isSameRevenue(otherRevenue)
                && otherRevenue.getTotalRevenue() == getTotalRevenue();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(day, month, year, totalRevenue);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Total Revenue for ")
                .append(getDay())
                .append(".")
                .append(getMonth())
                .append(".")
                .append(getYear())
                .append(" : $")
                .append(getTotalRevenue());
        return builder.toString();
    }

}
