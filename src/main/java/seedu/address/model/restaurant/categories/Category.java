package seedu.address.model.restaurant.categories;

import java.util.Optional;

/**
 * Encapsulates the categories of a restaurant.
 */
public class Category {
    private final Optional<Cuisine> cuisine;
    private final Optional<Occasion> occasion;

    public Category(Cuisine cuisine, Occasion occasion) {
        this.cuisine = Optional.ofNullable(cuisine);
        this.occasion = Optional.ofNullable(occasion);
    }

    public Optional<Cuisine> getCuisine() {
        return this.cuisine;
    }

    public Optional<Occasion> getOccasion() {
        return this.occasion;
    }

    public static Category empty() {
        return new Category(null, null);
    }

    /**
     * Merge the updated category with the old category.
     * Ensures previous category fields are retained if updated fields are not present.
     */
    public static Category merge(Category previous, Category updated) {
        Cuisine mergedCuisine;
        Occasion mergedOccasion;
        if (updated.cuisine.isPresent()) {
            mergedCuisine = updated.cuisine.get();
        } else {
            mergedCuisine = previous.cuisine.get();
        }

        if (updated.occasion.isPresent()) {
            mergedOccasion = updated.occasion.get();
        } else {
            mergedOccasion = previous.occasion.get();
        }
        return new Category(mergedCuisine, mergedOccasion);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof Category // instanceof handles nulls
                && cuisine.equals(((Category) obj).cuisine))
                && occasion.equals(((Category) obj).occasion); // state check
    }

    @Override
    public String toString() {
        String cuisineString;
        if (cuisine.isPresent()) {
            cuisineString = "(cuisine) " + cuisine.get().toString();
        } else {
            cuisineString = "";
        }
        String occasionString;
        if (occasion.isPresent()) {
            occasionString = "(occasion) " + occasion.get().toString();
        } else {
            occasionString = "";
        }
        return cuisineString + " " + occasionString;
    }
}
