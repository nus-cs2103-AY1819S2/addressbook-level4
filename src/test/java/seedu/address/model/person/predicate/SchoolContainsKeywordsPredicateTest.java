package seedu.address.model.person.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class SchoolContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        SchoolContainsKeywordsPredicate firstPredicate = new SchoolContainsKeywordsPredicate(firstPredicateKeywordList);
        SchoolContainsKeywordsPredicate secondPredicate = new SchoolContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SchoolContainsKeywordsPredicate firstPredicateCopy = new SchoolContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_schoolContainsKeywords_returnsTrue() {
        // One keyword
        SchoolContainsKeywordsPredicate predicate = new SchoolContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withSchool("Alice Bob").build()));

        // Multiple keywords
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withSchool("Alice Bob").build()));

        // Only one matching keyword
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withSchool("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withSchool("Alice Bob").build()));
    }

    @Test
    public void test_schoolDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SchoolContainsKeywordsPredicate predicate = new SchoolContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSchool("Alice").build()));

        // Non-matching keyword
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withSchool("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match School
        predicate = new SchoolContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com",
                "Chinese", "Main", "Street", "CS"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").withEmail("alice@email.com")
                .withNric("S9091209Q").withGender("Female").withRace("Chinese").withAddress("Main Street")
                .withSchool("NUS").withMajor("CS").withGrade("2.35").build()));
    }
}
