package seedu.pdf.model.pdf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_JSON_DONE;
import static seedu.pdf.logic.commands.CommandTestUtil.DIR_1_VALID;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.pdf.testutil.PdfBuilder;

public class NameContainsKeywordsPredicateTest {

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

        // different pdf -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("CS2103T"));
        assertTrue(predicate.test(new PdfBuilder().withName("CS2103T Lecture.pdf").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("CS2103T", "Lecture"));
        assertTrue(predicate.test(new PdfBuilder().withName("CS2103T Lecture.pdf").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Your", "Lecture"));
        assertTrue(predicate.test(new PdfBuilder().withName("My Lecture.pdf").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("cS2103t", "lEctUre"));
        assertTrue(predicate.test(new PdfBuilder().withName("CS2103T Lecture.pdf").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PdfBuilder().withName("Resume.pdf").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("CS2107"));
        assertFalse(predicate.test(new PdfBuilder().withName("CS2103T Lecture.pdf").build()));

        // Keywords match directory, deadline and size, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", DIR_1_VALID,
                "Transcript.pdf", DEADLINE_JSON_DONE));
        assertFalse(predicate.test(new PdfBuilder().withName("Resume.pdf").withSize("12345")
                .withDirectory(DIR_1_VALID).withDeadline(DEADLINE_JSON_DONE).build()));
    }
}
