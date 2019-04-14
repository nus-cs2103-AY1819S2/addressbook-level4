package quickdocs.model.record;

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
    public static final YearMonth START_DATE = YearMonth.of(2019, 1);

    private static final int NUMBER_OF_MONTHS_IN_A_YEAR = 12;

    private BigDecimal consultationFee;
    private List<MonthStatistics> monthStatistics;

    public StatisticsManager() {
        consultationFee = DEFAULT_CONSULTATION_FEE;
        monthStatistics = new ArrayList<>();
    }

    public BigDecimal getConsultationFee() {
        return this.consultationFee;
    }

    /**
     * Sets the consultation fee of the clinic. Must be a non-negative BigDecimal value.
     * @param cost A non-negative BigDecimal value
     */
    public void setConsultationFee(BigDecimal cost) {
        if (cost.compareTo(BigDecimal.ZERO) == -1) {
            throw new IllegalArgumentException("Consultation Fee cannot be a negative number");
        }
        consultationFee = cost;
    }

    /**
     * Adds the statistics of the record to the storage by merging, sorted to months.
     * @param record Record object for merging of statistics
     * @param clock Clock object to obtain the month and year of when the record is recorded.
     */
    public void record(Record record, Clock clock) {
        int idx = getYearMonthIndex(YearMonth.now(clock));
        if (idx < 0) {
            throw new IllegalArgumentException("System clock is before January 2019");
        }
        this.updateListSize(clock);
        this.monthStatistics.get(idx).addRecord(record, this);
    }
    private int getYearMonthIndex(YearMonth now) {
        return ((now.getYear() - START_DATE.getYear()) * NUMBER_OF_MONTHS_IN_A_YEAR)
                + (now.getMonthValue() - START_DATE.getMonthValue());
    }
    /**
     * Updates the ArrayList of Statistics to the proper size according to the current time.
     * @param clock Clock used to get the month and year from
     */
    private void updateListSize(Clock clock) {
        YearMonth now = YearMonth.now(clock);
        if (now.isBefore(START_DATE)) {
            throw new IllegalArgumentException("System clock is before January 2019");
        }
        updateListSize(now);
    }

    /**
     * Updates the ArrayList of Statistics to the proper size according to the yearMonth.
     */
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
        // check if the queried indexes are in range
        if (fromIdx < 0 || toIdx < 0 || fromIdx > monthStatistics.size() - 1 || toIdx > monthStatistics.size() - 1
            || toIdx < fromIdx) {
            throw new IllegalArgumentException("Invalid MMYY range");
        }
        for (int idx = fromIdx; idx <= toIdx; idx++) {
            stats = stats.merge(monthStatistics.get(idx).getStatistics());
        }
        return stats;
    }

    public List<MonthStatistics> getMonthStatisticsList() {
        return this.monthStatistics;
    }

    public int getMonthStatisticsListSize() {
        return this.monthStatistics.size();
    }

    /**
     * Adds a MonthStatistics object to the StatisticsManager if it does not exist
     */
    public void addMonthStatistics(MonthStatistics monthStatistics) {
        YearMonth yearMonth = monthStatistics.getYearMonth();
        YearMonth currentMonth = YearMonth.now(Clock.systemDefaultZone());
        if (yearMonth.isBefore(START_DATE) || yearMonth.isAfter(currentMonth)) {
            throw new IllegalArgumentException("Invalid stored statistics");
        }
        updateListSize(yearMonth);
        int idx = getYearMonthIndex(yearMonth);
        this.monthStatistics.set(idx, monthStatistics);
    }
}
