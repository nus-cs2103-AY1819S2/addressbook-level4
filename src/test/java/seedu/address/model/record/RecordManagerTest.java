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

class RecordManagerTest {
    private final RecordManager recordManager = new RecordManager();
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
        recordManager.record(record1, clock);
        Assert.assertEquals(recordManager.getTotalNoOfRecords(), 1);
    }

    @Test
    void getStatistics() {
        Statistics stats = new Statistics(0, BigDecimal.ZERO, BigDecimal.valueOf(10.00));
        recordManager.record(record1, clock);
        Statistics testStats = recordManager.getStatistics(YearMonth.of(2019, 1), YearMonth.of(2019, 1));
        Assert.assertEquals(testStats, stats);
    }
}
