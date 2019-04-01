package seedu.address.model.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.RequestBuilder;

class RequestNameContainsKeywordPredicateTest {
    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        RequestNameContainsKeywordPredicate firstPredicate =
            new RequestNameContainsKeywordPredicate(firstPredicateKeyword);
        RequestNameContainsKeywordPredicate secondPredicate =
            new RequestNameContainsKeywordPredicate(secondPredicateKeyword);

        assertEquals(firstPredicate, firstPredicate);

        RequestNameContainsKeywordPredicate firstPredicateCopy =
            new RequestNameContainsKeywordPredicate(firstPredicateKeyword);
        assertEquals(firstPredicate, firstPredicateCopy);

        assertNotEquals(firstPredicate, 1);

        assertNotEquals(firstPredicate, null);

        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFlase() {
        RequestNameContainsKeywordPredicate predicate = new RequestNameContainsKeywordPredicate(
            "Carol");
        assertFalse(predicate.test(new RequestBuilder().withName("Alice Bob").build()));
    }
}
