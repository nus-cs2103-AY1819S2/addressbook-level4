package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code PocketProject} that keeps track of its own history.
 */
public class VersionedPocketProject extends PocketProject {

    private final List<ReadOnlyPocketProject> pocketProjectStateList;
    private int currentStatePointer;

    public VersionedPocketProject(ReadOnlyPocketProject initialState) {
        super(initialState);

        pocketProjectStateList = new ArrayList<>();
        pocketProjectStateList.add(new PocketProject(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code PocketProject} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        pocketProjectStateList.add(new PocketProject(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        pocketProjectStateList.subList(currentStatePointer + 1, pocketProjectStateList.size()).clear();
    }

    /**
     * Restores the pocket project to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(pocketProjectStateList.get(currentStatePointer));
    }

    /**
     * Restores the pocket project to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(pocketProjectStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has pocket project states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has pocket project states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < pocketProjectStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedPocketProject)) {
            return false;
        }

        VersionedPocketProject otherVersionedPocketProject = (VersionedPocketProject) other;

        // state check
        return super.equals(otherVersionedPocketProject)
                && pocketProjectStateList.equals(otherVersionedPocketProject.pocketProjectStateList)
                && currentStatePointer == otherVersionedPocketProject.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of pocketProjectState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of pocketProjectState list, unable to redo.");
        }
    }
}
