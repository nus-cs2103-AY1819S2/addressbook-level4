package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code DocX} that keeps track of its own history.
 */
public class VersionedDocX extends DocX {

    private final List<ReadOnlyDocX> docXStateList;
    private int currentStatePointer;

    public VersionedDocX(ReadOnlyDocX initialState) {
        super(initialState);

        docXStateList = new ArrayList<>();
        docXStateList.add(new DocX(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code DocX} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        docXStateList.add(new DocX(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        docXStateList.subList(currentStatePointer + 1, docXStateList.size()).clear();
    }

    /**
     * Restores the docX to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(docXStateList.get(currentStatePointer));
    }

    /**
     * Restores the docX to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(docXStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has docX states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has docX states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < docXStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedDocX)) {
            return false;
        }

        VersionedDocX otherVersioneddocX = (VersionedDocX) other;

        // state check
        return super.equals(otherVersioneddocX)
                && docXStateList.equals(otherVersioneddocX.docXStateList)
                && currentStatePointer == otherVersioneddocX.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of docXState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of docXState list, unable to redo.");
        }
    }
}
