package seedu.address.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Flashcard}'s FrontFace OR BackFace OR {@code Tag} matches any of the keywords given.
 * AND that the {@Code Flashcard}'s success rate is within the {@code upperBound} and {@code lowerBound}
 */
public class FlashcardPredicate implements Predicate<Flashcard> {
    public static final boolean IS_FRONT_FACE = true;

    private final List<String> frontFaceKeywords;
    private final List<String> backFaceKeywords;
    private final List<String> tagKeywords;
    private double lowerBound;
    private double upperBound;

    public FlashcardPredicate(List<String> frontFaceKeywords, List<String> backFaceKeywords,
                              List<String> tagKeywords, double[] statRange) {
        this.frontFaceKeywords = frontFaceKeywords;
        this.backFaceKeywords = backFaceKeywords;
        this.tagKeywords = tagKeywords;
        this.lowerBound = statRange[0];
        this.upperBound = statRange[1];
    }

    @Override
    public boolean test(Flashcard flashcard) {
        if (frontFaceKeywords.isEmpty() && backFaceKeywords.isEmpty() && tagKeywords.isEmpty()) {
            return (flashcard.getStatistics().getSuccessRate() * 100 >= lowerBound
                    && flashcard.getStatistics().getSuccessRate() * 100 <= upperBound);
        }
        FaceContainsKeywordsPredicate frontFacePredicate =
                new FaceContainsKeywordsPredicate(frontFaceKeywords, IS_FRONT_FACE);
        FaceContainsKeywordsPredicate backFacePredicate =
                new FaceContainsKeywordsPredicate(backFaceKeywords, !IS_FRONT_FACE);
        TagContainsKeywordsPredicate tagPredicate = new TagContainsKeywordsPredicate(tagKeywords);

        return ((frontFacePredicate.test(flashcard) || backFacePredicate.test(flashcard)
                || tagPredicate.test(flashcard)) && (flashcard.getStatistics().getSuccessRate() * 100 >= lowerBound
                && flashcard.getStatistics().getSuccessRate() * 100 <= upperBound));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FlashcardPredicate // instanceof handles nulls
                && frontFaceKeywords.equals(((FlashcardPredicate) other).frontFaceKeywords)
                && backFaceKeywords.equals(((FlashcardPredicate) other).backFaceKeywords)
                && tagKeywords.equals(((FlashcardPredicate) other).tagKeywords)
                && lowerBound == (((FlashcardPredicate) other).lowerBound)
                && upperBound == (((FlashcardPredicate) other).upperBound)); // state check
    }

}
