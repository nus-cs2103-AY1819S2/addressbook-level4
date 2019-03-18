package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ArchiveBook} that keeps track of its own history.
 */
public class VersionedArchiveBook extends ArchiveBook {

    private final List<ReadOnlyArchiveBook> archiveBookStateList;
    private int currentStatePointer;

    public VersionedArchiveBook(ReadOnlyArchiveBook initialState) {
        super(initialState);

        archiveBookStateList = new ArrayList<>();
        archiveBookStateList.add(new ArchiveBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code ArchiveBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        archiveBookStateList.add(new ArchiveBook(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        archiveBookStateList.subList(currentStatePointer + 1, archiveBookStateList.size()).clear();
    }

    /**
     * Restores the archive book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(archiveBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the archive book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(archiveBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has archive book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has archive book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < archiveBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedArchiveBook)) {
            return false;
        }

        VersionedArchiveBook otherVersionedArchiveBook = (VersionedArchiveBook) other;

        // state check
        return super.equals(otherVersionedArchiveBook)
                && archiveBookStateList.equals(otherVersionedArchiveBook.archiveBookStateList)
                && currentStatePointer == otherVersionedArchiveBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of archiveBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of archiveBookState list, unable to redo.");
        }
    }
}

