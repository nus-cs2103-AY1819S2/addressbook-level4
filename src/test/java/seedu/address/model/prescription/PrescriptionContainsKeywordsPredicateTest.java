package seedu.address.model.prescription;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PrescriptionBuilder;

public class PrescriptionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("test");
        List<String> secondPredicateKeywordList = Arrays.asList("test", "testing");

        PrescriptionContainsKeywordsPredicate firstPredicate =
                new PrescriptionContainsKeywordsPredicate(firstPredicateKeywordList);
        PrescriptionContainsKeywordsPredicate secondPredicate =
                new PrescriptionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PrescriptionContainsKeywordsPredicate firstPredicateCopy =
                new PrescriptionContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword. The description contains the keyword pain -> returns true
        PrescriptionContainsKeywordsPredicate predicate =
                new PrescriptionContainsKeywordsPredicate(Collections.singletonList("pain"));
        assertTrue(predicate.test(new PrescriptionBuilder().withDescription("For relieving pain").build()));

        // Multiple keywords. The description contains at least one keyword -> returns true
        predicate = new PrescriptionContainsKeywordsPredicate(Arrays.asList("pain", "fever"));
        assertTrue(predicate.test(new PrescriptionBuilder().withDescription("For relieving pain").build()));

        // Mixed-case keywords. The description contains at least one keyword(ignoring case) -> returns true
        predicate = new PrescriptionContainsKeywordsPredicate(Arrays.asList("Pain", "fever"));
        assertTrue(predicate.test(new PrescriptionBuilder().withDescription("For relieving pain").build()));
    }

    @Test
    public void test_descriptionDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        PrescriptionContainsKeywordsPredicate predicate =
                new PrescriptionContainsKeywordsPredicate(Arrays.asList("pain"));
        assertFalse(predicate.test(new PrescriptionBuilder().withDescription("For curing fever").build()));
    }
}

