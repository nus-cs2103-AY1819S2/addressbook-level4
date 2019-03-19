package seedu.address.model.person.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class MajorContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MajorContainsKeywordsPredicate firstPredicate = new MajorContainsKeywordsPredicate(firstPredicateKeywordList);
        MajorContainsKeywordsPredicate secondPredicate = new MajorContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MajorContainsKeywordsPredicate firstPredicateCopy =
            new MajorContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_majorContainsKeywords_returnsTrue() {
        // One keyword
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate(Collections.singletonList("CS"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("CS").build()));

        // Mixed-case keywords
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("aRt"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("ART").build()));
    }

    @Test
    public void test_majorDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMajor("CS").build()));

        // Non-matching keyword
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("ART"));
        assertFalse(predicate.test(new PersonBuilder().withMajor("CS").build()));

        // Keywords match other fields, but does not match Major
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com",
            "Chinese", "Main", "Street", "NUS"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").withEmail("alice@email.com")
            .withNric("S9091209Q").withGender("Female").withRace("Chinese").withAddress("Main Street")
            .withSchool("NUS").withMajor("CS").withGrade("2.35").build()));
    }
}
