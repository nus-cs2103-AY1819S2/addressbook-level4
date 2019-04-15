package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code BookShelf} that keeps track of its own history.
 */
public class VersionedBookShelf extends BookShelf {

    private final List<ReadOnlyBookShelf> bookShelfStateList;
    private int currentStatePointer;

    public VersionedBookShelf(ReadOnlyBookShelf initialState) {
        super(initialState);

        bookShelfStateList = new ArrayList<>();
        bookShelfStateList.add(new BookShelf(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code BookShelf} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        bookShelfStateList.add(new BookShelf(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        bookShelfStateList.subList(currentStatePointer + 1, bookShelfStateList.size()).clear();
    }

    /**
     * Restores the book list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(bookShelfStateList.get(currentStatePointer));
    }

    /**
     * Restores the book list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(bookShelfStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has book list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has book list states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < bookShelfStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedBookShelf)) {
            return false;
        }

        VersionedBookShelf otherVersionedBookShelf = (VersionedBookShelf) other;

        // state check
        return super.equals(otherVersionedBookShelf)
                && bookShelfStateList.equals(otherVersionedBookShelf.bookShelfStateList)
                && currentStatePointer == otherVersionedBookShelf.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of bookShelfState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of bookShelfState list, unable to redo.");
        }
    }
}
