package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code HealthWorkerBook} that keeps track of its own history.
 */
public class VersionedHealthWorkerBook extends HealthWorkerBook implements VersionedBook {

    private final List<ReadOnlyHealthWorkerBook> healthWorkerBookStateList;
    private int currStatePointer;

    public VersionedHealthWorkerBook(ReadOnlyHealthWorkerBook initialState) {
        super(initialState);

        this.healthWorkerBookStateList = new ArrayList<>();
        this.healthWorkerBookStateList.add(new HealthWorkerBook(initialState));
        this.currStatePointer = 0;
    }

    private void removeStatesAfterCurrentPointer() {
        this.healthWorkerBookStateList.subList(this.currStatePointer + 1,
                this.healthWorkerBookStateList.size()).clear();
    }

    @Override
    public void commit() {
        removeStatesAfterCurrentPointer();
        this.healthWorkerBookStateList.add(new HealthWorkerBook(this));
        this.currStatePointer++;
        indicateModified();
    }

    @Override
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        this.currStatePointer--;
        resetData(this.healthWorkerBookStateList.get(this.currStatePointer));
    }

    @Override
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }

        this.currStatePointer++;
        resetData(this.healthWorkerBookStateList.get(this.currStatePointer));
    }

    @Override
    public boolean canUndo() {
        return this.currStatePointer > 0;
    }

    @Override
    public boolean canRedo() {
        return this.currStatePointer < this.healthWorkerBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof VersionedHealthWorkerBook)) {
            return false;
        }

        VersionedHealthWorkerBook otherHealthWorkerBook = (VersionedHealthWorkerBook) other;

        return super.equals(otherHealthWorkerBook)
                && this.healthWorkerBookStateList.equals(otherHealthWorkerBook.healthWorkerBookStateList)
                && this.currStatePointer == otherHealthWorkerBook.currStatePointer;
    }
}
