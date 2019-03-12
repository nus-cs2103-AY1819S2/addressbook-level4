package seedu.address.model.tag;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.equipment.Equipment;

/**
 * Tests that a {@code Tag}'s {@code Name} matches any of the keywords given.
 */

public class TagsContainsKeywordsPredicate implements Predicate<Equipment> {
    private final List<String> keywords;

    public TagsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test(Equipment equipment) {
        Set<Tag> tags = equipment.getTags();
        if (keywords.isEmpty()) {
            return false;
        }
        return keywords.stream()
                .anyMatch(keyword -> tags.stream().anyMatch(tag ->
                        StringUtil.containsWordIgnoreCase(tag.getTagName(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagsContainsKeywordsPredicate) other).keywords)); // state check
    }
}
