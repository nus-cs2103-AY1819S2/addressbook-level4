package seedu.address.model.statistics;

/**
 * Daily Revenue which contains the day, month year and total revenue earned on that specific day.
 */
public class DailyRevenue extends Revenue {

    /**
     * Every field must be present and not null.
     */
    public DailyRevenue(Day day, Month month, Year year, float totalDailyRevenue) {
        super(day, month, year, totalDailyRevenue);
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
                && otherDailyRevenue.getTotalRevenue() == getTotalRevenue();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Total Daily Revenue for ")
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
