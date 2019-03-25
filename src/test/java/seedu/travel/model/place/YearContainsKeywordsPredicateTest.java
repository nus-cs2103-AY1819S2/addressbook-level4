package seedu.travel.model.place;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.travel.testutil.PlaceBuilder;

public class YearContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("2015");
        List<String> secondPredicateKeywordList = Arrays.asList("2015", "2018");

        YearContainsKeywordsPredicate firstPredicate =
                new YearContainsKeywordsPredicate(firstPredicateKeywordList);
        YearContainsKeywordsPredicate secondPredicate =
                new YearContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        YearContainsKeywordsPredicate firstPredicateCopy =
                new YearContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different place -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_yearContainsKeywords_returnsTrue() {
        // One keyword
        YearContainsKeywordsPredicate predicate = new YearContainsKeywordsPredicate(
                Collections.singletonList("2015"));
        assertTrue(predicate.test(new PlaceBuilder().withDateVisited("05/06/2015").build()));

        // Multiple keywords
        predicate = new YearContainsKeywordsPredicate(Arrays.asList("1996", "2000", "2009"));
        assertTrue(predicate.test(new PlaceBuilder().withDateVisited("01/02/2000").build()));

    }

    @Test
    public void test_yearDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        YearContainsKeywordsPredicate predicate = new YearContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new PlaceBuilder().withDateVisited("20/05/2016").build()));

        // Non-matching keyword
        predicate = new YearContainsKeywordsPredicate(Collections.singletonList("2014"));
        assertFalse(predicate.test(new PlaceBuilder().withDateVisited("15/12/2013").build()));

    }
}
