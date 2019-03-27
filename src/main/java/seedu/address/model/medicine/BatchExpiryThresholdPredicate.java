package seedu.address.model.medicine;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

import seedu.address.model.threshold.Threshold;

/**
 * Tests that number of days from {@code Batch}'s {@code Expiry} to today is less than the threshold given.
 */
public class BatchExpiryThresholdPredicate implements Predicate<Batch> {
    private final Threshold threshold;

    public BatchExpiryThresholdPredicate(Threshold threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean test(Batch batch) {
        return calculateDaysToExpiry(batch) < threshold.getNumericValue();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BatchExpiryThresholdPredicate // instanceof handles nulls
                && threshold.equals(((BatchExpiryThresholdPredicate) other).threshold)); // state check
    }

    public int getThreshold() {
        return threshold.getNumericValue();
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
