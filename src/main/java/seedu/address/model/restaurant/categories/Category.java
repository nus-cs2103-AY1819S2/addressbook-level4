package seedu.address.model.restaurant.categories;

import javafx.scene.control.Label;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.restaurant.Restaurant;

/**
 * Encapsulates the categories of a restaurant.
 */
public class Category {
    private final Optional<Cuisine> cuisine;
    private final Optional<Occasion> occasion;
    private final Optional<PriceRange> priceRange;

    public Category(Cuisine cuisine, Occasion occasion, PriceRange priceRange) {
        this.cuisine = Optional.ofNullable(cuisine);
        this.occasion = Optional.ofNullable(occasion);
        this.priceRange = Optional.ofNullable(priceRange);
    }

    public Category(Optional<Cuisine> cuisine, Optional<Occasion> occasion, Optional<PriceRange> priceRange) {
        this.cuisine = cuisine;
        this.occasion = occasion;
        this.priceRange = priceRange;
    }

    public Optional<Cuisine> getCuisine() {
        return this.cuisine;
    }

    public Optional<Occasion> getOccasion() {
        return this.occasion;
    }

    public Optional<PriceRange> getPriceRange() { return this.priceRange; }

    public static Category empty() {
        return new Category(Optional.empty(), Optional.empty(), Optional.empty());
    }

    /**
     * Merge the updated category with the old category.
     * Ensures previous category fields are retained if updated fields are not present.
     */
    public static Category merge(Category previous, Category updated) {
        Optional<Cuisine> mergedCuisine = updated.cuisine.or(() -> previous.cuisine);
        Optional<Occasion> mergedOccasion = updated.occasion.or(() -> previous.occasion);
        Optional<PriceRange> mergedPriceRange = updated.priceRange.or(() -> previous.priceRange);
        return new Category(mergedCuisine, mergedOccasion, mergedPriceRange);
    }

    /**
     * Checks if the given keyword matches any of the categories
     */
    public boolean match(String keyword) {
        boolean matchesCuisine = StringUtil
                .containsWordIgnoreCase(cuisine.map(content -> content.value).orElse(""), keyword);
        boolean matchesOccasion = StringUtil
                .containsWordIgnoreCase(occasion.map(content -> content.value).orElse(""), keyword);
        boolean matchesPriceRange = StringUtil
                .containsWordIgnoreCase(priceRange.map(content -> content.value).orElse(""), keyword);
        return matchesCuisine || matchesOccasion || matchesPriceRange;
    }

    public void setLabels(Label cuisineLabel, Label occasionLabel, Label priceRangeLabel) {
        cuisine.ifPresentOrElse(content -> cuisineLabel.setText(content.value), () -> cuisineLabel.setVisible(false));
        occasion.ifPresentOrElse(content -> occasionLabel.setText(content.value),
                () -> priceRangeLabel.setVisible(false));
        priceRange.ifPresentOrElse(content -> priceRangeLabel.setText(content.value),
                () -> priceRangeLabel.setVisible(false));
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof Category // instanceof handles nulls
                && cuisine.equals(((Category) obj).cuisine))
                && occasion.equals(((Category) obj).occasion)
                && priceRange.equals(((Category) obj).priceRange); // state check
    }

    @Override
    public String toString() {
        String cuisineString = cuisine.isPresent()
                ? cuisine.map(content -> "(cuisine)" + content.toString()).get() : "";
        String occasionString = occasion.isPresent()
                ? occasion.map(content -> "(occasion)" + content.toString()).get() : "";
        String priceRangeString = priceRange.isPresent()
                ? priceRange.map(content -> "(price range)" + content.toString()).get() : "";
        return cuisineString + " " + occasionString + " " + priceRangeString;
    }
}
