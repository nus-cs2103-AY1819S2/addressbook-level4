package seedu.address.model;

/**
 * @author Lookaz
 * Interface that represents a ObjectBook that keeps track of changes.
 */
public interface VersionedBook {

    /**
     * Saves a copy of the current state.
     */
    void commit();

    /**
     * Restores its previous state.
     */
    void undo();

    /**
     * Restores its previously undone state.
     */
    void redo();

    /**
     * Returns true if {@code undo()} has states to undo.
     */
    boolean canUndo();

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    boolean canRedo();

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    class NoUndoableStateException extends RuntimeException {
        protected NoUndoableStateException() {
            super("Current state pointer at start of BookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    class NoRedoableStateException extends RuntimeException {
        protected NoRedoableStateException() {
            super("Current state pointer at end of BookState list, unable to redo.");
        }
    }
}
