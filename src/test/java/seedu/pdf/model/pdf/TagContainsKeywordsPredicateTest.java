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

public class TagContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different pdf -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(
                Collections.singletonList("CS2103T"));
        assertTrue(predicate.test(new PdfBuilder().withTags("CS2103T", "w9").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("CS2103T", "w9"));
        assertTrue(predicate.test(new PdfBuilder().withTags("CS2103T", "SEROCKS").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("CS2103T", "SEROCKS"));
        assertTrue(predicate.test(new PdfBuilder().withTags("SEROCKS").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PdfBuilder().withTags("SEROCKS").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("CS2107"));
        assertFalse(predicate.test(new PdfBuilder().withTags("CS2103T").build()));

        // Multiple keywords, but none match
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("SEROCKS", "CS2103T", "PDF"));
        assertFalse(predicate.test(new PdfBuilder().withTags("ITHINKIGETTINGAB").withSize("12345")
                .withDirectory(DIR_1_VALID).withDeadline(DEADLINE_JSON_DONE).build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("cS2103t", "lEctUre"));
        assertFalse(predicate.test(new PdfBuilder().withTags("CS2103T").build()));
    }
}
