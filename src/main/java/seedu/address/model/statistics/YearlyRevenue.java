package seedu.address.model.statistics;

/**
 * Yearly Revenue which contains the year and total revenue earned on that specific year.
 */
public class YearlyRevenue extends Revenue {

    /**
     * Every field must be present and not null.
     */
    public YearlyRevenue(Year year, float totalYearlyRevenue) {
        super(year, totalYearlyRevenue);
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
     * This defines a stronger notion of equality between two yearlyRevenue.
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
                && otherYearlyRevenue.getTotalRevenue() == getTotalRevenue();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Total Yearly Revenue for ")
                .append(getYear())
                .append(" : $")
                .append(getTotalRevenue());
        return builder.toString();
    }

}
