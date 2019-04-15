package seedu.address.model.medicine.predicates;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

import seedu.address.model.medicine.Batch;
import seedu.address.model.threshold.Threshold;

/**
 * Tests if {@code Batch} has expired or is expiring soon based on its {@code Expiry} and given threshold.
 */
public class BatchExpiryThresholdPredicate implements Predicate<Batch> {
    private final Threshold threshold;

    public BatchExpiryThresholdPredicate(Threshold threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean test(Batch batch) {
        return calculateDaysToExpiry(batch) <= threshold.getNumericValue();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BatchExpiryThresholdPredicate // instanceof handles nulls
                && threshold.equals(((BatchExpiryThresholdPredicate) other).threshold)); // state check
    }

    public Threshold getThreshold() {
        return threshold;
    }

    /**
     * Calculates and returns number of days from batch's expiry date to today.
     * @param batch
     * @return
     */
    private float calculateDaysToExpiry(Batch batch) {
        return ChronoUnit.DAYS.between(LocalDate.now(), batch.getExpiry().getExpiryDate());
    }
}
