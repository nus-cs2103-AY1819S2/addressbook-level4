package seedu.address.model.record;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager for the Record objects, segmented in months
 */
public class RecordManager {
    private static final YearMonth START_DATE = YearMonth.of(2019, 1);
    private List<MonthRecord> monthRecords;
    private int totalNoOfRecords;

    public RecordManager() {
        monthRecords = new ArrayList<>();
        totalNoOfRecords = 0;
    }
    public RecordManager(RecordManager toBeCopied) {
        monthRecords = toBeCopied.copyRecords();
        totalNoOfRecords = toBeCopied.getTotalNoOfRecords();
    }
    private List<MonthRecord> copyRecords() {
        return this.monthRecords;
    }

    /**
     * Records the record into the system records, according to months.
     * @param record Record object for storage
     * @param clock Clock object to obtain the month and year of when the record is recorded.
     */
    public void record(Record record, Clock clock) {
        int idx = getYearMonthIndex(YearMonth.now(clock));
        this.updateListSize(clock);
        MonthRecord monthRecord = monthRecords.get(idx);
        monthRecord.addRecord(record);
        totalNoOfRecords++;
    }
    private int getYearMonthIndex(YearMonth now) {
        return ((now.getYear() - START_DATE.getYear()) * 12)
                + (now.getMonthValue() - START_DATE.getMonthValue());
    }
    public int getTotalNoOfRecords() {
        return totalNoOfRecords;
    }

    /**
     * Updates the ArrayList of MonthRecord to the proper size according to the current time.
     * @param clock Clock used to get the month and year from
     */
    private void updateListSize(Clock clock) {
        YearMonth now = YearMonth.now(clock);
        int expectedSize = getYearMonthIndex(now) + 1;
        int sizeDifference = expectedSize - monthRecords.size();
        if (sizeDifference > 0) {
            for (int i = sizeDifference - 1; i >= 0; i--) {
                YearMonth toAdd = now.minusMonths((long) 1);
                this.monthRecords.add(new MonthRecord(toAdd));
            }
        }
    }
    public Statistics getStatistics(String topic, YearMonth from, YearMonth to) {
        Statistics stats = new Statistics();
        int fromIdx = getYearMonthIndex(from);
        int toIdx = getYearMonthIndex(to);
        for (int idx = fromIdx; idx <= toIdx; idx++) {
            stats = stats.merge(monthRecords.get(idx).getStatistics());
        }
        Statistics toReturn;
        switch (topic) {
        case "finances":
            toReturn = new FinancesStatistics(stats);
            break;
        case "consultations":
            toReturn = new ConsultationStatistics(stats);
            break;
        case "all":
        default:
            toReturn = stats;
        }
        return toReturn;
    }
    public void setConsultationFee(BigDecimal cost) {
        Statistics.setConsultationFee(cost);
    }
}
