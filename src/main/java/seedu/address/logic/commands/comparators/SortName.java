package seedu.address.logic.commands.comparators;

import java.util.Comparator;

import seedu.address.model.restaurant.Restaurant;

/**
 * Implements Comparator for sorting {@code Restaurant} based on its {@code AvgRating}
 */
class SortName implements Comparator<Restaurant> {

    /**
     * Compares two restaurants to determine which should be before the other.
     * @param firstRestaurant {@code Restaurant} of the first restaurant
     * @param secondRestaurant {@code Restaurant} of the second restaurant
     * @return -1 if firstRestaurant is to be before secondRestaurant and
     * 1 if firstRestaurant is to be after secondRestaurant
     */
    @Override
    public int compare(Restaurant firstRestaurant, Restaurant secondRestaurant) {
        String nameFirst = firstRestaurant.getName().toString();
        String nameSecond = secondRestaurant.getName().toString();

        return nameFirst.compareTo(nameSecond);
    }
}
