package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.BookBuilder;


public class BookTagsContainExactKeywordsPredicateTest {
    @Test
    public void equals() {
        String[] arr1 = {"fantasy"};
        String[] arr2 = {"textbook"};
        List<String> firstPredicateKeywordList = Arrays.asList(arr1);
        List<String> secondPredicateKeywordList = Arrays.asList(arr2);

        BookTagsContainExactKeywordsPredicate firstPredicate =
                new BookTagsContainExactKeywordsPredicate(firstPredicateKeywordList);
        BookTagsContainExactKeywordsPredicate secondPredicate =
                new BookTagsContainExactKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BookTagsContainExactKeywordsPredicate firstPredicateCopy =
                new BookTagsContainExactKeywordsPredicate(firstPredicateKeywordList);
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
        String[] arr1 = {"fantasy"};
        BookTagsContainExactKeywordsPredicate predicate =
                new BookTagsContainExactKeywordsPredicate(Arrays.asList(arr1));
        assertTrue(predicate.test(new BookBuilder().withTags("fantasy").build()));

        // Mixed-case keywords
        assertTrue(predicate.test(new BookBuilder().withTags("fantasy", "textbook").build()));

        // Multiple keywords
        String[] arr2 = {"textbook", "fantasy"};
        predicate = new BookTagsContainExactKeywordsPredicate(Arrays.asList(arr2));
        assertTrue(predicate.test(new BookBuilder().withTags("fantasy").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        String[] arr = {"textbook", "fantasy"};
        BookTagsContainExactKeywordsPredicate predicate = new BookTagsContainExactKeywordsPredicate(Arrays.asList(arr));
        assertFalse(predicate.test(new BookBuilder().withTags("popular").build()));
    }
}
