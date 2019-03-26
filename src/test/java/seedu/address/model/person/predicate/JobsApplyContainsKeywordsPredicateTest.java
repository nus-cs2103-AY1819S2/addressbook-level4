package seedu.address.model.person.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class JobsApplyContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        JobsApplyContainsKeywordsPredicate firstPredicate =
            new JobsApplyContainsKeywordsPredicate(firstPredicateKeywordList);
        JobsApplyContainsKeywordsPredicate secondPredicate =
            new JobsApplyContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        JobsApplyContainsKeywordsPredicate firstPredicateCopy =
            new JobsApplyContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_jobsApplyContainsKeywords_returnsTrue() {
        // One keyword
        JobsApplyContainsKeywordsPredicate predicate =
            new JobsApplyContainsKeywordsPredicate(Collections.singletonList("SE"));
        assertTrue(predicate.test(new PersonBuilder().withJobsApply("SE").build()));

        // Mixed-case keywords
        predicate = new JobsApplyContainsKeywordsPredicate(Arrays.asList("sE"));
        assertTrue(predicate.test(new PersonBuilder().withJobsApply("SE").build()));
    }

    @Test
    public void test_jobsApplyDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        JobsApplyContainsKeywordsPredicate predicate = new JobsApplyContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withJobsApply("SE").build()));

        // Non-matching keyword
        predicate = new JobsApplyContainsKeywordsPredicate(Arrays.asList("SE"));
        assertFalse(predicate.test(new PersonBuilder().withJobsApply("CEO").build()));

        // Keywords match other fields, but does not match JobsApply
        predicate = new JobsApplyContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com",
            "Chinese", "Main", "Street", "NUS"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").withEmail("alice@email.com")
            .withNric("S9091209Q").withGender("Female").withRace("Chinese").withAddress("Main Street")
            .withSchool("NUS").withMajor("CS").withGrade("2.35").withJobsApply("CEO").build()));
    }
}
