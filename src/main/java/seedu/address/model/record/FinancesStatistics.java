package seedu.address.model.record;

/**
 * Subclass of Statistics for
 */
public class FinancesStatistics extends Statistics {
    public FinancesStatistics(Statistics stats) {
        super(stats);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Revenue: ")
                .append(Statistics.currencyFormat(getRevenue()))
                .append("\n")
                .append("Expenditure: ")
                .append(Statistics.currencyFormat(getExpenditure()))
                .append("\n")
                .append("Profit: ")
                .append(Statistics.currencyFormat(getProfit()))
                .append("\n\n");
        return sb.toString();
    }
}
