package seedu.address.model.record;

import java.math.BigDecimal;
import java.util.List;

/**
 * This class holds the statistics relevant to the private clinic's needs
 * A Statistics object is immutable.
 */
public class Statistics {
    private static BigDecimal consultationFee = BigDecimal.valueOf(30);
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
    @Override
    public String toString() {
        return super.toString(); // TODO
    }
}
