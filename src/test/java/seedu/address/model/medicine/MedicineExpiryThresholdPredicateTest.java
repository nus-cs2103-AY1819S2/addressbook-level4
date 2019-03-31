package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import seedu.address.model.threshold.Threshold;
import seedu.address.model.Model;
import seedu.address.testutil.MedicineBuilder;

public class MedicineExpiryThresholdPredicateTest {

    @Test
    public void equals() {
        Threshold firstThreshold = Model.DEFAULT_EXPIRY_THRESHOLD; // threshold value of 10
        Threshold secondThreshold = new Threshold("0");

        MedicineExpiryThresholdPredicate firstPredicate = new MedicineExpiryThresholdPredicate(firstThreshold);
        MedicineExpiryThresholdPredicate secondPredicate = new MedicineExpiryThresholdPredicate(secondThreshold);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MedicineExpiryThresholdPredicate firstPredicateCopy = new MedicineExpiryThresholdPredicate(firstThreshold);
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
     * Medicine is expiring or has expired.
     */
    @Test
    public void test_medicineBelowExpiryThreshold_returnsTrue() {
        MedicineExpiryThresholdPredicate predicate;

        String today = formatDateToString(LocalDate.now());
        String tomorrow = formatDateToString(LocalDate.now().plusDays(1));
        String yesterday = formatDateToString(LocalDate.now().minusDays(1));
        String defaultDate = formatDateToString(LocalDate.now()
                .plusDays(Model.DEFAULT_EXPIRY_THRESHOLD.getNumericValue()));

        // Minimum threshold for expiring and expired medicine
        predicate = new MedicineExpiryThresholdPredicate(new Threshold("0"));
        assertTrue(predicate.test(new MedicineBuilder().withExpiry(today).build()));
        assertTrue(predicate.test(new MedicineBuilder().withExpiry(yesterday).build()));

        // Default threshold
        predicate = new MedicineExpiryThresholdPredicate(Model.DEFAULT_EXPIRY_THRESHOLD);
        assertTrue(predicate.test(new MedicineBuilder().withExpiry(defaultDate).build()));
        assertTrue(predicate.test(new MedicineBuilder().withExpiry(tomorrow).build()));
        assertTrue(predicate.test(new MedicineBuilder().withExpiry(today).build()));
        assertTrue(predicate.test(new MedicineBuilder().withExpiry(yesterday).build()));
    }

    /**
     * Medicine is not expiring.
     */
    @Test
    public void test_medicineAboveExpiryThreshold_returnsFalse() {
        MedicineExpiryThresholdPredicate predicate;

        String tomorrow = formatDateToString(LocalDate.now().plusDays(1));
        String defaultDate = formatDateToString(LocalDate.now()
                .plusDays(Model.DEFAULT_EXPIRY_THRESHOLD.getNumericValue()));
        String nextYear = formatDateToString(LocalDate.now().plusDays(365));

        // Minimum threshold for expiring and expired medicine
        predicate = new MedicineExpiryThresholdPredicate(new Threshold("0"));
        assertFalse(predicate.test(new MedicineBuilder().withExpiry(tomorrow).build()));
        assertFalse(predicate.test(new MedicineBuilder().withExpiry(defaultDate).build()));

        // Default threshold
        predicate = new MedicineExpiryThresholdPredicate(Model.DEFAULT_EXPIRY_THRESHOLD);
        assertFalse(predicate.test(new MedicineBuilder().withExpiry(nextYear).build()));
    }

    private String formatDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
