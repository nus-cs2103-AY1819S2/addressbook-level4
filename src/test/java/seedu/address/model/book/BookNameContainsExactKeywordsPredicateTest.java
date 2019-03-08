package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.BookBuilder;

public class BookNameContainsExactKeywordsPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeywordList = "first";
        String secondPredicateKeywordList = "first second";

        BookNameContainsExactKeywordsPredicate firstPredicate =
                new BookNameContainsExactKeywordsPredicate(firstPredicateKeywordList);
        BookNameContainsExactKeywordsPredicate secondPredicate =
                new BookNameContainsExactKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BookNameContainsExactKeywordsPredicate firstPredicateCopy =
                new BookNameContainsExactKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        BookNameContainsExactKeywordsPredicate predicate =
                new BookNameContainsExactKeywordsPredicate("Alice");
        assertTrue(predicate.test(new BookBuilder().withName("Alice").build()));

        // Multiple keywords
        predicate = new BookNameContainsExactKeywordsPredicate("Alice in Wonderland");
        assertTrue(predicate.test(new BookBuilder().withName("Alice in Wonderland").build()));

        // Mixed-case keywords
        predicate = new BookNameContainsExactKeywordsPredicate("Alice In WONDERLAND");
        assertTrue(predicate.test(new BookBuilder().withName("Alice In Wonderland").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        BookNameContainsExactKeywordsPredicate predicate =
                new BookNameContainsExactKeywordsPredicate("");
        assertFalse(predicate.test(new BookBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new BookNameContainsExactKeywordsPredicate("Carol");
        assertFalse(predicate.test(new BookBuilder().withName("Alice Bob").build()));

        // Keywords match author and rating, but does not match name
        predicate = new BookNameContainsExactKeywordsPredicate("Rollin 9");
        assertFalse(predicate.test(new BookBuilder().withName("Alice").withAuthor("Rollin")
                .withRating("9").build()));
    }

    @Test
    public void test_nameContainsSomeKeywords_returnsFalse() {
        // Some keywords
        BookNameContainsExactKeywordsPredicate predicate =
                new BookNameContainsExactKeywordsPredicate("Bob Carol");
        assertFalse(predicate.test(new BookBuilder().withName("Alice Carol").build()));

        // Some non-matching keyword
        predicate = new BookNameContainsExactKeywordsPredicate("Carol");
        assertFalse(predicate.test(new BookBuilder().withName("Carol Bob").build()));

        // Keywords in wrong order
        predicate = new BookNameContainsExactKeywordsPredicate("Bob Carol");
        assertFalse(predicate.test(new BookBuilder().withName("Carol Bob").build()));
    }
}
