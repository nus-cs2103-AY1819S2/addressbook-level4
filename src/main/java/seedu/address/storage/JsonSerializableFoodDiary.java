package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.FoodDiary;
import seedu.address.model.ReadOnlyFoodDiary;
import seedu.address.model.restaurant.Restaurant;

/**
 * An Immutable FoodDiary that is serializable to JSON format.
 */
@JsonRootName(value = "fooddiary")
class JsonSerializableFoodDiary {

    public static final String MESSAGE_DUPLICATE_RESTAURANT = "Restaurants list contains duplicate restaurant(s).";

    private final List<JsonAdaptedRestaurant> restaurants = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFoodDiary} with the given restaurants.
     */
    @JsonCreator
    public JsonSerializableFoodDiary(@JsonProperty("restaurants") List<JsonAdaptedRestaurant> restaurants) {
        this.restaurants.addAll(restaurants);
    }

    /**
     * Converts a given {@code ReadOnlyFoodDiary} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFoodDiary}.
     */
    public JsonSerializableFoodDiary(ReadOnlyFoodDiary source) {
        restaurants.addAll(source.getRestaurantList().stream().map(JsonAdaptedRestaurant::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this food diary into the model's {@code FoodDiary} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FoodDiary toModelType() throws IllegalValueException {
        FoodDiary foodDiary = new FoodDiary();
        for (JsonAdaptedRestaurant jsonAdaptedRestaurant : restaurants) {
            Restaurant restaurant = jsonAdaptedRestaurant.toModelType();
            if (foodDiary.hasRestaurant(restaurant)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RESTAURANT);
            }
            foodDiary.addRestaurant(restaurant);
        }
        return foodDiary;
    }

}
