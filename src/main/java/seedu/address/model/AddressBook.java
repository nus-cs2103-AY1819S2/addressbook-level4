package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.place.Place;
import seedu.address.model.place.UniquePlaceList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePlace comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePlaceList places;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        places = new UniquePlaceList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Places in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the place list with {@code places}.
     * {@code places} must not contain duplicate places.
     */
    public void setPlaces(List<Place> places) {
        this.places.setPlaces(places);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setPlaces(newData.getPlaceList());
    }

    //// place-level operations

    /**
     * Returns true if a place with the same identity as {@code place} exists in the address book.
     */
    public boolean hasPlace(Place place) {
        requireNonNull(place);
        return places.contains(place);
    }

    /**
     * Adds a place to the address book.
     * The place must not already exist in the address book.
     */
    public void addPlace(Place p) {
        places.add(p);
        indicateModified();
    }

    /**
     * Replaces the given place {@code target} in the list with {@code editedPlace}.
     * {@code target} must exist in the address book.
     * The place identity of {@code editedPlace} must not be the same as another existing place in the address book.
     */
    public void setPlace(Place target, Place editedPlace) {
        requireNonNull(editedPlace);

        places.setPlace(target, editedPlace);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePlace(Place key) {
        places.remove(key);
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
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return places.asUnmodifiableObservableList().size() + " places";
        // TODO: refine later
    }

    @Override
    public ObservableList<Place> getPlaceList() {
        return places.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && places.equals(((AddressBook) other).places));
    }

    @Override
    public int hashCode() {
        return places.hashCode();
    }
}
