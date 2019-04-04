package seedu.finance.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.finance.testutil.RecordBuilder;

public class DateContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("1/1/2001");
        List<String> secondPredicateKeywordList = Arrays.asList("1/1/2001", "2/2/2002");

        DateContainsKeywordsPredicate firstPredicate = new DateContainsKeywordsPredicate(firstPredicateKeywordList);
        DateContainsKeywordsPredicate secondPredicate = new DateContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateContainsKeywordsPredicate firstPredicateCopy = new DateContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different record -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateContainsKeywords_returnsTrue() {
        // One date
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("01/01/2001"));
        assertTrue(predicate.test(new RecordBuilder().withDate("01/01/2001").build()));

        // Only one matching date
        predicate = new DateContainsKeywordsPredicate(Arrays.asList("01/01/2001", "02/02/2002"));
        assertTrue(predicate.test(new RecordBuilder().withDate("01/01/2001").build()));

    }

    @Test
    public void test_dateDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DateContainsKeywordsPredicate predicate = new DateContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new RecordBuilder().withDate("01/01/2001").build()));

        // Non-matching keyword
        predicate = new DateContainsKeywordsPredicate(Arrays.asList("11/01/2001"));
        assertFalse(predicate.test(new RecordBuilder().withDate("01/01/2001").build()));
    }
}
