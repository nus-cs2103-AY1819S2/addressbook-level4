package seedu.travel.model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.travel.model.chart.Chart;
import seedu.travel.model.place.Place;

/**
 * {@code TravelBuddy} that keeps track of its own history.
 */
public class VersionedTravelBuddy extends TravelBuddy {

    private final List<ReadOnlyTravelBuddy> travelBuddyStateList;
    private int currentStatePointer;

    VersionedTravelBuddy(ReadOnlyTravelBuddy initialState) {
        super(initialState);

        travelBuddyStateList = new ArrayList<>();
        travelBuddyStateList.add(new TravelBuddy(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code TravelBuddy} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    void commit() {
        removeStatesAfterCurrentPointer();
        travelBuddyStateList.add(new TravelBuddy(this));
        currentStatePointer++;
        commitChart();
        indicateModified();
    }

    void commitChart() {
        ObservableList<Place> placeList = travelBuddyStateList.get(travelBuddyStateList.size() - 1).getPlaceList();
        new Chart(placeList).indicateModified();
        //indicateModified();
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
    boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has travel book states to redo.
     */
    boolean canRedo() {
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
