package seedu.address.model.medicalhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.MedHistBuilder;

public class MedHistContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MedHistContainsKeywordsPredicate firstPredicate =
                new MedHistContainsKeywordsPredicate(firstPredicateKeywordList);
        MedHistContainsKeywordsPredicate secondPredicate =
                new MedHistContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MedHistContainsKeywordsPredicate firstPredicateCopy =
                new MedHistContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_writeUpContainsKeywords_returnsTrue() {
        // One keyword
        MedHistContainsKeywordsPredicate predicate =
                new MedHistContainsKeywordsPredicate(Collections.singletonList("Fever"));
        assertTrue(predicate.test(new MedHistBuilder().withWriteUp("have a fever").build()));

        // Multiple keywords
        predicate = new MedHistContainsKeywordsPredicate(Arrays.asList("fever", "sore"));
        assertTrue(predicate.test(new MedHistBuilder().withWriteUp("have a fever and sore throat").build()));

        // Only one matching keyword
        predicate = new MedHistContainsKeywordsPredicate(Arrays.asList("fever", "sore"));
        assertTrue(predicate.test(new MedHistBuilder().withWriteUp("have a fever").build()));

        // Mixed-case keywords
        predicate = new MedHistContainsKeywordsPredicate(Arrays.asList("Fever", "sore"));
        assertTrue(predicate.test(new MedHistBuilder().withWriteUp("have a fever").build()));
    }

    @Test
    public void test_writeUpDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MedHistContainsKeywordsPredicate predicate =
                new MedHistContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new MedHistBuilder().withWriteUp("fever").build()));

        // Non-matching keyword
        predicate = new MedHistContainsKeywordsPredicate(Arrays.asList("fever"));
        assertFalse(predicate.test(new MedHistBuilder().withWriteUp("cough").build()));
    }
}
