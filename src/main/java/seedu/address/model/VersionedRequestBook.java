package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code RequestBook} that keeps track of its own history.
 *
 * @@author daviddl9
 */
public class VersionedRequestBook extends RequestBook implements VersionedBook {

    private final List<ReadOnlyRequestBook> requestBookStateList;
    private int currentStatePointer;

    public VersionedRequestBook(ReadOnlyRequestBook initialState) {
        super(initialState);

        requestBookStateList = new ArrayList<>();
        requestBookStateList.add(new RequestBook(initialState));
        currentStatePointer = 0;
    }

    private void removeStatesAfterCurrentPointer() {
        requestBookStateList.subList(currentStatePointer + 1, requestBookStateList.size()).clear();
    }

    @Override
    public String toString() {
        return "State: " + this.currentStatePointer + "\n" + this.requestBookStateList.toString();
    }

    /**
     * Saves a copy of the current state.
     */
    @Override
    public void commit() {
        removeStatesAfterCurrentPointer();
        requestBookStateList.add(new RequestBook(this));
        currentStatePointer++;
        indicateModified();
    }

    /**
     * Restores its previous state.
     */
    @Override
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(requestBookStateList.get(currentStatePointer));
    }

    /**
     * Restores its previously undone state.
     */
    @Override
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }

        this.currentStatePointer++;
        resetData(this.requestBookStateList.get(this.currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has states to undo.
     */
    @Override
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    @Override
    public boolean canRedo() {
        return currentStatePointer < requestBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedRequestBook)) {
            return false;
        }

        VersionedRequestBook otherVersionedRequestBook = (VersionedRequestBook) other;

        // state check
        return super.equals(otherVersionedRequestBook) && requestBookStateList
            .equals(otherVersionedRequestBook.requestBookStateList)
            && currentStatePointer == otherVersionedRequestBook.currentStatePointer;
    }
}
