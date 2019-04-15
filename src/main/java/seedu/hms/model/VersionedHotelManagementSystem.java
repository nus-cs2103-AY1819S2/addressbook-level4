package seedu.hms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code HotelManagementSystem} that keeps track of its own history.
 */
public class VersionedHotelManagementSystem extends HotelManagementSystem {

    private final List<ReadOnlyHotelManagementSystem> hotelManagementSystemStateList;
    private int currentStatePointer;

    public VersionedHotelManagementSystem(ReadOnlyHotelManagementSystem initialState) {
        super(initialState);

        hotelManagementSystemStateList = new ArrayList<>();
        hotelManagementSystemStateList.add(new HotelManagementSystem(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code HotelManagementSystem} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        hotelManagementSystemStateList.add(new HotelManagementSystem(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        hotelManagementSystemStateList.subList(currentStatePointer + 1, hotelManagementSystemStateList.size()).clear();
    }

    /**
     * Restores the hms book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(hotelManagementSystemStateList.get(currentStatePointer));
    }

    /**
     * Restores the hms book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(hotelManagementSystemStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has hms book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has hms book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < hotelManagementSystemStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedHotelManagementSystem)) {
            return false;
        }

        VersionedHotelManagementSystem otherVersionedHotelManagementSystem = (VersionedHotelManagementSystem) other;

        // state check
        return super.equals(otherVersionedHotelManagementSystem)
            && hotelManagementSystemStateList.equals(otherVersionedHotelManagementSystem.hotelManagementSystemStateList)
            && currentStatePointer == otherVersionedHotelManagementSystem.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of hotelManagementSystemState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of hotelManagementSystemState list, unable to redo.");
        }
    }
}
