package seedu.address.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Flashcard}'s {FRONTFACE} OR {BACKFACE} OR {@code Tag} matches any of the keywords given.
 */
public class FlashcardContainsKeywordsPredicate implements Predicate<Flashcard> {
    public static final boolean IS_FRONT_FACE = true;

    private final List<String> frontFaceKeywords;
    private final List<String> backFaceKeywords;
    private final List<String> tagKeywords;

    public FlashcardContainsKeywordsPredicate(List<String> frontFaceKeywords, List<String> backFaceKeywords,
                                              List<String> tagKeywords) {
        this.frontFaceKeywords = frontFaceKeywords;
        this.backFaceKeywords = backFaceKeywords;
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        FaceContainsKeywordsPredicate frontFacePredicate =
                new FaceContainsKeywordsPredicate(frontFaceKeywords, IS_FRONT_FACE);
        FaceContainsKeywordsPredicate backFacePredicate =
                new FaceContainsKeywordsPredicate(backFaceKeywords, !IS_FRONT_FACE);
        TagContainsKeywordsPredicate tagPredicate = new TagContainsKeywordsPredicate(tagKeywords);

        return (frontFacePredicate.test(flashcard) || backFacePredicate.test(flashcard)
                || tagPredicate.test(flashcard));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlashcardContainsKeywordsPredicate // instanceof handles nulls
                && frontFaceKeywords.equals(((FlashcardContainsKeywordsPredicate) other).frontFaceKeywords)
                && backFaceKeywords.equals(((FlashcardContainsKeywordsPredicate) other).backFaceKeywords)
                && tagKeywords.equals(((FlashcardContainsKeywordsPredicate) other).tagKeywords)); // state check
    }

}
