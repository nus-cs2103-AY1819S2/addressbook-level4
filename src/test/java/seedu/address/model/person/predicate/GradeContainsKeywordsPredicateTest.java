package seedu.address.model.person.predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class GradeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        GradeContainsKeywordsPredicate firstPredicate = new GradeContainsKeywordsPredicate(firstPredicateKeywordList);
        GradeContainsKeywordsPredicate secondPredicate = new GradeContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        GradeContainsKeywordsPredicate firstPredicateCopy =
            new GradeContainsKeywordsPredicate(firstPredicateKeywordList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(firstPredicate, 1);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different person -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_gradeContainsKeywords_returnsTrue() {
        // One keyword
        GradeContainsKeywordsPredicate predicate =
            new GradeContainsKeywordsPredicate(Collections.singletonList("4.20-4.23"));
        assertTrue(predicate.test(new PersonBuilder().withGrade("4.21").build()));

    }

    @Test
    public void test_gradeDoesNotContainKeywords_returnsFalse() {
        GradeContainsKeywordsPredicate predicate;
        // Non-matching keyword
        predicate = new GradeContainsKeywordsPredicate(Collections.singletonList("4.20-4.23"));
        assertFalse(predicate.test(new PersonBuilder().withGrade("4.12").build()));
    }
}
