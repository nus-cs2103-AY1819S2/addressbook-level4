package seedu.address.model.medicine.predicates;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

import seedu.address.model.medicine.Medicine;
import seedu.address.model.threshold.Threshold;

/**
 * Tests if {@code Medicine} has expired or is expiring soon based on its {@code Expiry} and given threshold.
 */
public class MedicineExpiryThresholdPredicate implements Predicate<Medicine> {
    private final Threshold threshold;

    public MedicineExpiryThresholdPredicate(Threshold threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean test(Medicine medicine) {
        return medicine.getNextExpiry().getExpiryDate() != null
                && calculateDaysToExpiry(medicine) <= threshold.getNumericValue();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicineExpiryThresholdPredicate // instanceof handles nulls
                && threshold.equals(((MedicineExpiryThresholdPredicate) other).threshold)); // state check
    }

    public Threshold getThreshold() {
        return threshold;
    }

    /**
     * Calculates and returns number of days from batch's expiry date to today.
     * @param medicine
     * @return
     */
    private float calculateDaysToExpiry(Medicine medicine) {
        return ChronoUnit.DAYS.between(LocalDate.now(), medicine.getNextExpiry().getExpiryDate());
    }
}
