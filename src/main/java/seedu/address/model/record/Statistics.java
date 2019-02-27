package seedu.address.model.record;

import java.util.List;

/**
 * This class holds the statistics relevant to the private clinic's needs
 * A Statistics object is immutable.
 */
public class Statistics {
    private int noOfConsultations;
    private int revenue;
    private int expenditure;
    private int profit;
    public Statistics() {
        this.noOfConsultations = 0;
        this.revenue = 0;
        this.expenditure = 0;
        this.profit = 0;
    }
    public Statistics(int noOfConsultations, int revenue, int expenditure, int profit) {
        this.noOfConsultations = noOfConsultations;
        this.revenue = revenue;
        this.expenditure = expenditure;
        this.profit = profit;
    }
    public int getNoOfConsultations() {
        return noOfConsultations;
    }
    public int getRevenue() {
        return revenue;
    }
    public int getExpenditure() {
        return expenditure;
    }
    public int getProfit() {
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
                this.getRevenue() + other.getRevenue(),
                this.getExpenditure() + other.getExpenditure(),
                this.getProfit() + other.getProfit());
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

    /**
     * Returns a new Statistics object from a ConsultationRecord
     * @return A new Statistics object
     */
    public static Statistics fromConsultationRecord(ConsultationRecord consultationRecord) {
        return new Statistics();
        // TODO
    }
    /**
     * Returns a new Statistics object from a MedicineRecord
     * @return A new Statistics object
     */
    public static Statistics fromMedicineRecord(MedicineRecord medicineRecord) {
        return new Statistics();
        // TODO
    }
}
