package seedu.address.model.person.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class RaceContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RaceContainsKeywordsPredicate firstPredicate = new RaceContainsKeywordsPredicate(firstPredicateKeywordList);
        RaceContainsKeywordsPredicate secondPredicate = new RaceContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RaceContainsKeywordsPredicate firstPredicateCopy = new RaceContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_raceContainsKeywords_returnsTrue() {
        // One keyword
        RaceContainsKeywordsPredicate predicate = new RaceContainsKeywordsPredicate
            (Collections.singletonList("Chinese"));
        assertTrue(predicate.test(new PersonBuilder().withRace("Chinese").build()));

        // Mixed-case keywords
        predicate = new RaceContainsKeywordsPredicate(Arrays.asList("ChiNese"));
        assertTrue(predicate.test(new PersonBuilder().withRace("Chinese").build()));
    }

    @Test
    public void test_raceDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RaceContainsKeywordsPredicate predicate = new RaceContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withRace("Chinese").build()));

        // Non-matching keyword
        predicate = new RaceContainsKeywordsPredicate(Arrays.asList("usa"));
        assertFalse(predicate.test(new PersonBuilder().withRace("Chinese").build()));

        // Keywords match other fields, but does not match Race
        predicate = new RaceContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com",
            "Main", "Street", "NUS", "CS"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").withEmail("alice@email.com")
            .withNric("S9091209Q").withGender("Female").withRace("Chinese").withAddress("Main Street")
            .withSchool("NUS").withMajor("CS").withGrade("2.35").build()));
    }
}
