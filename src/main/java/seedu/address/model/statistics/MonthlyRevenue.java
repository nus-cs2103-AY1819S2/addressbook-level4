package seedu.address.model.statistics;

/**
 * Monthly Revenue which contains the month year and total revenue earned on that specific month.
 */
public class MonthlyRevenue extends Revenue {

    /**
     * Every field must be present and not null.
     */
    public MonthlyRevenue(Month month, Year year, float totalMonthlyRevenue) {
        super(month, year, totalMonthlyRevenue);
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
     * This defines a stronger notion of equality between two monthlyRevenue.
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
                && otherMonthlyRevenue.getTotalRevenue() == getTotalRevenue();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Total Monthly Revenue for ")
                .append(getMonth())
                .append(" ")
                .append(getYear())
                .append(" : $")
                .append(getTotalRevenue());
        return builder.toString();
    }

}
