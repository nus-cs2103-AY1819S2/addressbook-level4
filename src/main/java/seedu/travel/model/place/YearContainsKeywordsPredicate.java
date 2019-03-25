package seedu.travel.model.place;

import java.util.List;
import java.util.function.Predicate;

import seedu.travel.commons.util.StringUtil;

/**
 * Tests that a {@code Place}'s {@code CountryCode} matches any of the keywords given.
 */
public class YearContainsKeywordsPredicate implements Predicate<Place> {

    private final List<String> keywords;

    public YearContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Place place) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsYear(place.getDateVisited().year, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.travel.model.place.YearContainsKeywordsPredicate
                // instanceof handles nulls
                && keywords.equals(((seedu.travel.model.place.YearContainsKeywordsPredicate) other)
                .keywords)); // state check
    }

}
