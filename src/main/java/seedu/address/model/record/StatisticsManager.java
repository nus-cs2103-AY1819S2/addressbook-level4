package seedu.address.model.record;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager for the Statistics objects, segmented in months
 */
public class StatisticsManager {
    public static final BigDecimal DEFAULT_CONSULTATION_FEE = BigDecimal.valueOf(30.00);
    private static final YearMonth START_DATE = YearMonth.of(2019, 1);
    private BigDecimal consultationFee;
    private List<MonthStatistics> monthStatistics;

    public StatisticsManager() {
        consultationFee = BigDecimal.valueOf(30.00);
        monthStatistics = new ArrayList<>();
    }
    public BigDecimal getConsultationFee() {
        return this.consultationFee;
    }
    public void setConsultationFee(BigDecimal cost) {
        consultationFee = cost;
    }

    /**
     * Adds the statistics of the record to the storage by merging, sorted to months.
     * @param record Record object for merging of statistics
     * @param clock Clock object to obtain the month and year of when the record is recorded.
     */
    public void record(Record record, Clock clock) {
        int idx = getYearMonthIndex(YearMonth.now(clock));
        this.updateListSize(clock);
        this.monthStatistics.get(idx).addRecord(record, this);
    }
    private int getYearMonthIndex(YearMonth now) {
        return ((now.getYear() - START_DATE.getYear()) * 12)
                + (now.getMonthValue() - START_DATE.getMonthValue());
    }
    /**
     * Updates the ArrayList of Statistics to the proper size according to the current time.
     * @param clock Clock used to get the month and year from
     */
    private void updateListSize(Clock clock) {
        YearMonth now = YearMonth.now(clock);
        updateListSize(now);
    }
    private void updateListSize(YearMonth yearMonth) {
        int expectedSize = getYearMonthIndex(yearMonth) + 1;
        int sizeDifference = expectedSize - monthStatistics.size();
        if (sizeDifference > 0) {
            for (int i = sizeDifference - 1; i >= 0; i--) {
                YearMonth toAdd = yearMonth.minusMonths((long) i);
                this.monthStatistics.add(new MonthStatistics(toAdd));
            }
        }
    }
    public Statistics getStatistics(YearMonth from, YearMonth to) {
        Statistics stats = new Statistics();
        int fromIdx = getYearMonthIndex(from);
        int toIdx = getYearMonthIndex(to);
        for (int idx = fromIdx; idx <= toIdx; idx++) {
            stats = stats.merge(monthStatistics.get(idx).getStatistics());
        }
        return stats;
    }
    public List<MonthStatistics> getMonthStatisticsList() {
        return this.monthStatistics;
    }

    /**
     * Adds a MonthStatistics object to the StatisticsManager if it does not exist
     */
    public void addMonthStatistics(MonthStatistics monthStatistics) {
        YearMonth yearMonth = monthStatistics.getYearMonth();
        updateListSize(yearMonth);
        int idx = getYearMonthIndex(yearMonth);
        this.monthStatistics.set(idx, monthStatistics);
    }
}
