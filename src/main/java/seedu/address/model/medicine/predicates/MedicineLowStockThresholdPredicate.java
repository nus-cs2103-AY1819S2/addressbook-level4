package seedu.address.model.medicine.predicates;

import java.util.function.Predicate;

import seedu.address.model.medicine.Medicine;
import seedu.address.model.threshold.Threshold;

/**
 * Tests that a {@code Medicine}'s {@code Quantity} is less than the threshold given.
 */
public class MedicineLowStockThresholdPredicate implements Predicate<Medicine> {
    private final Threshold threshold;

    public MedicineLowStockThresholdPredicate(Threshold threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean test(Medicine medicine) {
        return medicine.getTotalQuantity().getNumericValue() <= threshold.getNumericValue();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicineLowStockThresholdPredicate // instanceof handles nulls
                && threshold.equals(((MedicineLowStockThresholdPredicate) other).threshold)); // state check
    }

    public Threshold getThreshold() {
        return threshold;
    }
}
