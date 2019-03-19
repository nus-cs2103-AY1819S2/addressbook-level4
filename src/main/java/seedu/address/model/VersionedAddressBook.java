package seedu.address.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.model.place.CountryCode;
import seedu.address.model.place.Place;
import seedu.address.model.place.Rating;

/**
 * {@code AddressBook} that keeps track of its own history.
 */
public class VersionedAddressBook extends AddressBook {

    private final List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);

        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new AddressBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        addressBookStateList.add(new AddressBook(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAddressBook)) {
            return false;
        }

        VersionedAddressBook otherVersionedAddressBook = (VersionedAddressBook) other;

        // state check
        return super.equals(otherVersionedAddressBook)
                && addressBookStateList.equals(otherVersionedAddressBook.addressBookStateList)
                && currentStatePointer == otherVersionedAddressBook.currentStatePointer;
    }

    /**
     * Generates a chart by country.
     */
    public Map<CountryCode, Integer> generateCountryChart() {
        ObservableList<Place> placeList = addressBookStateList.get(addressBookStateList.size() - 1).getPlaceList();
        CountryCode countryCode;
        Map<CountryCode, Integer> mapCountry = new HashMap<>();
        for (Place place : placeList) {
            countryCode = place.getCountryCode();
            if (mapCountry.containsKey(countryCode)) {
                mapCountry.put(countryCode, mapCountry.get(countryCode) + 1);
            } else {
                mapCountry.put(countryCode, 1);
            }
        }
        return mapCountry;
    }

    /**
     * Generates a chart by rating.
     */
    public Map<Rating, Integer> generateRatingChart() {
        ObservableList<Place> placeList = addressBookStateList.get(addressBookStateList.size() - 1).getPlaceList();
        Rating rating;
        Map<Rating, Integer> mapRating = new HashMap<>();
        for (Place place : placeList) {
            rating = place.getRating();
            if (mapRating.containsKey(rating)) {
                mapRating.put(rating, mapRating.get(rating) + 1);
            } else {
                mapRating.put(rating, 1);
            }
        }
        return mapRating;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}
