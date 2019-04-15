package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.BookBuilder;

public class BookNameContainsExactKeywordsPredicateTest {

    @Test
    public void equals() {
        BookName firstPredicateKeywordList = new BookName("first");
        BookName secondPredicateKeywordList = new BookName("second");

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
                new BookNameContainsExactKeywordsPredicate(new BookName("Alice"));
        assertTrue(predicate.test(new BookBuilder().withBookName("Alice").build()));

        // Multiple keywords
        predicate = new BookNameContainsExactKeywordsPredicate(new BookName("Alice in Wonderland"));
        assertTrue(predicate.test(new BookBuilder().withBookName("Alice in Wonderland").build()));

        // Mixed-case keywords
        predicate = new BookNameContainsExactKeywordsPredicate(new BookName("Alice In WONDERLAND"));
        assertTrue(predicate.test(new BookBuilder().withBookName("Alice In Wonderland").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        BookNameContainsExactKeywordsPredicate predicate =
                new BookNameContainsExactKeywordsPredicate(new BookName("Carol"));
        assertFalse(predicate.test(new BookBuilder().withBookName("Alice Bob").build()));

        // Keywords match author and rating, but does not match name
        predicate = new BookNameContainsExactKeywordsPredicate(new BookName("Rollin 9"));
        assertFalse(predicate.test(new BookBuilder().withBookName("Alice").withAuthor("Rollin")
                .withRating("9").build()));
    }

    @Test
    public void test_nameContainsSomeKeywords_returnsFalse() {
        // Some keywords
        BookNameContainsExactKeywordsPredicate predicate =
                new BookNameContainsExactKeywordsPredicate(new BookName("Bob Carol"));
        assertFalse(predicate.test(new BookBuilder().withBookName("Alice Carol").build()));

        // Some non-matching keyword
        predicate = new BookNameContainsExactKeywordsPredicate(new BookName("Carol"));
        assertFalse(predicate.test(new BookBuilder().withBookName("Carol Bob").build()));

        // Keywords in wrong order
        predicate = new BookNameContainsExactKeywordsPredicate(new BookName("Bob Carol"));
        assertFalse(predicate.test(new BookBuilder().withBookName("Carol Bob").build()));
    }
}
