package seedu.address.model.medicine;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

import seedu.address.model.threshold.Threshold;

/**
 * Tests that number of days from {@code Medicine}'s {@code Expiry} to today is less than the threshold given.
 */
public class MedicineExpiryThresholdPredicate implements Predicate<Medicine> {
    private final Threshold threshold;

    public MedicineExpiryThresholdPredicate(Threshold threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean test(Medicine medicine) {
        return medicine.getNextExpiry().getExpiryDate() != null
                && calculateDaysToExpiry(medicine) < threshold.getNumericValue();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicineExpiryThresholdPredicate // instanceof handles nulls
                && threshold.equals(((MedicineExpiryThresholdPredicate) other).threshold)); // state check
    }

    public int getThreshold() {
        return threshold.getNumericValue();
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
