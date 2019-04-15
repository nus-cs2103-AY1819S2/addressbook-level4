package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.BookBuilder;

public class BookRatingMatchesScorePredicateTest {
    @Test
    public void equals() {
        String[] arr1 = {"1"};
        String[] arr2 = {"2"};
        List<String> firstPredicateKeywordList = Arrays.asList(arr1);
        List<String> secondPredicateKeywordList = Arrays.asList(arr2);

        BookRatingMatchesScorePredicate firstPredicate =
                new BookRatingMatchesScorePredicate(firstPredicateKeywordList);
        BookRatingMatchesScorePredicate secondPredicate =
                new BookRatingMatchesScorePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BookRatingMatchesScorePredicate firstPredicateCopy =
                new BookRatingMatchesScorePredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_ratingsContained_returnsTrue() {
        // One keyword
        String[] arr1 = {"1"};
        BookRatingMatchesScorePredicate predicate =
                new BookRatingMatchesScorePredicate(Arrays.asList(arr1));
        assertTrue(predicate.test(new BookBuilder().withRating("1").build()));

        // Multiple keywords
        String[] arr2 = {"1", "2"};
        predicate = new BookRatingMatchesScorePredicate(Arrays.asList(arr2));
        assertTrue(predicate.test(new BookBuilder().withRating("2").build()));
    }

    @Test
    public void test_ratingsNotcontained_returnsFalse() {
        // Non-matching rating
        String[] arr1 = {"1", "2"};
        BookRatingMatchesScorePredicate predicate =
                new BookRatingMatchesScorePredicate(Arrays.asList(arr1));
        assertFalse(predicate.test(new BookBuilder().withRating("3").build()));
    }
}
