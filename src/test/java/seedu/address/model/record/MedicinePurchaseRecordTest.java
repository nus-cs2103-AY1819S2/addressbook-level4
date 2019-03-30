package seedu.address.model.record;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import seedu.address.model.medicine.Medicine;

class MedicinePurchaseRecordTest {

    @Test
    void toStatistics() {
        StatisticsManager statisticsManager = new StatisticsManager();
        Medicine medicine = new Medicine("test");
        medicine.setPrice(BigDecimal.valueOf(11.10));
        Statistics stats = new Statistics(0, BigDecimal.ZERO, BigDecimal.valueOf(44.40));
        MedicinePurchaseRecord mpr = new MedicinePurchaseRecord(medicine, 4);
        Assert.assertEquals(mpr.toStatistics(statisticsManager), stats);
    }
}
