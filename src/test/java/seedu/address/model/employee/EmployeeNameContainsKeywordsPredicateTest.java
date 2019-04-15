package seedu.address.model.employee;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.EmployeeBuilder;

public class EmployeeNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EmployeeNameContainsKeywordsPredicate firstPredicate = new EmployeeNameContainsKeywordsPredicate(
            firstPredicateKeywordList);
        EmployeeNameContainsKeywordsPredicate secondPredicate = new EmployeeNameContainsKeywordsPredicate(
            secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmployeeNameContainsKeywordsPredicate firstPredicateCopy = new EmployeeNameContainsKeywordsPredicate(
            firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different employee -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        EmployeeNameContainsKeywordsPredicate predicate = new EmployeeNameContainsKeywordsPredicate(
            Collections.singletonList("Alice"));
        assertTrue(predicate.test(new EmployeeBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new EmployeeNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new EmployeeBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new EmployeeNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new EmployeeBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new EmployeeNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new EmployeeBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmployeeNameContainsKeywordsPredicate predicate = new EmployeeNameContainsKeywordsPredicate(
            Collections.emptyList());
        assertFalse(predicate.test(new EmployeeBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new EmployeeNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new EmployeeBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and github, but does not match name
        predicate = new EmployeeNameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com",
            "Main", "Street"));
        assertFalse(predicate.test(new EmployeeBuilder().withName("Alice").withPhone("12345")
            .withEmail("alice@email.com").withGitHubAccount("aliceballer").build()));
    }
}
