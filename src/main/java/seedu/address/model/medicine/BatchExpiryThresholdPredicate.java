package seedu.address.model.medicine;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

/**
 * Tests that a {@code Medicine}'s {@code Name} matches any of the keywords given.
 */
public class BatchExpiryThresholdPredicate implements Predicate<Batch> {
    private final Integer threshold;

    public BatchExpiryThresholdPredicate(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean test(Batch batch) {
        return calculateDaysToExpiry(batch) < threshold;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BatchExpiryThresholdPredicate // instanceof handles nulls
                && threshold.equals(((BatchExpiryThresholdPredicate) other).threshold)); // state check
    }

    public int getThreshold() {
        return threshold.intValue();
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
