package seedu.address.model.pdf;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

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
                .anyMatch(keyword ->
                {
                    Iterator<Tag> itr = pdf.getTags().iterator();
                    while(itr.hasNext()){
                        if(StringUtil.containsFullWordSameCase(itr.next().tagName, keyword)) {
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
