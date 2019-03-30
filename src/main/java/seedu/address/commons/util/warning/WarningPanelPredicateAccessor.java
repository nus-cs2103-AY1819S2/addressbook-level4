package seedu.address.commons.util.warning;

import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchExpiryThresholdPredicate;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.MedicineExpiryThresholdPredicate;
import seedu.address.model.medicine.MedicineLowStockThresholdPredicate;
import seedu.address.model.threshold.Threshold;

/**
 * Accessor to all predicates used in the warning panel for filtration.
 */
public class WarningPanelPredicateAccessor {
    private BatchExpiryThresholdPredicate batchExpiryPredicate;
    private MedicineExpiryThresholdPredicate medicineExpiryPredicate;
    private MedicineLowStockThresholdPredicate medicineLowStockPredicate;


    public WarningPanelPredicateAccessor() {
        setDefaultPredicates();
    }


    public Predicate<Batch> getBatchExpiryPredicate() {
        return this.batchExpiryPredicate;
    }

    public Predicate<Medicine> getMedicineExpiryPredicate() {
        return this.medicineExpiryPredicate;
    }

    public Predicate<Medicine> getMedicineLowStockPredicate() {
        return this.medicineLowStockPredicate;
    }

    public Threshold getExpiryThreshold() {
        return medicineExpiryPredicate.getThreshold();
    }

    public Threshold getLowStockThreshold() {
        return medicineLowStockPredicate.getThreshold();
    }

    public void setBatchExpiringThreshold(int threshold) {
        this.batchExpiryPredicate = new BatchExpiryThresholdPredicate(
            new Threshold(Integer.valueOf(threshold)));
    }

    public void setMedicineExpiringThreshold(int threshold) {
        this.medicineExpiryPredicate = new MedicineExpiryThresholdPredicate(
            new Threshold(Integer.valueOf(threshold)));
    }

    public void setMedicineLowStockThreshold(int threshold) {
        this.medicineLowStockPredicate = new MedicineLowStockThresholdPredicate(
            new Threshold(Integer.valueOf(threshold)));
    }

    private void setDefaultPredicates() {
        this.batchExpiryPredicate = new BatchExpiryThresholdPredicate(Model.DEFAULT_EXPIRY_THRESHOLD);
        this.medicineExpiryPredicate = new MedicineExpiryThresholdPredicate(Model.DEFAULT_EXPIRY_THRESHOLD);
        this.medicineLowStockPredicate = new MedicineLowStockThresholdPredicate(Model.DEFAULT_LOW_STOCK_THRESHOLD);
    }
}

