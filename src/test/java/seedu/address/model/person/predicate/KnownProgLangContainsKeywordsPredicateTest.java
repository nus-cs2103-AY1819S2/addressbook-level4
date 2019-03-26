package seedu.address.model.person.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class KnownProgLangContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        KnownProgLangContainsKeywordsPredicate firstPredicate =
            new KnownProgLangContainsKeywordsPredicate(firstPredicateKeywordList);
        KnownProgLangContainsKeywordsPredicate secondPredicate =
            new KnownProgLangContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        KnownProgLangContainsKeywordsPredicate firstPredicateCopy =
            new KnownProgLangContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_knownProgLangContainsKeywords_returnsTrue() {
        // One keyword
        KnownProgLangContainsKeywordsPredicate predicate =
            new KnownProgLangContainsKeywordsPredicate(Collections.singletonList("Python"));
        assertTrue(predicate.test(new PersonBuilder().withKnownProgLangs("Python").build()));

        // Mixed-case keywords
        predicate = new KnownProgLangContainsKeywordsPredicate(Arrays.asList("PYthon"));
        assertTrue(predicate.test(new PersonBuilder().withKnownProgLangs("Python").build()));
    }

    @Test
    public void test_knownProgLangDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        KnownProgLangContainsKeywordsPredicate predicate =
            new KnownProgLangContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withKnownProgLangs("Python").build()));

        // Non-matching keyword
        predicate = new KnownProgLangContainsKeywordsPredicate(Arrays.asList("Python"));
        assertFalse(predicate.test(new PersonBuilder().withKnownProgLangs("C++").build()));

        // Keywords match other fields, but does not match KnownProgLang
        predicate = new KnownProgLangContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com",
            "Chinese", "Main", "Street", "NUS", "CEO", "Male", "A9671597H", "Python"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").withEmail("alice@email.com")
            .withNric("S9671597H").withRace("Chinese").withAddress("Main Street")
            .withSchool("NUS").withMajor("CS").withGrade("2.35").withJobsApply("CEO").withPastJobs("SE")
            .withGender("Female").withKnownProgLangs("C++").build()));
    }
}
