package seedu.travel.model.place;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.travel.testutil.PlaceBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different place -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList("school"));
        assertTrue(predicate.test(new PlaceBuilder().withTags("school", "temple").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("school", "temple"));
        assertTrue(predicate.test(new PlaceBuilder().withTags("school", "temple").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("school", "shoppingMall"));
        assertTrue(predicate.test(new PlaceBuilder().withTags("airport", "shoppingMall").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("sChOOl", "tEmPlE"));
        assertTrue(predicate.test(new PlaceBuilder().withTags("school", "temple").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PlaceBuilder().withTags("airport").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("airport"));
        assertFalse(predicate.test(new PlaceBuilder().withTags("school", "temple").build()));

        // Keywords match name, country code, rating, description and travel, but does not match tags
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("NUS", "None", "Main", "Street"));
        assertFalse(predicate.test(new PlaceBuilder().withName("NUS").withCountryCode("SGP").withRating("4")
                .withDescription("None").withAddress("Main Street").withTags("school").build()));
    }
}
