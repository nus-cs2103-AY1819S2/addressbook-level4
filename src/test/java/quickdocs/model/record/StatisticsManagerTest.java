package quickdocs.model.record;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import quickdocs.testutil.Assert;
import quickdocs.testutil.TypicalStatistics;

class StatisticsManagerTest {
    private static StatisticsManager statisticsManager;
    private Record record1;
    private Clock clock;

    @BeforeEach
    void setUp() {
        statisticsManager = new StatisticsManager();
    }

    @Test
    void setAndGetConsultationFee_positiveFee_success() {
        statisticsManager.setConsultationFee(BigDecimal.valueOf(40));
        assertEquals(BigDecimal.valueOf(40), statisticsManager.getConsultationFee());
    }

    @Test
    void setAndGetConsultationFee_zeroFee_success() {
        statisticsManager.setConsultationFee(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, statisticsManager.getConsultationFee());
    }

    @Test
    void setConsultationFee_negativeFee_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> statisticsManager
                .setConsultationFee(BigDecimal.valueOf(-1)));
    }

    @Test
    void recordAndGetStatistics_validClock_success() {
        Statistics expectedStats = new Statistics(0, BigDecimal.ZERO, BigDecimal.valueOf(20.00));
        clock = Clock.fixed(Instant.parse("2019-01-01T10:15:30.00Z"), ZoneId.systemDefault());
        record1 = TypicalStatistics.SAMPLE_RECORD_MPR_A;
        statisticsManager.record(record1, clock);
        Statistics testStats = statisticsManager.getStatistics(YearMonth.of(2019, 1), YearMonth.of(2019, 1));
        assertEquals(expectedStats, testStats);
    }

    @Test
    void recordAndGetStatistics_invalidClock_throwsIllegalArgumentException() {
        clock = Clock.fixed(Instant.parse("2018-12-01T10:15:30.00Z"), ZoneId.systemDefault());
        record1 = TypicalStatistics.SAMPLE_RECORD_MPR_A;
        Assert.assertThrows(IllegalArgumentException.class, () -> statisticsManager
            .record(record1, clock));
    }

    @Test
    void getStatistics_yearMonthBeforeJan2019_throwsIllegalArgumentException() {
        clock = Clock.fixed(Instant.parse("2019-12-01T10:15:30.00Z"), ZoneId.systemDefault());
        record1 = TypicalStatistics.SAMPLE_RECORD_MPR_A;
        statisticsManager.record(record1, clock);
        Assert.assertThrows(IllegalArgumentException.class, () -> statisticsManager
                .getStatistics(YearMonth.of(2018, 12), YearMonth.of(2018, 12)));
    }

    @Test
    void getStatistics_yearMonthAfterCurrentClock_throwsIllegalArgumentException() {
        clock = Clock.fixed(Instant.parse("2019-12-01T10:15:30.00Z"), ZoneId.systemDefault());
        record1 = TypicalStatistics.SAMPLE_RECORD_MPR_A;
        statisticsManager.record(record1, clock);
        Assert.assertThrows(IllegalArgumentException.class, () -> statisticsManager
                .getStatistics(YearMonth.of(2020, 1), YearMonth.of(2020, 1)));
    }

    @Test
    void getStatistics_fromYearMonthAfterToYearMonth_throwsIllegalArgumentException() {
        clock = Clock.fixed(Instant.parse("2019-12-01T10:15:30.00Z"), ZoneId.systemDefault());
        record1 = TypicalStatistics.SAMPLE_RECORD_MPR_A;
        statisticsManager.record(record1, clock);
        Assert.assertThrows(IllegalArgumentException.class, () -> statisticsManager
                .getStatistics(YearMonth.of(2019, 12), YearMonth.of(2019, 1)));
    }

    @Test
    void updateListSize_validClock_success() {
        clock = Clock.fixed(Instant.parse("2019-04-01T10:15:30.00Z"), ZoneId.systemDefault());
        record1 = TypicalStatistics.SAMPLE_RECORD_MPR_A;
        statisticsManager.record(record1, clock);
        assertEquals(4, statisticsManager.getMonthStatisticsListSize());
    }

    @Test
    void updateListSize_invalidClock_success() {
        clock = Clock.fixed(Instant.parse("2000-04-01T10:15:30.00Z"), ZoneId.systemDefault());
        record1 = TypicalStatistics.SAMPLE_RECORD_MPR_A;
        Assert.assertThrows(IllegalArgumentException.class, () -> statisticsManager
                .record(record1, clock));
    }

    @Test
    void addMonthStatistics_validMonthStatistics_success() {
        YearMonth yearmonth = YearMonth.of(2019, 1);
        MonthStatistics monthStatistics = new MonthStatistics(yearmonth);
        statisticsManager.addMonthStatistics(monthStatistics);
        assertEquals(1, statisticsManager.getMonthStatisticsListSize());
    }

    @Test
    void addMonthStatistics_invalidMonthStatistics_throwsIllegalArgumentException() {
        YearMonth yearmonth = YearMonth.of(2018, 1);
        MonthStatistics monthStatistics = new MonthStatistics(yearmonth);
        Assert.assertThrows(IllegalArgumentException.class, () -> statisticsManager
                .addMonthStatistics(monthStatistics));
    }
}
