package seedu.address.model.record;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MedicinePurchaseRecordTest {

    @Test
    void toStatistics() {
        Statistics stats = new Statistics(0, BigDecimal.ZERO, BigDecimal.valueOf(44.40));
        MedicinePurchaseRecord mpr = new MedicinePurchaseRecord("test", 4, BigDecimal.valueOf(11.10));
        Assert.assertEquals(mpr.toStatistics(), stats);
    }
}
