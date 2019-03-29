package seedu.address.model.record;

import java.time.YearMonth;

/**
 * This class holds the Statistics for a specified month
 */
public class MonthStatistics {
    private YearMonth yearMonth;
    private Statistics stats;

    public MonthStatistics() {
    }
    public MonthStatistics(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
        this.stats = new Statistics();
    }
    public MonthStatistics(YearMonth yearMonth, Statistics stats) {
        this.yearMonth = yearMonth;
        this.stats = stats;
    }

    /**
     * Merges the statistics of a single Record object to the MonthStatistics
     */
    public void addRecord(Record record, StatisticsManager statisticsManager) {
        Statistics toMerge = record.toStatistics(statisticsManager);
        this.stats = this.stats.merge(toMerge);
    }
    public Statistics getStatistics() {
        return this.stats;
    }
    public YearMonth getYearMonth() {
        return this.yearMonth;
    }
}
