package seedu.travel.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.Rating;

/**
 * {@code TravelBuddy} that keeps track of its own history.
 */
public class VersionedTravelBuddy extends TravelBuddy {

    private final List<ReadOnlyTravelBuddy> travelBuddyStateList;
    private int currentStatePointer;

    public VersionedTravelBuddy(ReadOnlyTravelBuddy initialState) {
        super(initialState);

        travelBuddyStateList = new ArrayList<>();
        travelBuddyStateList.add(new TravelBuddy(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code TravelBuddy} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        travelBuddyStateList.add(new TravelBuddy(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        travelBuddyStateList.subList(currentStatePointer + 1, travelBuddyStateList.size()).clear();
    }

    /**
     * Restores the travel book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(travelBuddyStateList.get(currentStatePointer));
    }

    /**
     * Restores the travel book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(travelBuddyStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has travel book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has travel book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < travelBuddyStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedTravelBuddy)) {
            return false;
        }

        VersionedTravelBuddy otherVersionedTravelBuddy = (VersionedTravelBuddy) other;

        // state check
        return super.equals(otherVersionedTravelBuddy)
                && travelBuddyStateList.equals(otherVersionedTravelBuddy.travelBuddyStateList)
                && currentStatePointer == otherVersionedTravelBuddy.currentStatePointer;
    }

    /**
     * Generates a chart by country.
     */
    protected Map<CountryCode, Integer> generateCountryChart() {
        ObservableList<Place> placeList = travelBuddyStateList.get(travelBuddyStateList.size() - 1).getPlaceList();
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
    protected Map<Rating, Integer> generateRatingChart() {
        ObservableList<Place> placeList = travelBuddyStateList.get(travelBuddyStateList.size() - 1).getPlaceList();
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
            super("Current state pointer at start of travelBuddyState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of travelBuddyState list, unable to redo.");
        }
    }
}
