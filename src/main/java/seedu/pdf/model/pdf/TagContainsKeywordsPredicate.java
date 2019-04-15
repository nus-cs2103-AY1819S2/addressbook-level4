package seedu.pdf.model.pdf;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import seedu.pdf.commons.util.StringUtil;
import seedu.pdf.model.tag.Tag;

/**
 * Tests that a {@code Pdf}'s {@code Name} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Pdf> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Pdf pdf) {
        return keywords.stream()
                .anyMatch(keyword -> {
                    Iterator<Tag> itr = pdf.getTags().iterator();
                    while (itr.hasNext()) {
                        if (StringUtil.containsFullWordSameCase(itr.next().tagName, keyword)) {
                            return true;
                        }
                    }
                    return false;
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
