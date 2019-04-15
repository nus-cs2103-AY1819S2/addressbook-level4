package seedu.address.model.restaurant.categories;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.restaurant.Restaurant;

/**
 * Tests that a {@code Restaurant}'s {@code Categories} matches any of the keywords given.
 */
public class CategoriesContainKeywordsPredicate implements Predicate<Restaurant> {
    private final List<String> keywords;

    public CategoriesContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Restaurant restaurant) {
        return keywords.stream()
                .anyMatch(keyword -> restaurant.getCategories().match(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CategoriesContainKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CategoriesContainKeywordsPredicate) other).keywords)); // state check
    }
}
