package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalRestaurants.AMY;
import static seedu.address.testutil.TypicalRestaurants.BOB;
import static seedu.address.testutil.TypicalRestaurants.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.FoodDiaryBuilder;

public class VersionedFoodDiaryTest {

    private final ReadOnlyFoodDiary foodDiaryWithAmy = new FoodDiaryBuilder().withRestaurant(AMY).build();
    private final ReadOnlyFoodDiary foodDiaryWithBob = new FoodDiaryBuilder().withRestaurant(BOB).build();
    private final ReadOnlyFoodDiary foodDiaryWithCarl = new FoodDiaryBuilder().withRestaurant(CARL).build();
    private final ReadOnlyFoodDiary emptyFoodDiary = new FoodDiaryBuilder().build();

    @Test
    public void commit_singleFoodDiary_noStatesRemovedCurrentStateSaved() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(emptyFoodDiary);

        versionedFoodDiary.commit();
        assertFoodDiaryListStatus(versionedFoodDiary,
                Collections.singletonList(emptyFoodDiary),
                emptyFoodDiary,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleFoodDiaryPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);

        versionedFoodDiary.commit();
        assertFoodDiaryListStatus(versionedFoodDiary,
                Arrays.asList(emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob),
                foodDiaryWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleFoodDiaryPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);
        shiftCurrentStatePointerLeftwards(versionedFoodDiary, 2);

        versionedFoodDiary.commit();
        assertFoodDiaryListStatus(versionedFoodDiary,
                Collections.singletonList(emptyFoodDiary),
                emptyFoodDiary,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleFoodDiaryPointerAtEndOfStateList_returnsTrue() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);

        assertTrue(versionedFoodDiary.canUndo());
    }

    @Test
    public void canUndo_multipleFoodDiaryPointerAtStartOfStateList_returnsTrue() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);
        shiftCurrentStatePointerLeftwards(versionedFoodDiary, 1);

        assertTrue(versionedFoodDiary.canUndo());
    }

    @Test
    public void canUndo_singleFoodDiary_returnsFalse() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(emptyFoodDiary);

        assertFalse(versionedFoodDiary.canUndo());
    }

    @Test
    public void canUndo_multipleFoodDiaryPointerAtStartOfStateList_returnsFalse() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);
        shiftCurrentStatePointerLeftwards(versionedFoodDiary, 2);

        assertFalse(versionedFoodDiary.canUndo());
    }

    @Test
    public void canRedo_multipleFoodDiaryPointerNotAtEndOfStateList_returnsTrue() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);
        shiftCurrentStatePointerLeftwards(versionedFoodDiary, 1);

        assertTrue(versionedFoodDiary.canRedo());
    }

    @Test
    public void canRedo_multipleFoodDiaryPointerAtStartOfStateList_returnsTrue() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);
        shiftCurrentStatePointerLeftwards(versionedFoodDiary, 2);

        assertTrue(versionedFoodDiary.canRedo());
    }

    @Test
    public void canRedo_singleFoodDiary_returnsFalse() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(emptyFoodDiary);

        assertFalse(versionedFoodDiary.canRedo());
    }

    @Test
    public void canRedo_multipleFoodDiaryPointerAtEndOfStateList_returnsFalse() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);

        assertFalse(versionedFoodDiary.canRedo());
    }

    @Test
    public void undo_multipleFoodDiaryPointerAtEndOfStateList_success() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);

        versionedFoodDiary.undo();
        assertFoodDiaryListStatus(versionedFoodDiary,
                Collections.singletonList(emptyFoodDiary),
                foodDiaryWithAmy,
                Collections.singletonList(foodDiaryWithBob));
    }

    @Test
    public void undo_multipleFoodDiaryPointerNotAtStartOfStateList_success() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);
        shiftCurrentStatePointerLeftwards(versionedFoodDiary, 1);

        versionedFoodDiary.undo();
        assertFoodDiaryListStatus(versionedFoodDiary,
                Collections.emptyList(),
                emptyFoodDiary,
                Arrays.asList(foodDiaryWithAmy, foodDiaryWithBob));
    }

    @Test
    public void undo_singleFoodDiary_throwsNoUndoableStateException() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(emptyFoodDiary);

        assertThrows(VersionedFoodDiary.NoUndoableStateException.class, versionedFoodDiary::undo);
    }

    @Test
    public void undo_multipleFoodDiaryPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);
        shiftCurrentStatePointerLeftwards(versionedFoodDiary, 2);

        assertThrows(VersionedFoodDiary.NoUndoableStateException.class, versionedFoodDiary::undo);
    }

    @Test
    public void redo_multipleFoodDiaryPointerNotAtEndOfStateList_success() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);
        shiftCurrentStatePointerLeftwards(versionedFoodDiary, 1);

        versionedFoodDiary.redo();
        assertFoodDiaryListStatus(versionedFoodDiary,
                Arrays.asList(emptyFoodDiary, foodDiaryWithAmy),
                foodDiaryWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleFoodDiaryPointerAtStartOfStateList_success() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);
        shiftCurrentStatePointerLeftwards(versionedFoodDiary, 2);

        versionedFoodDiary.redo();
        assertFoodDiaryListStatus(versionedFoodDiary,
                Collections.singletonList(emptyFoodDiary),
                foodDiaryWithAmy,
                Collections.singletonList(foodDiaryWithBob));
    }

    @Test
    public void redo_singleFoodDiary_throwsNoRedoableStateException() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(emptyFoodDiary);

        assertThrows(VersionedFoodDiary.NoRedoableStateException.class, versionedFoodDiary::redo);
    }

    @Test
    public void redo_multipleFoodDiaryPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(
                emptyFoodDiary, foodDiaryWithAmy, foodDiaryWithBob);

        assertThrows(VersionedFoodDiary.NoRedoableStateException.class, versionedFoodDiary::redo);
    }

    @Test
    public void equals() {
        VersionedFoodDiary versionedFoodDiary = prepareFoodDiaryList(foodDiaryWithAmy, foodDiaryWithBob);

        // same values -> returns true
        VersionedFoodDiary copy = prepareFoodDiaryList(foodDiaryWithAmy, foodDiaryWithBob);
        assertTrue(versionedFoodDiary.equals(copy));

        // same object -> returns true
        assertTrue(versionedFoodDiary.equals(versionedFoodDiary));

        // null -> returns false
        assertFalse(versionedFoodDiary.equals(null));

        // different types -> returns false
        assertFalse(versionedFoodDiary.equals(1));

        // different state list -> returns false
        VersionedFoodDiary differentFoodDiaryList = prepareFoodDiaryList(foodDiaryWithBob, foodDiaryWithCarl);
        assertFalse(versionedFoodDiary.equals(differentFoodDiaryList));

        // different current pointer index -> returns false
        VersionedFoodDiary differentCurrentStatePointer = prepareFoodDiaryList(
                foodDiaryWithAmy, foodDiaryWithBob);
        shiftCurrentStatePointerLeftwards(versionedFoodDiary, 1);
        assertFalse(versionedFoodDiary.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedFoodDiary} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedFoodDiary#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedFoodDiary#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertFoodDiaryListStatus(VersionedFoodDiary versionedFoodDiary,
                                             List<ReadOnlyFoodDiary> expectedStatesBeforePointer,
                                             ReadOnlyFoodDiary expectedCurrentState,
                                             List<ReadOnlyFoodDiary> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new FoodDiary(versionedFoodDiary), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedFoodDiary.canUndo()) {
            versionedFoodDiary.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyFoodDiary expectedFoodDiary : expectedStatesBeforePointer) {
            assertEquals(expectedFoodDiary, new FoodDiary(versionedFoodDiary));
            versionedFoodDiary.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyFoodDiary expectedFoodDiary : expectedStatesAfterPointer) {
            versionedFoodDiary.redo();
            assertEquals(expectedFoodDiary, new FoodDiary(versionedFoodDiary));
        }

        // check that there are no more states after pointer
        assertFalse(versionedFoodDiary.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedFoodDiary.undo());
    }

    /**
     * Creates and returns a {@code VersionedFoodDiary} with the {@code foodDiaryStates} added into it, and the
     * {@code VersionedFoodDiary#currentStatePointer} at the end of list.
     */
    private VersionedFoodDiary prepareFoodDiaryList(ReadOnlyFoodDiary... foodDiaryStates) {
        assertFalse(foodDiaryStates.length == 0);

        VersionedFoodDiary versionedFoodDiary = new VersionedFoodDiary(foodDiaryStates[0]);
        for (int i = 1; i < foodDiaryStates.length; i++) {
            versionedFoodDiary.resetData(foodDiaryStates[i]);
            versionedFoodDiary.commit();
        }

        return versionedFoodDiary;
    }

    /**
     * Shifts the {@code versionedFoodDiary#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedFoodDiary versionedFoodDiary, int count) {
        for (int i = 0; i < count; i++) {
            versionedFoodDiary.undo();
        }
    }
}
