package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.BookType;

public class ModifyCommandHistory {

    private final int NOT_MODIFIED_YET = -1;

    private final List<BookType> modifyCommandHistory;
    private int currStatePointer;

    public ModifyCommandHistory() {
        this.modifyCommandHistory = new ArrayList<>();
        this.currStatePointer = NOT_MODIFIED_YET ;
    }

    private void removeStatesAfterCurrentPointer() {
        this.modifyCommandHistory.subList(this.currStatePointer + 1,
                this.modifyCommandHistory.size()).clear();
    }


    public void addLatestCommand(BookType type) {
        removeStatesAfterCurrentPointer();
        this.modifyCommandHistory.add(type);
        this.currStatePointer++;
    }

    public void undo() {
        if (!canUndo()) {
            throw new VersionedBook.NoUndoableStateException();
        }
        this.currStatePointer--;
    }

    public void redo() {
        if (!canRedo()) {
            throw new VersionedBook.NoRedoableStateException();
        }

        this.currStatePointer++;
    }

    public boolean canUndo() {
        return this.currStatePointer > NOT_MODIFIED_YET;
    }

    public boolean canRedo() {
        return this.currStatePointer < this.modifyCommandHistory.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModifyCommandHistory)) {
            return false;
        }

        ModifyCommandHistory otherHistory = (ModifyCommandHistory) other;

        return super.equals(otherHistory)
                && this.modifyCommandHistory.equals(otherHistory.modifyCommandHistory)
                && this.currStatePointer == otherHistory.currStatePointer;
    }


}
