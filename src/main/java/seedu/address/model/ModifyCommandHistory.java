package seedu.address.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Class that stores history of commands that modify the respective Books.
 */
public class ModifyCommandHistory {

    private static final int NOT_MODIFIED_YET = -1;

    private final List<CommandType> modifyCommandHistory;
    private int currStatePointer;

    public ModifyCommandHistory() {
        this.modifyCommandHistory = new ArrayList<>();
        this.currStatePointer = NOT_MODIFIED_YET;
    }

    private void removeStatesAfterCurrentPointer() {
        this.modifyCommandHistory.subList(this.currStatePointer + 1,
                this.modifyCommandHistory.size()).clear();
    }

    /**
     * Add the type of the last executed modify command into history of modify commands
     * @param type The type of command that was last executed.
     */

    public void addLatestCommand(CommandType type) {
        removeStatesAfterCurrentPointer();
        this.modifyCommandHistory.add(type);
        this.currStatePointer++;
    }

    /**
     * Return the command to undo
     * @return The type of command that is to be undone
     */
    public CommandType getUndoCommand() {
        assert(this.currStatePointer > NOT_MODIFIED_YET);
        return this.modifyCommandHistory.get(currStatePointer);
    }

    /**
     * Return the command to undo
     * @return The type of command that is to be undone
     */

    public CommandType getRedoCommand() {
        assert(this.currStatePointer < modifyCommandHistory.size());
        return this.modifyCommandHistory.get(currStatePointer + 1);
    }

    /**
     * Restores its previous state pointer.
     */
    public void undo() {
        if (!canUndo()) {
            throw new VersionedBook.NoUndoableStateException();
        }
        this.currStatePointer--;
    }
    /**
     * Restores its previously undone state pointer.
     */
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
