package seedu.finance.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.finance.testutil.RecordBuilder;

public class CategoryContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CategoryContainsKeywordsPredicate firstPredicate =
                new CategoryContainsKeywordsPredicate(firstPredicateKeywordList);
        CategoryContainsKeywordsPredicate secondPredicate =
                new CategoryContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CategoryContainsKeywordsPredicate firstPredicateCopy =
                new CategoryContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different record -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_categoryContainsKeywords_returnsTrue() {
        // One keyword
        CategoryContainsKeywordsPredicate predicate =
                new CategoryContainsKeywordsPredicate(Collections.singletonList("food"));
        assertTrue(predicate.test(new RecordBuilder().withCategory("food").build()));

        // Only one matching keyword
        predicate = new CategoryContainsKeywordsPredicate(Arrays.asList("food", "transport"));
        assertTrue(predicate.test(new RecordBuilder().withCategory("food").build()));

        // Mixed-case keywords
        predicate = new CategoryContainsKeywordsPredicate(Arrays.asList("fOoD", "TrAnsPort"));
        assertTrue(predicate.test(new RecordBuilder().withCategory("food").build()));
    }

    @Test
    public void test_categoryDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CategoryContainsKeywordsPredicate predicate = new CategoryContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new RecordBuilder().withCategory("food").build()));

        // Non-matching keyword
        predicate = new CategoryContainsKeywordsPredicate(Arrays.asList("games"));
        assertFalse(predicate.test(new RecordBuilder().withCategory("food").build()));

        // Keywords match amount, date, but does not match category
        predicate = new CategoryContainsKeywordsPredicate(Arrays.asList("12345", "27/08/2014"));
        assertFalse(predicate.test(new RecordBuilder().withCategory("food").withAmount("12345")
                .withDate("27/08/2014").build()));
    }
}
