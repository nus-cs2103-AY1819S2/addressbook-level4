package seedu.address.commons.util.warning;

import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.predicates.BatchExpiryThresholdPredicate;
import seedu.address.model.medicine.predicates.MedicineExpiryThresholdPredicate;
import seedu.address.model.medicine.predicates.MedicineLowStockThresholdPredicate;
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

    /**
     * Updates {@code Batch}'s expiration threshold.
     * @param threshold
     */
    public void updateBatchExpiringThreshold(int threshold) {
        this.batchExpiryPredicate = new BatchExpiryThresholdPredicate(
            new Threshold(Integer.toString(threshold), WarningPanelPredicateType.EXPIRY));
    }

    /**
     * Updates {@code Medicine}'s expiration threshold.
     * @param threshold
     */
    public void updateMedicineExpiringThreshold(int threshold) {
        this.medicineExpiryPredicate = new MedicineExpiryThresholdPredicate(
            new Threshold(Integer.toString(threshold), WarningPanelPredicateType.EXPIRY));
    }

    /**
     * Updates {@code Medicine}'s low stock threshold.
     * @param threshold
     */
    public void updateMedicineLowStockThreshold(int threshold) {
        this.medicineLowStockPredicate = new MedicineLowStockThresholdPredicate(
            new Threshold(Integer.toString(threshold), WarningPanelPredicateType.LOW_STOCK));
    }

    /**
     * Set max threshold such that all predicates always return true.
     */
    public void setMaxThresholds() {
        updateBatchExpiringThreshold(Threshold.MAX_EXPIRY_THRESHOLD);
        updateMedicineExpiringThreshold(Threshold.MAX_EXPIRY_THRESHOLD);
        updateMedicineLowStockThreshold(Threshold.MAX_QUANTITY_THRESHOLD);
    }

    private void setDefaultPredicates() {
        this.batchExpiryPredicate = new BatchExpiryThresholdPredicate(Model.DEFAULT_EXPIRY_THRESHOLD);
        this.medicineExpiryPredicate = new MedicineExpiryThresholdPredicate(Model.DEFAULT_EXPIRY_THRESHOLD);
        this.medicineLowStockPredicate = new MedicineLowStockThresholdPredicate(Model.DEFAULT_LOW_STOCK_THRESHOLD);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof WarningPanelPredicateAccessor)) {
            return false;
        }

        // state check
        WarningPanelPredicateAccessor other = (WarningPanelPredicateAccessor) obj;
        return medicineExpiryPredicate.equals(other.medicineExpiryPredicate)
                && batchExpiryPredicate.equals(other.batchExpiryPredicate)
                && medicineLowStockPredicate.equals(other.medicineLowStockPredicate);
    }
}

