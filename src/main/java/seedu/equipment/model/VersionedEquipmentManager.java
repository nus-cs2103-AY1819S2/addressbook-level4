package seedu.equipment.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code EquipmentManager} that keeps track of its own history.
 */
public class VersionedEquipmentManager extends EquipmentManager {

    private final List<ReadOnlyEquipmentManager> addressBookStateList;
    private int currentStatePointer;

    public VersionedEquipmentManager(ReadOnlyEquipmentManager initialState) {
        super(initialState);

        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new EquipmentManager(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code EquipmentManager} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        addressBookStateList.add(new EquipmentManager(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
    }

    /**
     * Restores the equipment book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the equipment book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has equipment book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has equipment book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedEquipmentManager)) {
            return false;
        }

        VersionedEquipmentManager otherVersionedEquipmentManager = (VersionedEquipmentManager) other;

        // state check
        return super.equals(otherVersionedEquipmentManager)
                && addressBookStateList.equals(otherVersionedEquipmentManager.addressBookStateList)
                && currentStatePointer == otherVersionedEquipmentManager.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}
