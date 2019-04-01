package seedu.address.model.statistics;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Daily Revenue which contains the day, month year and total revenue earned on that specific day.
 */
public class DailyRevenue {

    private final Day day;
    private final Month month;
    private final Year year;
    private float totalDailyRevenue;

    /**
     * Every field must be present and not null.
     */
    public DailyRevenue(Day day, Month month, Year year, float totalDailyRevenue) {
        requireAllNonNull(day, month, year, totalDailyRevenue);
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalDailyRevenue = totalDailyRevenue;
    }

    /**
     * Creates a daily revenue that accepts null as it's field.
     * Mainly used for revenueCommand
     */
    public DailyRevenue(Day day, Month month, Year year) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalDailyRevenue = 0;
    }

    /**
     * Adds the bill's total bill into the revenue.
     */
    public void addToRevenue(Bill bill) {
        totalDailyRevenue += bill.getTotalBill();
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
    public float getTotalDailyRevenue() {
        return totalDailyRevenue;
    }

    /**
     * Returns true if both dailyRevenue have the same day, month and year.
     * This defines a weaker notion of equality between two dailyRevenue.
     */
    public boolean isSameDailyRevenue(DailyRevenue otherDailyRevenue) {
        if (otherDailyRevenue == this) {
            return true;
        }

        return otherDailyRevenue != null && otherDailyRevenue.getDay().equals(getDay()) && otherDailyRevenue
                .getMonth().equals(getMonth()) && otherDailyRevenue.getYear().equals(getYear());
    }

    /**
     * Returns true if both dailyRevenue have the same day, month, year and totalRevenue.
     * This defines a stronger notion of equality between two order items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DailyRevenue)) {
            return false;
        }

        DailyRevenue otherDailyRevenue = (DailyRevenue) other;
        return isSameDailyRevenue(otherDailyRevenue)
                && otherDailyRevenue.getTotalDailyRevenue() == getTotalDailyRevenue();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(day, month, year, totalDailyRevenue);
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
                .append(getTotalDailyRevenue());
        return builder.toString();
    }

}
