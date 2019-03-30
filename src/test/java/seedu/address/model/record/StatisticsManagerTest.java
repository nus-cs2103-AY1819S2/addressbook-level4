package seedu.address.model.record;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.medicine.Medicine;

class StatisticsManagerTest {
    private final StatisticsManager statisticsManager = new StatisticsManager();
    private Record record1;
    private Clock clock;

    @BeforeEach
    void setUp() {
        Medicine medicine = new Medicine("test");
        record1 = new MedicinePurchaseRecord(medicine, 1, BigDecimal.valueOf(10.00));
        clock = Clock.fixed(Instant.parse("2019-01-01T10:15:30.00Z"), ZoneId.systemDefault());
    }

    @Test
    void record() {
        statisticsManager.record(record1, clock);
    }

    @Test
    void getStatistics() {
        Statistics stats = new Statistics(0, BigDecimal.ZERO, BigDecimal.valueOf(10.00));
        statisticsManager.record(record1, clock);
        Statistics testStats = statisticsManager.getStatistics(YearMonth.of(2019, 1), YearMonth.of(2019, 1));
        Assert.assertEquals(testStats, stats);
    }
}
