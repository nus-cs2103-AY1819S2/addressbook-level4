package seedu.address.model.request;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.RequestBuilder;

class RequestNricContainsKeywordPredicateTest {

    @org.junit.jupiter.api.Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        RequestNricContainsKeywordPredicate firstPredicate =
            new RequestNricContainsKeywordPredicate(firstPredicateKeyword);
        RequestNricContainsKeywordPredicate secondPredicate =
            new RequestNricContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RequestNricContainsKeywordPredicate firstPredicateCopy =
            new RequestNricContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different name -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        RequestNricContainsKeywordPredicate predicate =
            new RequestNricContainsKeywordPredicate("S9623456G");
        assertTrue(predicate.test(new RequestBuilder().withName("S9623456G").build()));

        // Partial Match
        predicate = new RequestNricContainsKeywordPredicate("S962");
        assertTrue(predicate.test(new RequestBuilder().withNric("S9623456G").build()));

        // Mixed-case keywords
        predicate = new RequestNricContainsKeywordPredicate("s962");
        assertTrue(predicate.test(new RequestBuilder().withName("S9623456G").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        RequestNricContainsKeywordPredicate predicate = new RequestNricContainsKeywordPredicate(
            "S102");
        assertFalse(predicate.test(new RequestBuilder().withName("S9623456G").build()));
    }
}
