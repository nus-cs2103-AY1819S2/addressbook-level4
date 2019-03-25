package seedu.address.model.deck;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.CardBuilder;

public class CardNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CardNameContainsKeywordsPredicate firstPredicate = new CardNameContainsKeywordsPredicate(firstPredicateKeywordList);
        CardNameContainsKeywordsPredicate secondPredicate = new CardNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CardNameContainsKeywordsPredicate firstPredicateCopy = new CardNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different card -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        CardNameContainsKeywordsPredicate predicate = new CardNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new CardBuilder().withQuestion("Alice Bob").build()));

        // Multiple keywords
        predicate = new CardNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new CardBuilder().withQuestion("Alice Bob").build()));

        // Only one matching keyword
        predicate = new CardNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new CardBuilder().withQuestion("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new CardNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new CardBuilder().withQuestion("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CardNameContainsKeywordsPredicate predicate = new CardNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CardBuilder().withQuestion("Alice").build()));

        // Non-matching keyword
        predicate = new CardNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new CardBuilder().withQuestion("Alice Bob").build()));

        // Keywords match answer but does not match question
        predicate = new CardNameContainsKeywordsPredicate(Arrays.asList("12345"));
        assertFalse(predicate.test(new CardBuilder().withQuestion("Alice").withAnswer("12345").build()));
    }
}
