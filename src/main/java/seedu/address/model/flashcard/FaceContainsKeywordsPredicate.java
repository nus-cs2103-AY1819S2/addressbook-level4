package seedu.address.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Flashcard}'s {@code Face} matches any of the keywords given.
 */
public class FaceContainsKeywordsPredicate implements Predicate<Flashcard> {
    private final List<String> keywords;
    private final boolean isFrontFace;

    public FaceContainsKeywordsPredicate(List<String> keywords, boolean isFrontFace) {
        this.keywords = keywords;
        this.isFrontFace = isFrontFace;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        if (isFrontFace) {
            return keywords.stream()
                    .anyMatch(keyword ->
                            containsSubstring(flashcard.getFrontFace().text, keyword));
        } else {
            return keywords.stream()
                    .anyMatch(keyword ->
                            containsSubstring(flashcard.getBackFace().text, keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FaceContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((FaceContainsKeywordsPredicate) other).keywords)); // state check
    }

    /**
     * check if keyword is a substring of s.
     *
     * @param s
     * @param keyword
     * @return true if `keyword` is a substring of `s`
     */
    private boolean containsSubstring(String s, String keyword) {
        for (int i = 0; i + keyword.length() - 1 < s.length(); i++) {
            String sub = s.substring(i, i + keyword.length());

            if (sub.equalsIgnoreCase(keyword)) {
                return true;
            }
        }
        return false;
    }

}
