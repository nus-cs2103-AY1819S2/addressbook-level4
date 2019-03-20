package seedu.address.model.flashcard;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Flashcard}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Flashcard> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Flashcard flashcard) {
        boolean match = false;
        Set<Tag> tags = flashcard.getTags();
        for (Tag tag : tags) {
            match = keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword));
            if (match) {
                break;
            }
        }

        return match;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
