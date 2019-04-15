package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.BookBuilder;

public class BookNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        BookNameContainsKeywordsPredicate firstPredicate =
                new BookNameContainsKeywordsPredicate(firstPredicateKeywordList);
        BookNameContainsKeywordsPredicate secondPredicate =
                new BookNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BookNameContainsKeywordsPredicate firstPredicateCopy =
                new BookNameContainsKeywordsPredicate(firstPredicateKeywordList);
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
        // Zero keyword
        BookNameContainsKeywordsPredicate predicate =
                new BookNameContainsKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(new BookBuilder().withBookName("Alice").build()));

        // One keyword
        predicate = new BookNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new BookBuilder().withBookName("Alice Bob").build()));

        // Multiple keywords
        predicate = new BookNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new BookBuilder().withBookName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new BookNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new BookBuilder().withBookName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new BookNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new BookBuilder().withBookName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        BookNameContainsKeywordsPredicate predicate = new BookNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new BookBuilder().withBookName("Alice Bob").build()));

        // Keywords match author and rating, but does not match name
        predicate = new BookNameContainsKeywordsPredicate(Arrays.asList("Rollin", "9"));
        assertFalse(predicate.test(new BookBuilder().withBookName("Alice").withAuthor("Rollin")
                .withRating("9").build()));
    }
}
