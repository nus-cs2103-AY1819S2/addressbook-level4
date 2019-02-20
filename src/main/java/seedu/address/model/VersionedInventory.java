package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Inventory} that keeps track of its own history.
 */
public class VersionedInventory extends Inventory {

    private final List<ReadOnlyInventory> InventoryStateList;
    private int currentStatePointer;

    public VersionedInventory(ReadOnlyInventory initialState) {
        super(initialState);

        InventoryStateList = new ArrayList<>();
        InventoryStateList.add(new Inventory(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code Inventory} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        InventoryStateList.add(new Inventory(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        InventoryStateList.subList(currentStatePointer + 1, InventoryStateList.size()).clear();
    }

    /**
     * Restores the inventory to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(InventoryStateList.get(currentStatePointer));
    }

    /**
     * Restores the inventory to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(InventoryStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has inventory states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has inventory states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < InventoryStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedInventory)) {
            return false;
        }

        VersionedInventory otherVersionedInventory = (VersionedInventory) other;

        // state check
        return super.equals(otherVersionedInventory)
                && InventoryStateList.equals(otherVersionedInventory.InventoryStateList)
                && currentStatePointer == otherVersionedInventory.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of InventoryState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of InventoryState list, unable to redo.");
        }
    }
}
