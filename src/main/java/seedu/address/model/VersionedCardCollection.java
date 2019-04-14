package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code CardCollection} that keeps track of its own history.
 */
public class VersionedCardCollection extends CardCollection {

    private final List<ReadOnlyCardCollection> cardCollectionStateList;
    private final List<String> commandHistory;

    private int currentStatePointer;

    public VersionedCardCollection(ReadOnlyCardCollection initialState) {
        super(initialState);

        cardCollectionStateList = new ArrayList<>();
        commandHistory = new ArrayList<>();
        cardCollectionStateList.add(new CardCollection(initialState));
        commandHistory.add("");
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code CardCollection} state at the end of the state list.
     * Undone states are removed from the state list.
     * @param commandText The command that makes the change.
     */
    public void commit(String commandText) {
        removeStatesAfterCurrentPointer();
        cardCollectionStateList.add(new CardCollection(this));
        commandHistory.add(commandText);
        currentStatePointer++;
        indicateModified();
    }

    /**
     * Saves a copy of the current {@code CardCollection} state at the end of the state list.
     * Saves the command that makes the change.
     * Undone states are removed from the state list.
     */
    public void commit() {
        commit("");
    }

    private void removeStatesAfterCurrentPointer() {
        cardCollectionStateList.subList(currentStatePointer + 1, cardCollectionStateList.size()).clear();
        commandHistory.subList(currentStatePointer + 1, commandHistory.size()).clear();
    }

    /**
     * Restores the card collection to its previous state.
     * @return The command that makes the change before and after undo.
     */
    public String undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        String command = commandHistory.get(currentStatePointer);
        currentStatePointer--;
        resetData(cardCollectionStateList.get(currentStatePointer));
        return command;
    }

    /**
     * Restores the card collection to its previously undone state.
     * @return The command that makes the change before and after redo.
     */
    public String redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(cardCollectionStateList.get(currentStatePointer));
        return commandHistory.get(currentStatePointer);
    }

    /**
     * Returns true if {@code undo()} has card collection states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has card collection states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < cardCollectionStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedCardCollection)) {
            return false;
        }

        VersionedCardCollection otherVersionedCardCollection = (VersionedCardCollection) other;

        // state check
        return super.equals(otherVersionedCardCollection)
            && cardCollectionStateList.equals(otherVersionedCardCollection.cardCollectionStateList)
            && currentStatePointer == otherVersionedCardCollection.currentStatePointer
            && commandHistory.equals(otherVersionedCardCollection.commandHistory);
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of cardCollectionStateList, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of cardCollectionStateList, unable to redo.");
        }
    }
}
