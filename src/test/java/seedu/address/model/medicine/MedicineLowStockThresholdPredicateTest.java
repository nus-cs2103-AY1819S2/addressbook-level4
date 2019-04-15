package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.model.Model;
import seedu.address.model.medicine.predicates.MedicineLowStockThresholdPredicate;
import seedu.address.model.threshold.Threshold;
import seedu.address.testutil.MedicineBuilder;

public class MedicineLowStockThresholdPredicateTest {

    @Test
    public void equals() {
        Threshold firstThreshold = Model.DEFAULT_LOW_STOCK_THRESHOLD; // threshold value of 20
        Threshold secondThreshold =
                new Threshold(Integer.toString(Threshold.MIN_THRESHOLD), WarningPanelPredicateType.LOW_STOCK);

        MedicineLowStockThresholdPredicate firstPredicate = new MedicineLowStockThresholdPredicate(firstThreshold);
        MedicineLowStockThresholdPredicate secondPredicate = new MedicineLowStockThresholdPredicate(secondThreshold);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MedicineLowStockThresholdPredicate firstPredicateCopy = new MedicineLowStockThresholdPredicate(firstThreshold);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // same threshold -> returns true
        assertTrue(firstPredicate.getThreshold().equals(firstThreshold));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    /**
     * Medicine is low in stock.
     */
    @Test
    public void test_medicineBelowLowStockThreshold_returnsTrue() {
        MedicineLowStockThresholdPredicate predicate;

        // Minimum threshold
        predicate = new MedicineLowStockThresholdPredicate(
                new Threshold(Integer.toString(Threshold.MIN_THRESHOLD), WarningPanelPredicateType.LOW_STOCK));
        assertTrue(predicate.test(new MedicineBuilder().withQuantity(Integer.toString(Quantity.MIN_QUANTITY)).build()));

        // Threshold for maximum Medicine quantity
        predicate = new MedicineLowStockThresholdPredicate(
                new Threshold(Integer.toString(Threshold.MAX_QUANTITY_THRESHOLD), WarningPanelPredicateType.LOW_STOCK));
        assertTrue(predicate.test(new MedicineBuilder().withQuantity(Integer.toString(Quantity.MIN_QUANTITY)).build()));
        assertTrue(predicate.test(new MedicineBuilder().withQuantity(Integer.toString(Quantity.MAX_QUANTITY)).build()));
        assertTrue(predicate.test(new MedicineBuilder().withQuantity(Model.DEFAULT_LOW_STOCK_THRESHOLD.value).build()));

        // Default threshold
        predicate = new MedicineLowStockThresholdPredicate(Model.DEFAULT_LOW_STOCK_THRESHOLD);
        assertTrue(predicate.test(new MedicineBuilder().withQuantity(Integer.toString(Quantity.MIN_QUANTITY)).build()));
        assertTrue(predicate.test(new MedicineBuilder().withQuantity(Model.DEFAULT_LOW_STOCK_THRESHOLD.value).build()));
    }

    /**
     * Medicine is not low in stock.
     */
    @Test
    public void test_medicineAboveLowStockThreshold_returnsFalse() {
        MedicineLowStockThresholdPredicate predicate;

        // Minimum threshold
        predicate = new MedicineLowStockThresholdPredicate(
                new Threshold(Integer.toString(Threshold.MIN_THRESHOLD), WarningPanelPredicateType.LOW_STOCK));
        assertFalse(predicate.test(new MedicineBuilder()
                .withQuantity(Integer.toString(Quantity.MIN_QUANTITY + 1)).build()));
        assertFalse(predicate.test(new MedicineBuilder()
                .withQuantity(Model.DEFAULT_LOW_STOCK_THRESHOLD.value).build()));
        assertFalse(predicate.test(new MedicineBuilder()
                .withQuantity(Integer.toString(Quantity.MAX_QUANTITY)).build()));

        // Greatest threshold for maximum Medicine quantity
        predicate = new MedicineLowStockThresholdPredicate(
                new Threshold(Integer.toString(Quantity.MAX_QUANTITY - 1), WarningPanelPredicateType.LOW_STOCK));
        assertFalse(predicate.test(new MedicineBuilder()
                .withQuantity(Integer.toString(Quantity.MAX_QUANTITY)).build()));

        // Default threshold
        predicate = new MedicineLowStockThresholdPredicate(Model.DEFAULT_LOW_STOCK_THRESHOLD);
        assertFalse(predicate.test(new MedicineBuilder()
                .withQuantity(Integer.toString(Model.DEFAULT_LOW_STOCK_THRESHOLD.getNumericValue() + 1)).build()));
        assertFalse(predicate.test(new MedicineBuilder()
                .withQuantity(Integer.toString(Quantity.MAX_QUANTITY)).build()));
    }
}
