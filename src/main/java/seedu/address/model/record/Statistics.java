package seedu.address.model.record;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

/**
 * This class holds the statistics relevant to the private clinic's needs
 * A Statistics object is immutable.
 */
public class Statistics {
    private static BigDecimal consultationFee = BigDecimal.valueOf(30.00);
    private int noOfConsultations;
    private BigDecimal revenue;
    private BigDecimal expenditure;
    private BigDecimal profit;
    public Statistics() {
        this.noOfConsultations = 0;
        this.revenue = BigDecimal.ZERO;
        this.expenditure = BigDecimal.ZERO;
        this.profit = BigDecimal.ZERO;
    }
    public Statistics(int noOfConsultations, BigDecimal revenue, BigDecimal expenditure) {
        this.noOfConsultations = noOfConsultations;
        this.revenue = revenue;
        this.expenditure = expenditure;
        this.profit = revenue.subtract(expenditure);
    }
    public Statistics(Statistics stats) {
        this.noOfConsultations = stats.getNoOfConsultations();
        this.revenue = stats.getRevenue();
        this.expenditure = stats.getExpenditure();
        this.profit = stats.getProfit();
    }
    public int getNoOfConsultations() {
        return noOfConsultations;
    }
    public BigDecimal getRevenue() {
        return revenue;
    }
    public BigDecimal getExpenditure() {
        return expenditure;
    }
    public BigDecimal getProfit() {
        return profit;
    }

    /**
     * Merges this Statistics object with another Statistic object
     * @param other Statistics to merge with
     * @return A new Statistics object
     */
    public Statistics merge(Statistics other) {
        return new Statistics(
                this.getNoOfConsultations() + other.getNoOfConsultations(),
                this.getRevenue().add(other.getRevenue()),
                this.getExpenditure().add(other.getExpenditure()));
    }

    /**
     * Calculates the statistics of a list of records
     * @param records A list of Record objects
     * @return A new Statistics object
     */
    public static Statistics fromRecordList(List<Record> records) {
        Statistics stats = new Statistics();
        for (int i = 0; i < records.size(); i++) {
            stats = stats.merge(records.get(i).toStatistics());
        }
        return stats;
    }
    public static BigDecimal getConsultationFee() {
        return Statistics.consultationFee;
    }
    public static void setConsultationFee(BigDecimal cost) {
        Statistics.consultationFee = cost;
    }

    /**
     * Function to format BigDecimal objects to the locale's currency format.
     * @param money BigDecimal object representing any amount of money.
     * @return A String representation of the money formatted to the locale's currency format.
     */
    public static String currencyFormat(BigDecimal money) {
        /**
         * Adapted from https://stackoverflow.com/questions/3395825/how-to-print-formatted-bigdecimal-values/8581941
         */
        return NumberFormat.getCurrencyInstance().format(money);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of consultations: ")
                .append(getNoOfConsultations())
                .append("\n")
                .append("Revenue: ")
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
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Statistics)) {
            return false;
        }
        Statistics stats = (Statistics) other;
        return this.getNoOfConsultations() == stats.getNoOfConsultations()
                && this.getRevenue().equals(stats.getRevenue())
                && this.getExpenditure().equals(stats.getExpenditure())
                && this.getProfit().equals(stats.getProfit());
    }
}
