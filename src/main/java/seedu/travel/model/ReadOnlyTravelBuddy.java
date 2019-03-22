package seedu.travel.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.travel.model.place.Place;

/**
 * Unmodifiable view of an travel book
 */
public interface ReadOnlyTravelBuddy extends Observable {

    /**
     * Returns an unmodifiable view of the places list.
     * This list will not contain any duplicate places.
     */
    ObservableList<Place> getPlaceList();

}
