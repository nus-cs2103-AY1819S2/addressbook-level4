package seedu.address.model.restaurant.categories;

import java.util.Optional;

public class Category {
    private Optional<Cuisine> cuisine;
    private Optional<Occasion> occasion;

    public Category(Cuisine cuisine, Occasion occasion) {
        this.cuisine = Optional.ofNullable(cuisine);
        this.occasion = Optional.ofNullable(occasion);
    }
}
