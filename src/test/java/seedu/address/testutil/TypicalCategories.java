package seedu.address.testutil;

import seedu.address.model.restaurant.categories.Categories;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.model.restaurant.categories.Occasion;
import seedu.address.model.restaurant.categories.PriceRange;

/**
 * A utility class containing a list of {@code Categories} objects to be used in tests.
 */
public class TypicalCategories {
    public static final Categories VALID_ALL_SET = new Categories(new Cuisine("Western"), new Occasion("Fine Dining"),
            new PriceRange("$$$$$"));
    public static final Categories VALID_CUISINE_NOT_SET = new Categories(null, new Occasion("Casual"),
            new PriceRange("$$"));
    public static final Categories VALID_OCCASION_NOT_SET = new Categories(new Cuisine("Thai"), null,
            new PriceRange("$$$"));
    public static final Categories VALID_PRICE_RANGE_NOT_SET = new Categories(new Cuisine("Japanese"),
            new Occasion("Premium"), null);
    public static final Categories EMPTY = Categories.empty();
}
