package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code GradTrak} that keeps track of its own history.
 */
public class VersionedGradTrak extends GradTrak {

    private final List<ReadOnlyGradTrak> gradTrakStateList;
    private int currentStatePointer;

    public VersionedGradTrak(ReadOnlyGradTrak initialState) {
        super(initialState);

        gradTrakStateList = new ArrayList<>();
        gradTrakStateList.add(new GradTrak(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code GradTrak} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        gradTrakStateList.add(new GradTrak(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        gradTrakStateList.subList(currentStatePointer + 1, gradTrakStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(gradTrakStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(gradTrakStateList.get(currentStatePointer));
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
        return currentStatePointer < gradTrakStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedGradTrak)) {
            return false;
        }

        VersionedGradTrak otherVersionedAddressBook = (VersionedGradTrak) other;

        // state check
        return super.equals(otherVersionedAddressBook)
                && gradTrakStateList.equals(otherVersionedAddressBook.gradTrakStateList)
                && currentStatePointer == otherVersionedAddressBook.currentStatePointer;
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
