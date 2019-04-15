package seedu.address.logic.commands.comparators;

import static seedu.address.logic.commands.SortCommand.ORDER_ASC;

import java.util.Comparator;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortCommand.Order;
import seedu.address.model.restaurant.Restaurant;

/**
 * Implements Comparator for sorting {@code Restaurant} based on its {@code AvgRating}
 */
public class SortRating implements Comparator<Restaurant> {
    private Order order;

    /**
     * Initialise the set of avgRatings
     */
    public SortRating(Order order) {
        this.order = order;
    }

    /**
     * Compares two restaurants to determine which should be before the other.
     * @param firstRestaurant {@code Restaurant} of the first restaurant
     * @param secondRestaurant {@code Restaurant} of the second restaurant
     * @return -1 if firstRestaurant is to be before secondRestaurant and
     * 1 if firstRestaurant is to be after secondRestaurant
     */
    @Override
    public int compare(Restaurant firstRestaurant, Restaurant secondRestaurant) {
        float ratingFirst = firstRestaurant.getSummary().getAvgRating();
        float ratingSecond = secondRestaurant.getSummary().getAvgRating();

        // For ascending order
        if (order.equals(new SortCommand.Order(ORDER_ASC))) {
            if (ratingFirst - ratingSecond > 0) {
                return 1;
            } else if (ratingFirst - ratingSecond < 0) {
                return -1;
            } else {
                return 0;
            }
        } else {
            // For descending order
            if (ratingFirst - ratingSecond < 0) {
                return 1;
            } else if (ratingFirst - ratingSecond > 0) {
                return -1;
            } else {
                return 0;
            }
        }

    }
}
