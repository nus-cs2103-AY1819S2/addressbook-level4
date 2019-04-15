package seedu.address.model.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ProjectBuilder;

public class ProjectContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ProjectContainsKeywordsPredicate firstPredicate = new ProjectContainsKeywordsPredicate(
            firstPredicateKeywordList);
        ProjectContainsKeywordsPredicate secondPredicate = new ProjectContainsKeywordsPredicate(
            secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ProjectContainsKeywordsPredicate firstPredicateCopy = new ProjectContainsKeywordsPredicate(
            firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different value -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsKeywords_returnsTrue() {
        // One keyword , check if the keyword is inside description/client/name
        ProjectContainsKeywordsPredicate predicate = new ProjectContainsKeywordsPredicate(
            Collections.singletonList("software"));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Alice Project")
            .withDescription("A software engineering application").build()));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Alice Project")
            .withClient("software company").build()));

        // Multiple keywords
        predicate = new ProjectContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Alice Bob Project").build()));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Hello World")
            .withDescription("A collaboration between Alice and Bob").build()));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Hello World")
            .withClient("Alice Bob Tan").build()));


        // Only one matching keyword
        predicate = new ProjectContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Alice Carol Project").build()));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Hello World")
            .withDescription("A collaboration between Alice and Bob").build()));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Hello World")
            .withClient("Alice Bob Tan").build()));

        // Mixed-case keywords
        predicate = new ProjectContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Alice Bob Project").build()));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Hello World")
            .withDescription("A collaboration between Alice and Bob").build()));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Hello World")
            .withClient("Alice Bob Tan").build()));
    }

    @Test
    public void test_doesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ProjectContainsKeywordsPredicate predicate = new ProjectContainsKeywordsPredicate(
            Collections.emptyList());
        assertFalse(predicate.test(new ProjectBuilder().withProjectName("Alice").build()));

        // Non-matching keyword
        predicate = new ProjectContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ProjectBuilder().withProjectName("Alice Project").build()));
        assertFalse(predicate.test(new ProjectBuilder().withProjectName("Alice Project")
            .withClient("someonw").build()));
        assertFalse(predicate.test(new ProjectBuilder().withProjectName("Alice Project")
            .withDescription("Alice and Bob collaboration").build()));
    }
}
