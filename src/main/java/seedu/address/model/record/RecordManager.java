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
    public static final YearMonth START_DATE = YearMonth.of(2019, 1);
    private List<MonthRecord> monthRecords;

    public RecordManager() {
        monthRecords = new ArrayList<>();
    }
    public RecordManager(RecordManager toBeCopied) { monthRecords = toBeCopied.copyRecords(); }
    public List<MonthRecord> copyRecords() {
        return this.monthRecords;
    }

    /**
     * Records the record into the system records, according to months.
     * @param record
     * @param clock
     */
    public void record(Record record, Clock clock) {
        int idx = getYearMonthIndex(YearMonth.now(clock));
        this.updateListSize(clock);
        MonthRecord monthRecord = monthRecords.get(idx);
        monthRecord.addRecord(record);
    }
    private int getYearMonthIndex(YearMonth now) {
        return ((now.getYear() - START_DATE.getYear()) * 12)
                + (now.getMonthValue() - START_DATE.getMonthValue());
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
    public Statistics getStatisticOfYearMonth(YearMonth yearMonth) {
        MonthRecord monthRecord = monthRecords.get(getYearMonthIndex(yearMonth));
        return monthRecord.getStatistics();
    }
    public Statistics getStatistics(String topic, YearMonth from, YearMonth to) {
        switch (topic) {
        case "finances":
            return new Statistics();
        case "consultations":
            return new Statistics();
        case "all":
        default:
            return new Statistics();
        }
        // TODO
    }
    public void setConsultationFee(BigDecimal cost) {
        Statistics.setConsultationFee(cost);
    }
}
