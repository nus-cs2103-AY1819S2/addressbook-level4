package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.model.Model;
import seedu.address.model.medicine.predicates.BatchExpiryThresholdPredicate;
import seedu.address.model.threshold.Threshold;
import seedu.address.testutil.BatchBuilder;

public class BatchExpiryThresholdPredicateTest {

    @Test
    public void equals() {
        Threshold firstThreshold = Model.DEFAULT_EXPIRY_THRESHOLD; // threshold value of 10
        Threshold secondThreshold =
                new Threshold(Integer.toString(Threshold.MIN_THRESHOLD), WarningPanelPredicateType.EXPIRY);

        BatchExpiryThresholdPredicate firstPredicate = new BatchExpiryThresholdPredicate(firstThreshold);
        BatchExpiryThresholdPredicate secondPredicate = new BatchExpiryThresholdPredicate(secondThreshold);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BatchExpiryThresholdPredicate firstPredicateCopy = new BatchExpiryThresholdPredicate(firstThreshold);
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
     * Batch is expiring or has expired.
     */
    @Test
    public void test_batchBelowExpiryThreshold_returnsTrue() {
        BatchExpiryThresholdPredicate predicate;

        String today = formatDateToString(LocalDate.now());
        String yesterday = formatDateToString(LocalDate.now().minusDays(1));
        String defaultDate = formatDateToString(LocalDate.now()
                .plusDays(Model.DEFAULT_EXPIRY_THRESHOLD.getNumericValue()));
        String maxDate = formatDateToString(LocalDate.now().plusDays(Expiry.MAX_DAYS_TO_EXPIRY));

        // Minimum threshold for expiring and expired batch
        predicate = new BatchExpiryThresholdPredicate(
                new Threshold(Integer.toString(Threshold.MIN_THRESHOLD), WarningPanelPredicateType.EXPIRY));
        assertTrue(predicate.test(new BatchBuilder().withExpiry(today).build()));
        assertTrue(predicate.test(new BatchBuilder().withExpiry(yesterday).build()));

        // Default threshold
        predicate = new BatchExpiryThresholdPredicate(Model.DEFAULT_EXPIRY_THRESHOLD);
        assertTrue(predicate.test(new BatchBuilder().withExpiry(defaultDate).build()));
        assertTrue(predicate.test(new BatchBuilder().withExpiry(today).build()));
        assertTrue(predicate.test(new BatchBuilder().withExpiry(yesterday).build()));

        // Max threshold
        predicate = new BatchExpiryThresholdPredicate(
                new Threshold(Integer.toString(Threshold.MAX_EXPIRY_THRESHOLD), WarningPanelPredicateType.EXPIRY));
        assertTrue(predicate.test(new BatchBuilder().withExpiry(defaultDate).build()));
        assertTrue(predicate.test(new BatchBuilder().withExpiry(today).build()));
        assertTrue(predicate.test(new BatchBuilder().withExpiry(yesterday).build()));
        assertTrue(predicate.test(new BatchBuilder().withExpiry(maxDate).build()));
    }

    /**
     * Batch is not expiring.
     */
    @Test
    public void test_batchAboveExpiryThreshold_returnsFalse() {
        BatchExpiryThresholdPredicate predicate;

        String tomorrow = formatDateToString(LocalDate.now().plusDays(1));
        String defaultDate = formatDateToString(LocalDate.now()
                .plusDays(Model.DEFAULT_EXPIRY_THRESHOLD.getNumericValue()));
        String maxDate = formatDateToString(LocalDate.now().plusDays(Expiry.MAX_DAYS_TO_EXPIRY));

        // Minimum threshold for expiring and expired batch
        predicate = new BatchExpiryThresholdPredicate(
                new Threshold(Integer.toString(Threshold.MIN_THRESHOLD), WarningPanelPredicateType.EXPIRY));
        assertFalse(predicate.test(new BatchBuilder().withExpiry(tomorrow).build()));
        assertFalse(predicate.test(new BatchBuilder().withExpiry(defaultDate).build()));
        assertFalse(predicate.test(new BatchBuilder().withExpiry(maxDate).build()));

        // Default threshold
        predicate = new BatchExpiryThresholdPredicate(Model.DEFAULT_EXPIRY_THRESHOLD);
        assertFalse(predicate.test(new BatchBuilder().withExpiry(maxDate).build()));
    }

    private String formatDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
