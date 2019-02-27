package seedu.address.model.record;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the statistics of a month, and the records of the month.
 */
public class MonthStatistics {
    private Statistics stats;
    private YearMonth date;
    private int recordPointer;
    private ArrayList<Record> records;

    public MonthStatistics(YearMonth date) {
        this.stats = new Statistics();
        this.date = date;
        this.recordPointer = 0;
        this.records = new ArrayList<>();
    }

    public Statistics getStatistics() {
        return this.stats;
    }

    /**
     * Updates the statistics of the current month to match the records in records.
     */
    private void updateStatistics() {
        if (recordPointer < records.size()) {
            Statistics subStats = Statistics.fromRecordList(this.records.subList(recordPointer, records.size()));
            this.stats = this.stats.merge(subStats);
        }
        recordPointer = records.size();
    }

    /**
     * Adds a record to the MonthStatistics, and updates the statistics.
     * @param record A single Record object
     */
    public void addRecord(Record record) {
        this.records.add(record);
        this.updateStatistics();
    }

    /**
     * Adds a list of records to the MonthStatistics, and updates the statistics.
     * @param records A list of Record objects
     */
    public void addRecord(List<Record> records) {
        this.records.addAll(records);
        this.updateStatistics();
    }
    @Override
    public String toString() {
        // TODO
        return super.toString();
    }
}
