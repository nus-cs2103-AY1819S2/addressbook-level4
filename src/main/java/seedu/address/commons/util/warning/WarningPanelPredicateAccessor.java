package seedu.address.commons.util.warning;

import java.util.function.Predicate;

import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchExpiryThresholdPredicate;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.MedicineExpiryThresholdPredicate;
import seedu.address.model.medicine.MedicineLowStockThresholdPredicate;

/**
 * Accessor to all predicates used in the warning panel for filtration.
 */
public class WarningPanelPredicateAccessor {
    private static final int DEFAULT_EXPIRY_THRESHOLD = 10;
    private static final int DEFAULT_LOW_STOCK_THRESHOLD = 20;

    private BatchExpiryThresholdPredicate batchExpiringPredicate;
    private MedicineExpiryThresholdPredicate medicineExpiringPredicate;
    private MedicineLowStockThresholdPredicate medicineLowStockPredicate;


    public WarningPanelPredicateAccessor() {
        this.batchExpiringPredicate = new BatchExpiryThresholdPredicate(DEFAULT_EXPIRY_THRESHOLD);
        this.medicineExpiringPredicate = new MedicineExpiryThresholdPredicate(DEFAULT_EXPIRY_THRESHOLD);
        this.medicineLowStockPredicate = new MedicineLowStockThresholdPredicate(DEFAULT_LOW_STOCK_THRESHOLD);
    }


    public Predicate<Batch> getBatchExpiringPredicate() {
        return this.batchExpiringPredicate;
    }

    public Predicate<Medicine> getMedicineExpiringPredicate() {
        return this.medicineExpiringPredicate;
    }

    public Predicate<Medicine> getMedicineLowStockPredicate() {
        return this.medicineLowStockPredicate;
    }

    public int getExpiryThreshold() {
        return medicineExpiringPredicate.getThreshold();
    }

    public int getLowStockThreshold() {
        return medicineLowStockPredicate.getThreshold();
    }

    public void setBatchExpiringThreshold(int threshold) {
        this.batchExpiringPredicate = new BatchExpiryThresholdPredicate(threshold);
    }

    public void setMedicineExpiringThreshold(int threshold) {
        this.medicineExpiringPredicate = new MedicineExpiryThresholdPredicate(threshold);
    }

    public void setMedicinelowStockThreshold(int threshold) {
        this.medicineLowStockPredicate = new MedicineLowStockThresholdPredicate(threshold);
    }
}

