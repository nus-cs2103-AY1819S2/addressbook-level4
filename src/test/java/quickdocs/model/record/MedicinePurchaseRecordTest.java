package quickdocs.model.record;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import quickdocs.model.medicine.Medicine;
import quickdocs.testutil.Assert;
import quickdocs.testutil.TypicalStatistics;

class MedicinePurchaseRecordTest {

    @Test
    void constructor_medicineNoPrice_throwsNullPointerException() {
        Medicine testMedicine = TypicalStatistics.SAMPLE_MEDICINE_DEXTROMETHORPHAN;
        Assert.assertThrows(NullPointerException.class, () -> new MedicinePurchaseRecord(testMedicine,
                1));
    }

    @Test
    void constructor_invalidCost_throwsIllegalArgumentException() {
        Medicine testMedicine = TypicalStatistics.SAMPLE_MEDICINE_DEXTROMETHORPHAN;
        Assert.assertThrows(IllegalArgumentException.class, () -> new MedicinePurchaseRecord(testMedicine,
                1, BigDecimal.valueOf(-1)));
    }

    @Test
    void constructor_invalidQuantity_throwsIllegalArgumentException() {
        Medicine testMedicine = TypicalStatistics.SAMPLE_MEDICINE_DEXTROMETHORPHAN;
        Assert.assertThrows(IllegalArgumentException.class, () -> new MedicinePurchaseRecord(testMedicine,
                -1, BigDecimal.ZERO));
    }

    @Test
    void toStatistics() {
        StatisticsManager statisticsManager = new StatisticsManager();
        MedicinePurchaseRecord mpr = TypicalStatistics.SAMPLE_RECORD_MPR_A;
        assertEquals(new Statistics(0, BigDecimal.ZERO, BigDecimal.valueOf(20)), mpr.toStatistics(statisticsManager));
    }
}
