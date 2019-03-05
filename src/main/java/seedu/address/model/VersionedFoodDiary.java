package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code FoodDiary} that keeps track of its own history.
 */
public class VersionedFoodDiary extends FoodDiary {

    private final List<ReadOnlyFoodDiary> foodDiaryStateList;
    private int currentStatePointer;

    public VersionedFoodDiary(ReadOnlyFoodDiary initialState) {
        super(initialState);

        foodDiaryStateList = new ArrayList<>();
        foodDiaryStateList.add(new FoodDiary(initialState));
        currentStatePointer = 0;
    }

    public int getSize() {
        return foodDiaryStateList.get(currentStatePointer).getRestaurantList().size();
    }
    /**
     * Saves a copy of the current {@code FoodDiary} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        foodDiaryStateList.add(new FoodDiary(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        foodDiaryStateList.subList(currentStatePointer + 1, foodDiaryStateList.size()).clear();
    }

    /**
     * Restores the food diary to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(foodDiaryStateList.get(currentStatePointer));
    }

    /**
     * Restores the food diary to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(foodDiaryStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has food diary states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has food diary states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < foodDiaryStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedFoodDiary)) {
            return false;
        }

        VersionedFoodDiary otherVersionedFoodDiary = (VersionedFoodDiary) other;

        // state check
        return super.equals(otherVersionedFoodDiary)
                && foodDiaryStateList.equals(otherVersionedFoodDiary.foodDiaryStateList)
                && currentStatePointer == otherVersionedFoodDiary.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of foodDiaryState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of foodDiaryState list, unable to redo.");
        }
    }
}
