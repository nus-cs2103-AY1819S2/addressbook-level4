package seedu.address.model.record;

import java.util.ArrayList;

/**
 * This class holds the records for a month, and the statistics relevant to it.
 */
public class MonthRecord {
    private Statistics stats;
    private int recordPointer;
    private ArrayList<Record> records;

    public MonthRecord() {
        this.stats = new Statistics();
        this.recordPointer = 0;
        this.records = new ArrayList<>();
    }

    public Statistics getStatistics() {
        return this.stats;
    }

    /**
     * Updates the statistics of the current month to match the records in records.
     */
    private void updateStatistics(RecordManager recordManager) {
        if (recordPointer < records.size()) {
            Statistics subStats = Statistics.fromRecordList(
                    this.records.subList(recordPointer, records.size()), recordManager);
            this.stats = this.stats.merge(subStats);
        }
        recordPointer = records.size();
    }

    /**
     * Adds a single record to the MonthRecord, and updates the statistics.
     * @param record A single Record object
     */
    public void addRecord(Record record, RecordManager recordManager) {
        this.records.add(record);
        this.updateStatistics(recordManager);
    }
    public int getNoOfRecords() {
        return this.records.size();
    }
}
