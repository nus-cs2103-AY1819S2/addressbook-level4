package systemtests;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.restaurant.Restaurant;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Restaurant> PREDICATE_MATCHING_NO_RESTAURANTS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Restaurant> toDisplay) {
        Optional<Predicate<Restaurant>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredRestaurantList(predicate.orElse(PREDICATE_MATCHING_NO_RESTAURANTS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Restaurant... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }


    /**
     * Returns a predicate that evaluates to true if this {@code Restaurant} equals to {@code other}.
     */
    private static Predicate<Restaurant> getPredicateMatching(Restaurant other) {
        return restaurant -> restaurant.equals(other);
    }

    public static void setSortedList(Model model, List<Restaurant> toDisplay) {
        Comparator<Restaurant> comparator = getComparatorMatching(toDisplay);
        model.sortRestaurantList(comparator);
    }

    public static void setSortedList(Model model, Restaurant... toDisplay) {
        setSortedList(model, Arrays.asList(toDisplay));
    }


    private static Comparator<Restaurant> getComparatorMatching(List<Restaurant> restaurantList) {
        return new Comparator<>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                return restaurantList.indexOf(r1) - restaurantList.indexOf(r2);
            }
        };
    }
}
