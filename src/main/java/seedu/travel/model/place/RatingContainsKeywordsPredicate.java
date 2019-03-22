package seedu.travel.model.place;

import java.util.List;
import java.util.function.Predicate;

import seedu.travel.commons.util.StringUtil;

/**
 * Tests that a {@code Place}'s {@code Rating} matches any of the keywords given.
 */
public class RatingContainsKeywordsPredicate implements Predicate<Place> {
    private final List<String> keywords;

    public RatingContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Place place) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsRating(place.getRating().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RatingContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((RatingContainsKeywordsPredicate) other).keywords)); // state check
    }

}
