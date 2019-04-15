package seedu.address.commons.util.warning;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.medicine.predicates.BatchExpiryThresholdPredicate;
import seedu.address.model.medicine.predicates.MedicineExpiryThresholdPredicate;
import seedu.address.model.medicine.predicates.MedicineLowStockThresholdPredicate;
import seedu.address.model.threshold.Threshold;

public class WarningPanelPredicateAccessorTest {

    @Test
    public void equals() {
        WarningPanelPredicateAccessor firstAccessor = new WarningPanelPredicateAccessor();
        WarningPanelPredicateAccessor secondAccessor = new WarningPanelPredicateAccessor();
        secondAccessor.updateBatchExpiringThreshold(0);

        // same object -> returns true
        assertTrue(firstAccessor.equals(firstAccessor));

        // same values -> returns true
        WarningPanelPredicateAccessor firstAccessorCopy = new WarningPanelPredicateAccessor();
        assertTrue(firstAccessor.equals(firstAccessorCopy));

        // different types -> returns false
        assertFalse(firstAccessor.equals(1));

        // null -> returns false
        assertFalse(firstAccessor.equals(null));

        // different accessor -> returns false
        assertFalse(firstAccessor.equals(secondAccessor));
    }

    @Test
    public void setDefaultPredicates_returnsTrue() {
        WarningPanelPredicateAccessor accessor = new WarningPanelPredicateAccessor();

        BatchExpiryThresholdPredicate batchExpiryPredicate;
        MedicineExpiryThresholdPredicate medicineExpiryPredicate;
        MedicineLowStockThresholdPredicate medicineLowStockPredicate;

        // same default BatchExpiryPredicate -> returns true
        batchExpiryPredicate = new BatchExpiryThresholdPredicate(Model.DEFAULT_EXPIRY_THRESHOLD);
        assertTrue(accessor.getBatchExpiryPredicate().equals(batchExpiryPredicate));

        // same default MedicineExpiryPredicate -> returns true
        medicineExpiryPredicate = new MedicineExpiryThresholdPredicate(Model.DEFAULT_EXPIRY_THRESHOLD);
        assertTrue(accessor.getMedicineExpiryPredicate().equals(medicineExpiryPredicate));

        // same default MedicineLowStockPredicate -> returns true
        medicineLowStockPredicate = new MedicineLowStockThresholdPredicate(Model.DEFAULT_LOW_STOCK_THRESHOLD);
        assertTrue(accessor.getMedicineLowStockPredicate().equals(medicineLowStockPredicate));
    }

    @Test
    public void setDefaultPredicates_returnsFalse() {
        WarningPanelPredicateAccessor accessor = new WarningPanelPredicateAccessor();
        Threshold otherExpiryThreshold = new Threshold("0", WarningPanelPredicateType.EXPIRY);
        Threshold otherLowStockThreshold = new Threshold("0", WarningPanelPredicateType.LOW_STOCK);

        BatchExpiryThresholdPredicate batchExpiryPredicate;
        MedicineExpiryThresholdPredicate medicineExpiryPredicate;
        MedicineLowStockThresholdPredicate medicineLowStockPredicate;

        // different BatchExpiryPredicate -> returns false
        batchExpiryPredicate = new BatchExpiryThresholdPredicate(otherExpiryThreshold);
        assertFalse(accessor.getBatchExpiryPredicate().equals(batchExpiryPredicate));

        // different MedicineExpiryPredicate -> returns false
        medicineExpiryPredicate = new MedicineExpiryThresholdPredicate(otherExpiryThreshold);
        assertFalse(accessor.getMedicineExpiryPredicate().equals(medicineExpiryPredicate));

        // different MedicineLowStockPredicate -> returns false
        medicineLowStockPredicate = new MedicineLowStockThresholdPredicate(otherLowStockThreshold);
        assertFalse(accessor.getMedicineLowStockPredicate().equals(medicineLowStockPredicate));
    }

    @Test
    public void setBatchExpiringThreshold() {
        WarningPanelPredicateAccessor accessor = new WarningPanelPredicateAccessor();

        Threshold defaultThreshold = Model.DEFAULT_EXPIRY_THRESHOLD;
        Threshold otherThreshold = new Threshold("0", WarningPanelPredicateType.EXPIRY);

        BatchExpiryThresholdPredicate defaultPredicate = new BatchExpiryThresholdPredicate(defaultThreshold);
        BatchExpiryThresholdPredicate otherPredicate = new BatchExpiryThresholdPredicate(otherThreshold);

        // same predicate -> returns true
        assertTrue(accessor.getBatchExpiryPredicate().equals(defaultPredicate));

        // different predicate -> returns false
        assertFalse(accessor.getBatchExpiryPredicate().equals(otherPredicate));

        // set new threshold
        accessor.updateBatchExpiringThreshold(otherThreshold.getNumericValue());

        // same predicate -> returns true
        assertTrue(accessor.getBatchExpiryPredicate().equals(otherPredicate));

        // different predicate -> returns false
        assertFalse(accessor.getBatchExpiryPredicate().equals(defaultPredicate));
    }

    @Test
    public void setMedicineExpiringThreshold() {
        WarningPanelPredicateAccessor accessor = new WarningPanelPredicateAccessor();

        Threshold defaultThreshold = Model.DEFAULT_EXPIRY_THRESHOLD;
        Threshold otherThreshold = new Threshold("0", WarningPanelPredicateType.EXPIRY);

        MedicineExpiryThresholdPredicate defaultPredicate = new MedicineExpiryThresholdPredicate(defaultThreshold);
        MedicineExpiryThresholdPredicate otherPredicate = new MedicineExpiryThresholdPredicate(otherThreshold);

        // same predicate -> returns true
        assertTrue(accessor.getMedicineExpiryPredicate().equals(defaultPredicate));

        // different predicate -> returns false
        assertFalse(accessor.getMedicineExpiryPredicate().equals(otherPredicate));

        // set new threshold
        accessor.updateMedicineExpiringThreshold(otherThreshold.getNumericValue());

        // same predicate -> returns true
        assertTrue(accessor.getMedicineExpiryPredicate().equals(otherPredicate));

        // different predicate -> returns false
        assertFalse(accessor.getMedicineExpiryPredicate().equals(defaultPredicate));
    }

    @Test
    public void setMedicineLowStockThreshold() {
        WarningPanelPredicateAccessor accessor = new WarningPanelPredicateAccessor();

        Threshold defaultThreshold = Model.DEFAULT_LOW_STOCK_THRESHOLD;
        Threshold otherThreshold = new Threshold("0", WarningPanelPredicateType.LOW_STOCK);

        MedicineLowStockThresholdPredicate defaultPredicate = new MedicineLowStockThresholdPredicate(defaultThreshold);
        MedicineLowStockThresholdPredicate otherPredicate = new MedicineLowStockThresholdPredicate(otherThreshold);

        // same predicate -> returns true
        assertTrue(accessor.getMedicineLowStockPredicate().equals(defaultPredicate));

        // different predicate -> returns false
        assertFalse(accessor.getMedicineLowStockPredicate().equals(otherPredicate));

        // set new threshold
        accessor.updateMedicineLowStockThreshold(otherThreshold.getNumericValue());

        // same predicate -> returns true
        assertTrue(accessor.getMedicineLowStockPredicate().equals(otherPredicate));

        // different predicate -> returns false
        assertFalse(accessor.getMedicineLowStockPredicate().equals(defaultPredicate));
    }
}
