package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.BookBuilder;

public class BookAuthorContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        BookAuthorContainsKeywordPredicate firstPredicate =
                new BookAuthorContainsKeywordPredicate(firstPredicateKeywordList);
        BookAuthorContainsKeywordPredicate secondPredicate =
                new BookAuthorContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BookAuthorContainsKeywordPredicate firstPredicateCopy =
               new BookAuthorContainsKeywordPredicate(firstPredicateKeywordList);
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
        BookAuthorContainsKeywordPredicate predicate = new BookAuthorContainsKeywordPredicate(Collections.emptyList());
        assertTrue(predicate.test(new BookBuilder().withAuthor("Lewis Carroll").build()));

        // One keyword
        predicate = new BookAuthorContainsKeywordPredicate(Collections.singletonList("Carroll"));
        assertTrue(predicate.test(new BookBuilder().withAuthor("Lewis Carroll").build()));

        // Multiple keywords
        predicate = new BookAuthorContainsKeywordPredicate(Arrays.asList("Lewis", "Carroll"));
        assertTrue(predicate.test(new BookBuilder().withAuthor("Lewis Carroll").build()));

        // Only one matching keyword
        predicate = new BookAuthorContainsKeywordPredicate(Arrays.asList("Lewis", "Carroll"));
        assertTrue(predicate.test(new BookBuilder().withAuthor("Alice Carroll").build()));

        // Mixed-case keywords
        predicate = new BookAuthorContainsKeywordPredicate(Arrays.asList("lEWis", "cARROLL"));
        assertTrue(predicate.test(new BookBuilder().withAuthor("Lewis Carroll").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        BookAuthorContainsKeywordPredicate predicate = new BookAuthorContainsKeywordPredicate(Arrays.asList("Carroll"));
        assertFalse(predicate.test(new BookBuilder().withBookName("Lewis Zusak").build()));

        // Keywords match name and rating, but does not match author
        predicate = new BookAuthorContainsKeywordPredicate(Arrays.asList("Alice", "Zusak", "9"));
        assertFalse(predicate.test(new BookBuilder().withBookName("Alice").withAuthor("Carroll")
                .withRating("9").build()));
    }
}
