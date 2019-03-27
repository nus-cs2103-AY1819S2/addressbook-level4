package seedu.address.commons.util.warning;

import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchExpiryThresholdPredicate;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.MedicineExpiryThresholdPredicate;
import seedu.address.model.medicine.MedicineLowStockThresholdPredicate;

/**
 * Accessor to all predicates used in the warning panel for filtration.
 */
public class WarningPanelPredicateAccessor {
    private BatchExpiryThresholdPredicate batchExpiringPredicate;
    private MedicineExpiryThresholdPredicate medicineExpiringPredicate;
    private MedicineLowStockThresholdPredicate medicineLowStockPredicate;


    public WarningPanelPredicateAccessor() {
        setDefaultPredicates();
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

    private void setDefaultPredicates() {
        this.batchExpiringPredicate = new BatchExpiryThresholdPredicate(Model.DEFAULT_EXPIRY_THRESHOLD);
        this.medicineExpiringPredicate = new MedicineExpiryThresholdPredicate(Model.DEFAULT_EXPIRY_THRESHOLD);
        this.medicineLowStockPredicate = new MedicineLowStockThresholdPredicate(Model.DEFAULT_LOW_STOCK_THRESHOLD);
    }
}

