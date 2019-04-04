package seedu.travel.model.place;

import java.util.List;
import java.util.function.Predicate;

import seedu.travel.commons.util.StringUtil;

/**
 * Tests that a {@code Place}'s {@code CountryCode} matches any of the keywords given.
 */
public class YearContainsKeywordsPredicate implements Predicate<Place> {

    private static final String RANGE_DASH_SEPARATOR = "-";
    private static final int RANGE_LOWER_BOUND = 0;
    private static final int RANGE_UPPER_BOUND = 1;

    private final List<String> keywords;
    private boolean isARange = false;

    public YearContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public YearContainsKeywordsPredicate(List<String> keywords, boolean isARange) {
        this.keywords = keywords;
        this.isARange = isARange;
    }

    public String getKeywords() {
        if (isARange) {
            String keywordsRange = "";
            return keywordsRange.concat(keywords.get(RANGE_LOWER_BOUND)).concat(RANGE_DASH_SEPARATOR)
                    .concat(keywords.get(keywords.size() - RANGE_UPPER_BOUND));
        }
        return String.join(" ", keywords);
    }

    @Override
    public boolean test(Place place) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsYear(place.getDateVisited().getYear(), keyword));
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
