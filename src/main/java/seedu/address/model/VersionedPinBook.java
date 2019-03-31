package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code PinBook} that keeps track of its own history.
 */
public class VersionedPinBook extends PinBook {

    private final List<ReadOnlyPinBook> pinBookStateList;
    private int currentStatePointer;

    public VersionedPinBook(ReadOnlyPinBook initialState) {
        super(initialState);

        pinBookStateList = new ArrayList<>();
        pinBookStateList.add(new PinBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code PinBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        pinBookStateList.add(new PinBook(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        pinBookStateList.subList(currentStatePointer + 1, pinBookStateList.size()).clear();
    }

    /**
     * Restores the pin book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(pinBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the pin book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(pinBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has pin book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has pin book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < pinBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedPinBook)) {
            return false;
        }

        VersionedPinBook otherVersionedPinBook = (VersionedPinBook) other;

        // state check
        return super.equals(otherVersionedPinBook)
                && pinBookStateList.equals(otherVersionedPinBook.pinBookStateList)
                && currentStatePointer == otherVersionedPinBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of pinBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of pinBookState list, unable to redo.");
        }
    }
}

