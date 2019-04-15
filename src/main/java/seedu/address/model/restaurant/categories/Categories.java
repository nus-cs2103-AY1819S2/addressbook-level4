package seedu.address.model.restaurant.categories;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CUISINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_RANGE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Label;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.Prefix;

/**
 * Encapsulates the categories of a restaurant.
 */
public class Categories {
    private final Optional<Cuisine> cuisine;
    private final Optional<Occasion> occasion;
    private final Optional<PriceRange> priceRange;

    public Categories(Cuisine cuisine, Occasion occasion, PriceRange priceRange) {
        this.cuisine = Optional.ofNullable(cuisine);
        this.occasion = Optional.ofNullable(occasion);
        this.priceRange = Optional.ofNullable(priceRange);
    }

    public Categories(Optional<Cuisine> cuisine, Optional<Occasion> occasion, Optional<PriceRange> priceRange) {
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

    public Optional<PriceRange> getPriceRange() {
        return this.priceRange;
    }

    public static Categories empty() {
        return new Categories(Optional.empty(), Optional.empty(), Optional.empty());
    }


    /**
     * Gets a list of suggestions based on the entered prefix.
     * @param prefix The prefix decides which type of category (Cuisine, Occasion, Price) are suggestions
     *               retrieved from.
     */
    public static List<String> getCategoriesSuggestions(Prefix prefix) {
        if (prefix.equals(PREFIX_CUISINE)) {
            return Cuisine.SUGGESTED_CUISINES;
        } else if (prefix.equals(PREFIX_OCCASION)) {
            return Occasion.SUGGESTED_OCCASIONS;
        } else if (prefix.equals(PREFIX_PRICE_RANGE)) {
            return PriceRange.SUGGESTED_PRICE_RANGES;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Merge the updated category with the old category.
     * Ensures previous category fields are retained if updated fields are not present.
     */
    public static Categories merge(Categories previous, Categories updated) {
        assert(previous != null);
        assert(updated != null);
        Optional<Cuisine> mergedCuisine = updated.cuisine.or(() -> previous.cuisine);
        Optional<Occasion> mergedOccasion = updated.occasion.or(() -> previous.occasion);
        Optional<PriceRange> mergedPriceRange = updated.priceRange.or(() -> previous.priceRange);
        return new Categories(mergedCuisine, mergedOccasion, mergedPriceRange);
    }

    /**
     * Checks if the given keyword matches any of the categories.
     */
    public boolean match(String keyword) {
        assert(keyword != null);
        boolean matchesCuisine = StringUtil
                .containsWordIgnoreCase(cuisine.map(content -> content.value).orElse(""), keyword);
        boolean matchesOccasion = StringUtil
                .containsWordIgnoreCase(occasion.map(content -> content.value).orElse(""), keyword);
        boolean matchesPriceRange = StringUtil
                .containsWordIgnoreCase(priceRange.map(content -> content.value).orElse(""), keyword);
        return matchesCuisine || matchesOccasion || matchesPriceRange;
    }

    /**
     * Sets the labels in the UI with the value of each category if present. Else toggles visibility of label to false.
     */
    public void setLabels(Label cuisineLabel, Label occasionLabel, Label priceRangeLabel) {
        assert(cuisineLabel != null);
        assert(occasionLabel != null);
        assert(priceRangeLabel != null);
        cuisine.ifPresentOrElse(content -> cuisineLabel.setText(content.value), () -> cuisineLabel.setVisible(false));
        occasion.ifPresentOrElse(content -> occasionLabel.setText(content.value), () ->
                occasionLabel.setVisible(false));
        priceRange.ifPresentOrElse(content -> priceRangeLabel.setText(content.value), () ->
                priceRangeLabel.setVisible(false));
    }

    public boolean isEmpty() {
        return !cuisine.isPresent() && !occasion.isPresent() && !priceRange.isPresent();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof Categories // instanceof handles nulls
                && cuisine.equals(((Categories) obj).cuisine))
                && occasion.equals(((Categories) obj).occasion)
                && priceRange.equals(((Categories) obj).priceRange); // state check
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
