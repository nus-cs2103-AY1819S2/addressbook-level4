package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ModuleTakenBuilder;

public class ModuleInfoCodeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different moduleTaken -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("CS2103T"));
        assertTrue(predicate.test(new ModuleTakenBuilder().withModuleInfoCode("CS2103T").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("CS2030", "CS2103T"));
        assertTrue(predicate.test(new ModuleTakenBuilder().withModuleInfoCode("CS2103T").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("CS2040", "CS2103T"));
        assertTrue(predicate.test(new ModuleTakenBuilder().withModuleInfoCode("CS2040").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("cs2103T", "cs2040C"));
        assertTrue(predicate.test(new ModuleTakenBuilder().withModuleInfoCode("CS2040C").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ModuleTakenBuilder().withModuleInfoCode("CS2040").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("CS2010"));
        assertFalse(predicate.test(new ModuleTakenBuilder().withModuleInfoCode("CS2101").build()));

        // Keywords match semester, min & max expected grade, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("cs2012", "CS2103",
                "DEFAULT_MODULE_CS1010", "cs2040"));
        assertFalse(predicate.test(new ModuleTakenBuilder().withModuleInfoCode("CS2101").withSemester("Y2S2")
                .withExpectedMinGrade("C").withExpectedMaxGrade("B").build()));
    }
}
