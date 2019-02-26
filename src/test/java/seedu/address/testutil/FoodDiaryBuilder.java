package seedu.address.testutil;

import seedu.address.model.FoodDiary;
import seedu.address.model.restaurant.Restaurant;

/**
 * A utility class to help with building FoodDiary objects.
 * Example usage: <br>
 *     {@code FoodDiary ab = new FoodDiaryBuilder().withRestaurant("John", "Doe").build();}
 */
public class FoodDiaryBuilder {

    private FoodDiary foodDiary;

    public FoodDiaryBuilder() {
        foodDiary = new FoodDiary();
    }

    public FoodDiaryBuilder(FoodDiary foodDiary) {
        this.foodDiary = foodDiary;
    }

    /**
     * Adds a new {@code Restaurant} to the {@code FoodDiary} that we are building.
     */
    public FoodDiaryBuilder withRestaurant(Restaurant restaurant) {
        foodDiary.addRestaurant(restaurant);
        return this;
    }

    public FoodDiary build() {
        return foodDiary;
    }
}
