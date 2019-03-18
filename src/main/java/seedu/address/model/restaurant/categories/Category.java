package seedu.address.model.restaurant.categories;

import java.util.Optional;

public class Category {
    private Optional<Cuisine> cuisine;
    private Optional<Occasion> occasion;

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

    @Override
    public String toString() {
        String cuisine_string;
        if (cuisine.isPresent()) {
            cuisine_string = "(cuisine) " + cuisine.get().toString();
        } else {
            cuisine_string = "";
        }
        String occasion_string;
        if (occasion.isPresent()) {
            occasion_string = "(occasion) " + occasion.get().toString();
        } else {
            occasion_string = "";
        }
        return cuisine_string + " " + occasion_string;
    }
}
