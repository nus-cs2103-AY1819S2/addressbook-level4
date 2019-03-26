package seedu.address.model.person.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NricContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NricContainsKeywordsPredicate firstPredicate = new NricContainsKeywordsPredicate(firstPredicateKeywordList);
        NricContainsKeywordsPredicate secondPredicate = new NricContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NricContainsKeywordsPredicate firstPredicateCopy =
            new NricContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nricContainsKeywords_returnsTrue() {
        // One keyword
        NricContainsKeywordsPredicate predicate =
            new NricContainsKeywordsPredicate(Collections.singletonList("S9671597H"));
        assertTrue(predicate.test(new PersonBuilder().withNric("S9671597H").build()));

        // Mixed-case keywords
        predicate = new NricContainsKeywordsPredicate(Arrays.asList("s9671597H"));
        assertTrue(predicate.test(new PersonBuilder().withNric("S9671597H").build()));
    }

    @Test
    public void test_nricDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NricContainsKeywordsPredicate predicate = new NricContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withNric("S9671597H").build()));

        // Non-matching keyword
        predicate = new NricContainsKeywordsPredicate(Arrays.asList("A9671597H"));
        assertFalse(predicate.test(new PersonBuilder().withNric("S9671597H").build()));

        // Keywords match other fields, but does not match Nric
        predicate = new NricContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com",
            "Chinese", "Main", "Street", "NUS", "CEO","Male", "A9671597H"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").withEmail("alice@email.com")
            .withNric("S9671597H").withRace("Chinese").withAddress("Main Street")
            .withSchool("NUS").withMajor("CS").withGrade("2.35").withJobsApply("CEO").withPastJobs("SE")
            .withGender("Female").build()));
    }
}
