package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.restaurant.Restaurant;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook extends Observable {

    /**
     * Returns an unmodifiable view of the restaurants list.
     * This list will not contain any duplicate restaurants.
     */
    ObservableList<Restaurant> getRestaurantList();

}
