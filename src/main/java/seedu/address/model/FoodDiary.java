package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.UniqueRestaurantList;

/**
 * Wraps all data at the food diary level
 * Duplicates are not allowed (by .isSameRestaurant comparison)
 */
public class FoodDiary implements ReadOnlyFoodDiary {

    private final UniqueRestaurantList restaurants;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        restaurants = new UniqueRestaurantList();
    }

    public FoodDiary() {}

    /**
     * Creates an FoodDiary using the Restaurants in the {@code toBeCopied}
     */
    public FoodDiary(ReadOnlyFoodDiary toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the restaurant list with {@code restaurants}.
     * {@code restaurants} must not contain duplicate restaurants.
     */
    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants.setRestaurants(restaurants);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code FoodDiary} with {@code newData}.
     */
    public void resetData(ReadOnlyFoodDiary newData) {
        requireNonNull(newData);

        setRestaurants(newData.getRestaurantList());
    }

    //// restaurant-level operations

    /**
     * Returns true if a restaurant with the same identity as {@code restaurant} exists in the food diary.
     */
    public boolean hasRestaurant(Restaurant restaurant) {
        requireNonNull(restaurant);
        return restaurants.contains(restaurant);
    }

    /**
     * Adds a restaurant to the food diary.
     * The restaurant must not already exist in the food diary.
     */
    public void addRestaurant(Restaurant p) {
        restaurants.add(p);
        indicateModified();
    }

    /**
     * Replaces the given restaurant {@code target} in the list with {@code editedRestaurant}.
     * {@code target} must exist in the food diary.
     * The restaurant identity of {@code editedRestaurant} must not be the same as another existing restaurant
     * in the food diary.
     */
    public void setRestaurant(Restaurant target, Restaurant editedRestaurant) {
        requireNonNull(editedRestaurant);

        restaurants.setRestaurant(target, editedRestaurant);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code FoodDiary}.
     * {@code key} must exist in the food diary.
     */
    public void removeRestaurant(Restaurant key) {
        restaurants.remove(key);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the food diary has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return restaurants.asUnmodifiableObservableList().size() + " restaurants";
        // TODO: refine later
    }

    @Override
    public ObservableList<Restaurant> getRestaurantList() {
        return restaurants.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FoodDiary // instanceof handles nulls
                && restaurants.equals(((FoodDiary) other).restaurants));
    }

    @Override
    public int hashCode() {
        return restaurants.hashCode();
    }
}
