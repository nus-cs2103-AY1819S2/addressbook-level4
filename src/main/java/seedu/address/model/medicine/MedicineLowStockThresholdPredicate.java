package seedu.address.model.medicine;

import java.util.function.Predicate;

public class MedicineLowStockThresholdPredicate implements Predicate<Medicine> {
    private final Integer threshold;

    public MedicineLowStockThresholdPredicate(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean test(Medicine medicine) {
        return medicine.getTotalQuantity().getNumericValue() < threshold;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicineLowStockThresholdPredicate // instanceof handles nulls
                && threshold.equals(((MedicineLowStockThresholdPredicate) other).threshold)); // state check
    }

    public int getThreshold() {
        return threshold.intValue();
    }
}
