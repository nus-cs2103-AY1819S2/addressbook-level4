package seedu.travel.model.place;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.travel.testutil.PlaceBuilder;

public class CountryCodeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("SGP");
        List<String> secondPredicateKeywordList = Arrays.asList("SGP", "JPN");

        CountryCodeContainsKeywordsPredicate firstPredicate =
                new CountryCodeContainsKeywordsPredicate(firstPredicateKeywordList);
        CountryCodeContainsKeywordsPredicate secondPredicate =
                new CountryCodeContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CountryCodeContainsKeywordsPredicate firstPredicateCopy =
                new CountryCodeContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different place -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_countryCodeContainsKeywords_returnsTrue() {
        // One keyword
        CountryCodeContainsKeywordsPredicate predicate = new CountryCodeContainsKeywordsPredicate(
                Collections.singletonList("CHN"));
        assertTrue(predicate.test(new PlaceBuilder().withCountryCode("CHN").build()));

        // Multiple keywords
        predicate = new CountryCodeContainsKeywordsPredicate(Arrays.asList("DEU", "JPN", "USA"));
        assertTrue(predicate.test(new PlaceBuilder().withCountryCode("DEU").build()));

    }

    @Test
    public void test_countryCodeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CountryCodeContainsKeywordsPredicate predicate = new CountryCodeContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new PlaceBuilder().withCountryCode("KOR").build()));

        // Non-matching keyword
        predicate = new CountryCodeContainsKeywordsPredicate(Collections.singletonList("FRA"));
        assertFalse(predicate.test(new PlaceBuilder().withCountryCode("ITA").build()));

    }
}
