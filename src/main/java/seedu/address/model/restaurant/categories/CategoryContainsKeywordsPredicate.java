package seedu.address.model.restaurant.categories;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.restaurant.Restaurant;

/**
 * Tests that a {@code Restaurant}'s {@code Category} matches any of the keywords given.
 */
public class CategoryContainsKeywordsPredicate implements Predicate<Restaurant> {
    private final List<String> keywords;

    public CategoryContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Restaurant restaurant) {
        return keywords.stream()
                .anyMatch(keyword -> (restaurant.getCuisine().isPresent()
                            && StringUtil.containsWordIgnoreCase(restaurant.getCuisine().get().value, keyword))
                            || (restaurant.getOccasion().isPresent()
                            && StringUtil.containsWordIgnoreCase(restaurant.getOccasion().get().value, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CategoryContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CategoryContainsKeywordsPredicate) other).keywords)); // state check
    }
}
