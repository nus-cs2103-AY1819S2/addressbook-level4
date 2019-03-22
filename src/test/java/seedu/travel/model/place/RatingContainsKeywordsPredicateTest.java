package seedu.travel.model.place;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.travel.testutil.PlaceBuilder;

public class RatingContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("1");
        List<String> secondPredicateKeywordList = Arrays.asList("1", "2");

        RatingContainsKeywordsPredicate firstPredicate = new RatingContainsKeywordsPredicate(firstPredicateKeywordList);
        RatingContainsKeywordsPredicate secondPredicate =
                new RatingContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RatingContainsKeywordsPredicate firstPredicateCopy =
                new RatingContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different place -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_ratingContainsKeywords_returnsTrue() {
        // One keyword
        RatingContainsKeywordsPredicate predicate = new RatingContainsKeywordsPredicate(Collections.singletonList("4"));
        assertTrue(predicate.test(new PlaceBuilder().withRating("4").build()));

        // Multiple keywords
        predicate = new RatingContainsKeywordsPredicate(Arrays.asList("4", "5"));
        assertTrue(predicate.test(new PlaceBuilder().withRating("5").build()));

    }

    @Test
    public void test_ratingDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RatingContainsKeywordsPredicate predicate = new RatingContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PlaceBuilder().withRating("3").build()));

        // Non-matching keyword
        predicate = new RatingContainsKeywordsPredicate(Collections.singletonList("5"));
        assertFalse(predicate.test(new PlaceBuilder().withRating("3").build()));

        // Keywords match name, description and travel, but does not match rating
        predicate = new RatingContainsKeywordsPredicate(Arrays.asList("Alice", "None", "Main", "Street"));
        assertFalse(predicate.test(new PlaceBuilder().withName("Alice").withRating("4")
                .withDescription("None").withAddress("Main Street").build()));
    }
}
