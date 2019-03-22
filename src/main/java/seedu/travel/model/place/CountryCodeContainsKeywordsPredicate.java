package seedu.travel.model.place;

import java.util.List;
import java.util.function.Predicate;

import seedu.travel.commons.util.StringUtil;

/**
 * Tests that a {@code Place}'s {@code CountryCode} matches any of the keywords given.
 */
public class CountryCodeContainsKeywordsPredicate implements Predicate<Place> {

    private final List<String> keywords;

    public CountryCodeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Place place) {
        return keywords.stream()
            .anyMatch(keyword -> StringUtil.containsCountryCode(place.getCountryCode().code, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof seedu.travel.model.place.CountryCodeContainsKeywordsPredicate
            // instanceof handles nulls
            && keywords.equals(((seedu.travel.model.place.CountryCodeContainsKeywordsPredicate) other)
                .keywords)); // state check
    }

}
