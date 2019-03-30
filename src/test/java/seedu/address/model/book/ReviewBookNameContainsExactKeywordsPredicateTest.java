package seedu.address.model.book;

import org.junit.Test;
import seedu.address.testutil.ReviewBuilder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReviewBookNameContainsExactKeywordsPredicateTest {

    @Test
    public void equals() {
        BookName firstPredicateKeywordList = new BookName("first");
        BookName secondPredicateKeywordList = new BookName("second");

        ReviewBookNameContainsExactKeywordsPredicate firstPredicate =
                new ReviewBookNameContainsExactKeywordsPredicate(firstPredicateKeywordList);
        ReviewBookNameContainsExactKeywordsPredicate secondPredicate =
                new ReviewBookNameContainsExactKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ReviewBookNameContainsExactKeywordsPredicate firstPredicateCopy =
                new ReviewBookNameContainsExactKeywordsPredicate(firstPredicateKeywordList);
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
        ReviewBookNameContainsExactKeywordsPredicate predicate =
                new ReviewBookNameContainsExactKeywordsPredicate(new BookName("Alice"));
        assertTrue(predicate.test(new ReviewBuilder().withBookName("Alice").build()));

        // Multiple keywords
        predicate = new ReviewBookNameContainsExactKeywordsPredicate(new BookName("Alice in Wonderland"));
        assertTrue(predicate.test(new ReviewBuilder().withBookName("Alice in Wonderland").build()));

        // Mixed-case keywords
        predicate = new ReviewBookNameContainsExactKeywordsPredicate(new BookName("Alice In WONDERLAND"));
        assertTrue(predicate.test(new ReviewBuilder().withBookName("Alice In Wonderland").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        ReviewBookNameContainsExactKeywordsPredicate predicate =
                new ReviewBookNameContainsExactKeywordsPredicate(new BookName("Carol"));
        assertFalse(predicate.test(new ReviewBuilder().withBookName("Alice Bob").build()));

        // Keywords match message and title, but does not match name
        predicate = new ReviewBookNameContainsExactKeywordsPredicate(new BookName("Rollin 9"));
        assertFalse(predicate.test(new ReviewBuilder().withBookName("Alice").withReviewMessage("Rollin")
                .withReviewTitle("9").build()));
    }

    @Test
    public void test_nameContainsSomeKeywords_returnsFalse() {
        // Some keywords
        ReviewBookNameContainsExactKeywordsPredicate predicate =
                new ReviewBookNameContainsExactKeywordsPredicate(new BookName("Bob Carol"));
        assertFalse(predicate.test(new ReviewBuilder().withBookName("Alice Carol").build()));

        // Some non-matching keyword
        predicate = new ReviewBookNameContainsExactKeywordsPredicate(new BookName("Carol"));
        assertFalse(predicate.test(new ReviewBuilder().withBookName("Carol Bob").build()));

        // Keywords in wrong order
        predicate = new ReviewBookNameContainsExactKeywordsPredicate(new BookName("Bob Carol"));
        assertFalse(predicate.test(new ReviewBuilder().withBookName("Carol Bob").build()));
    }
}
