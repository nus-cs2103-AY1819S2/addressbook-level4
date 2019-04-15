package seedu.address.model.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.ProjectBuilder;

public class ProjectContainsDeadlinePredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "20/11/2019";
        String secondPredicateKeyword = "20/10/2019";

        ProjectContainsDeadlinePredicate firstPredicate = new ProjectContainsDeadlinePredicate(
            firstPredicateKeyword);
        ProjectContainsDeadlinePredicate secondPredicate = new ProjectContainsDeadlinePredicate(
            secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ProjectContainsDeadlinePredicate firstPredicateCopy = new ProjectContainsDeadlinePredicate(
            firstPredicateKeyword);
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

        ProjectContainsDeadlinePredicate predicate = new ProjectContainsDeadlinePredicate("10/10/2019");
        //date earlier than the keyword
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Alice Project")
            .withDeadline("10/10/2019").build()));
        //same date as keyword
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Alice Project")
            .withDeadline("01/10/2019").build()));
    }

    @Test
    public void test_doesNotContainKeywords_returnsFalse() {

        ProjectContainsDeadlinePredicate predicate = new ProjectContainsDeadlinePredicate("20/12/2019");
        //dates later than keyword
        assertFalse(predicate.test(new ProjectBuilder().withProjectName("Alice Project").withDeadline("21/12/2019")
            .build()));
        assertFalse(predicate.test(new ProjectBuilder().withProjectName("Alice Project")
            .withDeadline("20/12/2020").build()));
    }
}
