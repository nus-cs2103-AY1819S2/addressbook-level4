package seedu.address.model.flashcard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.flashcard.FlashcardContainsKeywordsPredicate.IS_FRONT_FACE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.FlashcardBuilder;

public class FaceContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");


        FaceContainsKeywordsPredicate firstPredicate = new FaceContainsKeywordsPredicate(firstPredicateKeywordList,
                IS_FRONT_FACE);
        FaceContainsKeywordsPredicate secondPredicate = new FaceContainsKeywordsPredicate(secondPredicateKeywordList,
                IS_FRONT_FACE);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FaceContainsKeywordsPredicate firstPredicateCopy = new FaceContainsKeywordsPredicate(firstPredicateKeywordList,
                IS_FRONT_FACE);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different flashcard -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        FaceContainsKeywordsPredicate frontFacePredicate;
        FaceContainsKeywordsPredicate backFacePredicate;

        // One keyword
        frontFacePredicate = new FaceContainsKeywordsPredicate(Collections.singletonList("Hello"), IS_FRONT_FACE);
        assertTrue(frontFacePredicate.test(new FlashcardBuilder().withFrontFace("Hello").build()));

        // Substring keywords
        backFacePredicate = new FaceContainsKeywordsPredicate(Arrays.asList("Hell"), !IS_FRONT_FACE);
        frontFacePredicate = new FaceContainsKeywordsPredicate(Arrays.asList("Hell"), IS_FRONT_FACE);
        assertTrue(frontFacePredicate.test(new FlashcardBuilder().withFrontFace("Hello Hola").build()));
        assertTrue(backFacePredicate.test(new FlashcardBuilder().withBackFace("Hello Hola").build()));

        // Multiple keywords
        backFacePredicate = new FaceContainsKeywordsPredicate(Arrays.asList("Hello", "Hola"), !IS_FRONT_FACE);
        frontFacePredicate = new FaceContainsKeywordsPredicate(Arrays.asList("Hello", "Hola"), IS_FRONT_FACE);
        assertTrue(frontFacePredicate.test(new FlashcardBuilder().withFrontFace("Hello Hola").build()));
        assertTrue(backFacePredicate.test(new FlashcardBuilder().withBackFace("Hello Hola").build()));

        // Only one matching keyword
        assertTrue(frontFacePredicate.test(new FlashcardBuilder().withFrontFace("Halo Hola").build()));
        assertTrue(backFacePredicate.test(new FlashcardBuilder().withBackFace("Halo Hola").build()));

        // Mixed-case keywords
        frontFacePredicate = new FaceContainsKeywordsPredicate(Arrays.asList("hEllO", "hOla"), IS_FRONT_FACE);
        backFacePredicate = new FaceContainsKeywordsPredicate(Arrays.asList("hEllO", "hOla"), !IS_FRONT_FACE);
        assertTrue(frontFacePredicate.test(new FlashcardBuilder().withFrontFace("Hello Holla").build()));
        assertTrue(backFacePredicate.test(new FlashcardBuilder().withBackFace("Hello Holla").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FaceContainsKeywordsPredicate predicate = new FaceContainsKeywordsPredicate(Collections.emptyList(),
                IS_FRONT_FACE);
        assertFalse(predicate.test(new FlashcardBuilder().withFrontFace("Good").build()));

        // Non-matching keyword
        predicate = new FaceContainsKeywordsPredicate(Collections.singletonList("Bad"), IS_FRONT_FACE);
        assertFalse(predicate.test(new FlashcardBuilder().withFrontFace("Good").build()));
    }
}
