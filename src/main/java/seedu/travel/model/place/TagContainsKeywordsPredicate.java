package seedu.travel.model.place;

import java.util.List;
import java.util.function.Predicate;

import seedu.travel.commons.util.StringUtil;

/**
 * Tests that a {@code Place}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Place> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Place place) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsTagIgnoreCase(place.getTags(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
