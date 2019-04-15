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

public class InterviewScoreContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        InterviewScoreContainsKeywordsPredicate firstPredicate =
            new InterviewScoreContainsKeywordsPredicate(1, firstPredicateKeywordList);
        InterviewScoreContainsKeywordsPredicate secondPredicate =
            new InterviewScoreContainsKeywordsPredicate(2, firstPredicateKeywordList);
        InterviewScoreContainsKeywordsPredicate thirdPredicate =
            new InterviewScoreContainsKeywordsPredicate(1, secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        InterviewScoreContainsKeywordsPredicate firstPredicateCopy =
            new InterviewScoreContainsKeywordsPredicate(1, firstPredicateKeywordList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(firstPredicate, 1);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different person -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_interviewContainsKeywords_returnsTrue() {
        // One keyword
        InterviewScoreContainsKeywordsPredicate predicate =
            new InterviewScoreContainsKeywordsPredicate(1, Collections.singletonList("4-6"));
        assertTrue(predicate.test(new PersonBuilder().withInterviewScores("5,5,5,5,5").build()));

    }

    @Test
    public void test_interviewDoesNotContainKeywords_returnsFalse() {
        InterviewScoreContainsKeywordsPredicate predicate;
        // Non-matching keyword
        predicate = new InterviewScoreContainsKeywordsPredicate(1, Collections.singletonList("3-4"));
        assertFalse(predicate.test(new PersonBuilder().withInterviewScores("5,5,5,5,5").build()));

    }
}
