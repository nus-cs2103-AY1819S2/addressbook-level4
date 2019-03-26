package seedu.address.model.person.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PastJobContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PastJobContainsKeywordsPredicate firstPredicate =
            new PastJobContainsKeywordsPredicate(firstPredicateKeywordList);
        PastJobContainsKeywordsPredicate secondPredicate =
            new PastJobContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PastJobContainsKeywordsPredicate firstPredicateCopy =
            new PastJobContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_pastJobContainsKeywords_returnsTrue() {
        // One keyword
        PastJobContainsKeywordsPredicate predicate =
            new PastJobContainsKeywordsPredicate(Collections.singletonList("SE"));
        assertTrue(predicate.test(new PersonBuilder().withPastJobs("SE").build()));

        // Mixed-case keywords
        predicate = new PastJobContainsKeywordsPredicate(Arrays.asList("sE"));
        assertTrue(predicate.test(new PersonBuilder().withPastJobs("SE").build()));
    }

    @Test
    public void test_pastJobDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PastJobContainsKeywordsPredicate predicate = new PastJobContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPastJobs("SE").build()));

        // Non-matching keyword
        predicate = new PastJobContainsKeywordsPredicate(Arrays.asList("SE"));
        assertFalse(predicate.test(new PersonBuilder().withPastJobs("CEO").build()));

        // Keywords match other fields, but does not match PastJob
        predicate = new PastJobContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com",
            "Chinese", "Main", "Street", "NUS", "CEO"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").withEmail("alice@email.com")
            .withNric("S9091209Q").withGender("Female").withRace("Chinese").withAddress("Main Street")
            .withSchool("NUS").withMajor("CS").withGrade("2.35").withJobsApply("CEO").withPastJobs("SE").build()));
    }
}
