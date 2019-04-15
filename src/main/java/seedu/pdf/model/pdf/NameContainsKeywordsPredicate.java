package seedu.pdf.model.pdf;

import java.util.List;
import java.util.function.Predicate;

import seedu.pdf.commons.util.StringUtil;

/**
 * Tests that a {@code Pdf}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Pdf> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Pdf pdf) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(pdf.getName().getFullName(), keyword)
                                || StringUtil.containsWordInContent(pdf, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
