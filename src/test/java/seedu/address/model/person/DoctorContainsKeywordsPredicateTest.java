package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.person.doctor.DoctorContainsKeywordsPredicate;
import seedu.address.testutil.DoctorBuilder;

public class DoctorContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DoctorContainsKeywordsPredicate firstPredicate =
                new DoctorContainsKeywordsPredicate(firstPredicateKeywordList);
        DoctorContainsKeywordsPredicate secondPredicate =
                new DoctorContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DoctorContainsKeywordsPredicate firstPredicateCopy =
                new DoctorContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different doctor -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_doctorContainsKeywords_returnsTrue() {
        // One keyword
        DoctorContainsKeywordsPredicate predicate =
                new DoctorContainsKeywordsPredicate(Collections.singletonList("acupuncture"));
        assertTrue(predicate.test(new DoctorBuilder().withSpecs("acupuncture", "general").build()));

        // Multiple keywords
        predicate = new DoctorContainsKeywordsPredicate(Arrays.asList("acupucnture", "general"));
        assertTrue(predicate.test(new DoctorBuilder().withSpecs("acupuncture", "general").build()));

        // Only one matching keyword
        predicate = new DoctorContainsKeywordsPredicate(Arrays.asList("general", "massage"));
        assertTrue(predicate.test(new DoctorBuilder().withSpecs("acupuncture", "massage").build()));

        // Mixed-case keywords
        predicate = new DoctorContainsKeywordsPredicate(Arrays.asList("aCuPUncture", "gENEraL"));
        assertTrue(predicate.test(new DoctorBuilder().withSpecs("acupuncture", "general").build()));

        // Keywords match part of name with the same case
        predicate = new DoctorContainsKeywordsPredicate(Arrays.asList("ong"));
        assertTrue(predicate.test(new DoctorBuilder().withName("David Yong").build()));

        // Keywords match part of name with mixed case
        predicate = new DoctorContainsKeywordsPredicate(Arrays.asList("OnG"));
        assertTrue(predicate.test(new DoctorBuilder().withName("Yong Han Qi").build()));

        // Keywords match part of number
        predicate = new DoctorContainsKeywordsPredicate(Arrays.asList("1"));
        assertTrue(predicate.test(new DoctorBuilder().withPhone("91233331").build()));

        // Keywords match part of year of specialisation
        predicate = new DoctorContainsKeywordsPredicate(Arrays.asList("2"));
        assertTrue(predicate.test(new DoctorBuilder().withYear("21").build()));

        // Keywords match name, gender, year and phone, but does not match specialisation
        predicate = new DoctorContainsKeywordsPredicate(
                Arrays.asList("F", "3", "62345678", "Elvina", "Tan"));
        assertTrue(predicate.test(new DoctorBuilder().withName("Elvina Tan")
                .withGender("F").withYear("3").withPhone("62345678")
                .build()));
    }

    @Test
    public void test_doctorDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DoctorContainsKeywordsPredicate predicate =
                new DoctorContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new DoctorBuilder().withSpecs("acupuncture").build()));

        // Non-matching keyword
        predicate = new DoctorContainsKeywordsPredicate(Arrays.asList("surgery"));
        assertFalse(predicate.test(new DoctorBuilder().withSpecs("acupuncture", "general").build()));

    }
}
