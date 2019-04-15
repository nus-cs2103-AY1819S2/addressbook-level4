package seedu.address.model.skill;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.EmployeeBuilder;

public class EmployeeSkillContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EmployeeSkillContainsKeywordsPredicate firstPredicate = new EmployeeSkillContainsKeywordsPredicate(
            firstPredicateKeywordList);
        EmployeeSkillContainsKeywordsPredicate secondPredicate = new EmployeeSkillContainsKeywordsPredicate(
            secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmployeeSkillContainsKeywordsPredicate firstPredicateCopy = new EmployeeSkillContainsKeywordsPredicate(
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
    public void test_skillContainsKeywords_returnsTrue() {
        // One keyword
        EmployeeSkillContainsKeywordsPredicate predicate = new EmployeeSkillContainsKeywordsPredicate(
            Collections.singletonList("Java"));
        assertTrue(predicate.test(new EmployeeBuilder().withName("Alice Bob")
            .withSkills("Java", "python").build()));

        // Multiple keywords
        predicate = new EmployeeSkillContainsKeywordsPredicate(Arrays.asList("Java", "Python"));
        assertTrue(predicate.test(new EmployeeBuilder().withName("Alice Bob")
            .withSkills("Java", "Python", "HTML").build()));

        // Only one matching keyword
        predicate = new EmployeeSkillContainsKeywordsPredicate(Arrays.asList("Java", "HTML"));
        assertTrue(predicate.test(new EmployeeBuilder().withName("Alice Carol")
            .withSkills("Java", "Python").build()));

        // Mixed-case keywords
        predicate = new EmployeeSkillContainsKeywordsPredicate(Arrays.asList("jAVa", "PYtHon"));
        assertTrue(predicate.test(new EmployeeBuilder().withName("Alice Bob")
            .withSkills("java", "python").build()));
    }

    @Test
    public void test_skillDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmployeeSkillContainsKeywordsPredicate predicate = new EmployeeSkillContainsKeywordsPredicate(
            Collections.emptyList());
        assertFalse(predicate.test(new EmployeeBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new EmployeeSkillContainsKeywordsPredicate(Arrays.asList("CSS"));
        assertFalse(predicate.test(new EmployeeBuilder().withName("Alice Bob").withSkills("Java", "Python").build()));
    }
}
