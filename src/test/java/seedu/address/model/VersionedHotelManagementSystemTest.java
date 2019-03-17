package seedu.hms.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.hms.testutil.TypicalCustomers.AMY;
import static seedu.hms.testutil.TypicalCustomers.BOB;
import static seedu.hms.testutil.TypicalCustomers.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.hms.testutil.HotelManagementSystemBuilder;

public class VersionedHotelManagementSystemTest {

    private final ReadOnlyHotelManagementSystem hotelManagementSystemWithAmy =
        new HotelManagementSystemBuilder().withCustomer(AMY).build();
    private final ReadOnlyHotelManagementSystem hotelManagementSystemWithBob =
        new HotelManagementSystemBuilder().withCustomer(BOB).build();
    private final ReadOnlyHotelManagementSystem hotelManagementSystemWithCarl =
        new HotelManagementSystemBuilder().withCustomer(CARL).build();
    private final ReadOnlyHotelManagementSystem emptyHotelManagementSystem = new HotelManagementSystemBuilder().build();

    @Test
    public void commit_singleHotelManagementSystem_noStatesRemovedCurrentStateSaved() {
        VersionedHotelManagementSystem versionedHotelManagementSystem =
            prepareHotelManagementSystemList(emptyHotelManagementSystem);

        versionedHotelManagementSystem.commit();
        assertHotelManagementSystemListStatus(versionedHotelManagementSystem,
            Collections.singletonList(emptyHotelManagementSystem),
            emptyHotelManagementSystem,
            Collections.emptyList());
    }

    @Test
    public void commit_multipleHotelManagementSystemPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);

        versionedHotelManagementSystem.commit();
        assertHotelManagementSystemListStatus(versionedHotelManagementSystem,
            Arrays.asList(emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob),
            hotelManagementSystemWithBob,
            Collections.emptyList());
    }

    @Test
    public void
        commit_multipleHotelManagementSystemPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);
        shiftCurrentStatePointerLeftwards(versionedHotelManagementSystem, 2);

        versionedHotelManagementSystem.commit();
        assertHotelManagementSystemListStatus(versionedHotelManagementSystem,
            Collections.singletonList(emptyHotelManagementSystem),
            emptyHotelManagementSystem,
            Collections.emptyList());
    }

    @Test
    public void canUndo_multipleHotelManagementSystemPointerAtEndOfStateList_returnsTrue() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);

        assertTrue(versionedHotelManagementSystem.canUndo());
    }

    @Test
    public void canUndo_multipleHotelManagementSystemPointerAtStartOfStateList_returnsTrue() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);
        shiftCurrentStatePointerLeftwards(versionedHotelManagementSystem, 1);

        assertTrue(versionedHotelManagementSystem.canUndo());
    }

    @Test
    public void canUndo_singleHotelManagementSystem_returnsFalse() {
        VersionedHotelManagementSystem versionedHotelManagementSystem =
            prepareHotelManagementSystemList(emptyHotelManagementSystem);

        assertFalse(versionedHotelManagementSystem.canUndo());
    }

    @Test
    public void canUndo_multipleHotelManagementSystemPointerAtStartOfStateList_returnsFalse() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);
        shiftCurrentStatePointerLeftwards(versionedHotelManagementSystem, 2);

        assertFalse(versionedHotelManagementSystem.canUndo());
    }

    @Test
    public void canRedo_multipleHotelManagementSystemPointerNotAtEndOfStateList_returnsTrue() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);
        shiftCurrentStatePointerLeftwards(versionedHotelManagementSystem, 1);

        assertTrue(versionedHotelManagementSystem.canRedo());
    }

    @Test
    public void canRedo_multipleHotelManagementSystemPointerAtStartOfStateList_returnsTrue() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);
        shiftCurrentStatePointerLeftwards(versionedHotelManagementSystem, 2);

        assertTrue(versionedHotelManagementSystem.canRedo());
    }

    @Test
    public void canRedo_singleHotelManagementSystem_returnsFalse() {
        VersionedHotelManagementSystem versionedHotelManagementSystem =
            prepareHotelManagementSystemList(emptyHotelManagementSystem);

        assertFalse(versionedHotelManagementSystem.canRedo());
    }

    @Test
    public void canRedo_multipleHotelManagementSystemPointerAtEndOfStateList_returnsFalse() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);

        assertFalse(versionedHotelManagementSystem.canRedo());
    }

    @Test
    public void undo_multipleHotelManagementSystemPointerAtEndOfStateList_success() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);

        versionedHotelManagementSystem.undo();
        assertHotelManagementSystemListStatus(versionedHotelManagementSystem,
            Collections.singletonList(emptyHotelManagementSystem),
            hotelManagementSystemWithAmy,
            Collections.singletonList(hotelManagementSystemWithBob));
    }

    @Test
    public void undo_multipleHotelManagementSystemPointerNotAtStartOfStateList_success() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);
        shiftCurrentStatePointerLeftwards(versionedHotelManagementSystem, 1);

        versionedHotelManagementSystem.undo();
        assertHotelManagementSystemListStatus(versionedHotelManagementSystem,
            Collections.emptyList(),
            emptyHotelManagementSystem,
            Arrays.asList(hotelManagementSystemWithAmy, hotelManagementSystemWithBob));
    }

    @Test
    public void undo_singleHotelManagementSystem_throwsNoUndoableStateException() {
        VersionedHotelManagementSystem versionedHotelManagementSystem =
            prepareHotelManagementSystemList(emptyHotelManagementSystem);

        assertThrows(VersionedHotelManagementSystem.NoUndoableStateException.class,
            versionedHotelManagementSystem::undo);
    }

    @Test
    public void undo_multipleHotelManagementSystemPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);
        shiftCurrentStatePointerLeftwards(versionedHotelManagementSystem, 2);

        assertThrows(VersionedHotelManagementSystem.NoUndoableStateException.class,
            versionedHotelManagementSystem::undo);
    }

    @Test
    public void redo_multipleHotelManagementSystemPointerNotAtEndOfStateList_success() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);
        shiftCurrentStatePointerLeftwards(versionedHotelManagementSystem, 1);

        versionedHotelManagementSystem.redo();
        assertHotelManagementSystemListStatus(versionedHotelManagementSystem,
            Arrays.asList(emptyHotelManagementSystem, hotelManagementSystemWithAmy),
            hotelManagementSystemWithBob,
            Collections.emptyList());
    }

    @Test
    public void redo_multipleHotelManagementSystemPointerAtStartOfStateList_success() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);
        shiftCurrentStatePointerLeftwards(versionedHotelManagementSystem, 2);

        versionedHotelManagementSystem.redo();
        assertHotelManagementSystemListStatus(versionedHotelManagementSystem,
            Collections.singletonList(emptyHotelManagementSystem),
            hotelManagementSystemWithAmy,
            Collections.singletonList(hotelManagementSystemWithBob));
    }

    @Test
    public void redo_singleHotelManagementSystem_throwsNoRedoableStateException() {
        VersionedHotelManagementSystem versionedHotelManagementSystem =
            prepareHotelManagementSystemList(emptyHotelManagementSystem);

        assertThrows(VersionedHotelManagementSystem.NoRedoableStateException.class,
            versionedHotelManagementSystem::redo);
    }

    @Test
    public void redo_multipleHotelManagementSystemPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedHotelManagementSystem versionedHotelManagementSystem = prepareHotelManagementSystemList(
            emptyHotelManagementSystem, hotelManagementSystemWithAmy, hotelManagementSystemWithBob);

        assertThrows(VersionedHotelManagementSystem.NoRedoableStateException.class,
            versionedHotelManagementSystem::redo);
    }

    @Test
    public void equals() {
        VersionedHotelManagementSystem versionedHotelManagementSystem =
            prepareHotelManagementSystemList(hotelManagementSystemWithAmy, hotelManagementSystemWithBob);

        // same values -> returns true
        VersionedHotelManagementSystem copy = prepareHotelManagementSystemList(hotelManagementSystemWithAmy,
            hotelManagementSystemWithBob);
        assertTrue(versionedHotelManagementSystem.equals(copy));

        // same object -> returns true
        assertTrue(versionedHotelManagementSystem.equals(versionedHotelManagementSystem));

        // null -> returns false
        assertFalse(versionedHotelManagementSystem == null);

        // different types -> returns false
        assertFalse(versionedHotelManagementSystem.equals(1));

        // different state list -> returns false
        VersionedHotelManagementSystem differentHotelManagementSystemList =
            prepareHotelManagementSystemList(hotelManagementSystemWithBob, hotelManagementSystemWithCarl);
        assertFalse(versionedHotelManagementSystem.equals(differentHotelManagementSystemList));

        // different current pointer index -> returns false
        VersionedHotelManagementSystem differentCurrentStatePointer = prepareHotelManagementSystemList(
            hotelManagementSystemWithAmy, hotelManagementSystemWithBob);
        shiftCurrentStatePointerLeftwards(versionedHotelManagementSystem, 1);
        assertFalse(versionedHotelManagementSystem.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedHotelManagementSystem} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedHotelManagementSystem#currentStatePointer} is equal to {@code
     * expectedStatesBeforePointer},
     * and states after {@code versionedHotelManagementSystem#currentStatePointer} is equal to {@code
     * expectedStatesAfterPointer}.
     */
    private void assertHotelManagementSystemListStatus(VersionedHotelManagementSystem versionedHotelManagementSystem,
                                                       List<ReadOnlyHotelManagementSystem> expectedStatesBeforePointer,
                                                       ReadOnlyHotelManagementSystem expectedCurrentState,
                                                       List<ReadOnlyHotelManagementSystem> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new HotelManagementSystem(versionedHotelManagementSystem), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedHotelManagementSystem.canUndo()) {
            versionedHotelManagementSystem.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyHotelManagementSystem expectedHotelManagementSystem : expectedStatesBeforePointer) {
            assertEquals(expectedHotelManagementSystem, new HotelManagementSystem(versionedHotelManagementSystem));
            versionedHotelManagementSystem.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyHotelManagementSystem expectedHotelManagementSystem : expectedStatesAfterPointer) {
            versionedHotelManagementSystem.redo();
            assertEquals(expectedHotelManagementSystem, new HotelManagementSystem(versionedHotelManagementSystem));
        }

        // check that there are no more states after pointer
        assertFalse(versionedHotelManagementSystem.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedHotelManagementSystem.undo());
    }

    /**
     * Creates and returns a {@code VersionedHotelManagementSystem} with the {@code hotelManagementSystemStates}
     * added into it, and the
     * {@code VersionedHotelManagementSystem#currentStatePointer} at the end of list.
     */
    private VersionedHotelManagementSystem prepareHotelManagementSystemList(
        ReadOnlyHotelManagementSystem... hotelManagementSystemStates) {
        assertFalse(hotelManagementSystemStates.length == 0);

        VersionedHotelManagementSystem versionedHotelManagementSystem =
            new VersionedHotelManagementSystem(hotelManagementSystemStates[0]);
        for (int i = 1; i < hotelManagementSystemStates.length; i++) {
            versionedHotelManagementSystem.resetData(hotelManagementSystemStates[i]);
            versionedHotelManagementSystem.commit();
        }

        return versionedHotelManagementSystem;
    }

    /**
     * Shifts the {@code versionedHotelManagementSystem#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedHotelManagementSystem versionedHotelManagementSystem,
                                                   int count) {
        for (int i = 0; i < count; i++) {
            versionedHotelManagementSystem.undo();
        }
    }
}
