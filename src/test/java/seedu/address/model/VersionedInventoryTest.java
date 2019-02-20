package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalMedicines.AMY;
import static seedu.address.testutil.TypicalMedicines.BOB;
import static seedu.address.testutil.TypicalMedicines.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.InventoryBuilder;

public class VersionedInventoryTest {

    private final ReadOnlyInventory InventoryWithAmy = new InventoryBuilder().withMedicine(AMY).build();
    private final ReadOnlyInventory InventoryWithBob = new InventoryBuilder().withMedicine(BOB).build();
    private final ReadOnlyInventory InventoryWithCarl = new InventoryBuilder().withMedicine(CARL).build();
    private final ReadOnlyInventory emptyInventory = new InventoryBuilder().build();

    @Test
    public void commit_singleInventory_noStatesRemovedCurrentStateSaved() {
        VersionedInventory versionedInventory = prepareInventoryList(emptyInventory);

        versionedInventory.commit();
        assertInventoryListStatus(versionedInventory,
                Collections.singletonList(emptyInventory),
                emptyInventory,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleInventoryPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);

        versionedInventory.commit();
        assertInventoryListStatus(versionedInventory,
                Arrays.asList(emptyInventory, InventoryWithAmy, InventoryWithBob),
                InventoryWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleInventoryPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 2);

        versionedInventory.commit();
        assertInventoryListStatus(versionedInventory,
                Collections.singletonList(emptyInventory),
                emptyInventory,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleInventoryPointerAtEndOfStateList_returnsTrue() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);

        assertTrue(versionedInventory.canUndo());
    }

    @Test
    public void canUndo_multipleInventoryPointerAtStartOfStateList_returnsTrue() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 1);

        assertTrue(versionedInventory.canUndo());
    }

    @Test
    public void canUndo_singleInventory_returnsFalse() {
        VersionedInventory versionedInventory = prepareInventoryList(emptyInventory);

        assertFalse(versionedInventory.canUndo());
    }

    @Test
    public void canUndo_multipleInventoryPointerAtStartOfStateList_returnsFalse() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 2);

        assertFalse(versionedInventory.canUndo());
    }

    @Test
    public void canRedo_multipleInventoryPointerNotAtEndOfStateList_returnsTrue() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 1);

        assertTrue(versionedInventory.canRedo());
    }

    @Test
    public void canRedo_multipleInventoryPointerAtStartOfStateList_returnsTrue() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 2);

        assertTrue(versionedInventory.canRedo());
    }

    @Test
    public void canRedo_singleInventory_returnsFalse() {
        VersionedInventory versionedInventory = prepareInventoryList(emptyInventory);

        assertFalse(versionedInventory.canRedo());
    }

    @Test
    public void canRedo_multipleInventoryPointerAtEndOfStateList_returnsFalse() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);

        assertFalse(versionedInventory.canRedo());
    }

    @Test
    public void undo_multipleInventoryPointerAtEndOfStateList_success() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);

        versionedInventory.undo();
        assertInventoryListStatus(versionedInventory,
                Collections.singletonList(emptyInventory),
                InventoryWithAmy,
                Collections.singletonList(InventoryWithBob));
    }

    @Test
    public void undo_multipleInventoryPointerNotAtStartOfStateList_success() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 1);

        versionedInventory.undo();
        assertInventoryListStatus(versionedInventory,
                Collections.emptyList(),
                emptyInventory,
                Arrays.asList(InventoryWithAmy, InventoryWithBob));
    }

    @Test
    public void undo_singleInventory_throwsNoUndoableStateException() {
        VersionedInventory versionedInventory = prepareInventoryList(emptyInventory);

        assertThrows(VersionedInventory.NoUndoableStateException.class, versionedInventory::undo);
    }

    @Test
    public void undo_multipleInventoryPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 2);

        assertThrows(VersionedInventory.NoUndoableStateException.class, versionedInventory::undo);
    }

    @Test
    public void redo_multipleInventoryPointerNotAtEndOfStateList_success() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 1);

        versionedInventory.redo();
        assertInventoryListStatus(versionedInventory,
                Arrays.asList(emptyInventory, InventoryWithAmy),
                InventoryWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleInventoryPointerAtStartOfStateList_success() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 2);

        versionedInventory.redo();
        assertInventoryListStatus(versionedInventory,
                Collections.singletonList(emptyInventory),
                InventoryWithAmy,
                Collections.singletonList(InventoryWithBob));
    }

    @Test
    public void redo_singleInventory_throwsNoRedoableStateException() {
        VersionedInventory versionedInventory = prepareInventoryList(emptyInventory);

        assertThrows(VersionedInventory.NoRedoableStateException.class, versionedInventory::redo);
    }

    @Test
    public void redo_multipleInventoryPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedInventory versionedInventory = prepareInventoryList(
                emptyInventory, InventoryWithAmy, InventoryWithBob);

        assertThrows(VersionedInventory.NoRedoableStateException.class, versionedInventory::redo);
    }

    @Test
    public void equals() {
        VersionedInventory versionedInventory = prepareInventoryList(InventoryWithAmy, InventoryWithBob);

        // same values -> returns true
        VersionedInventory copy = prepareInventoryList(InventoryWithAmy, InventoryWithBob);
        assertTrue(versionedInventory.equals(copy));

        // same object -> returns true
        assertTrue(versionedInventory.equals(versionedInventory));

        // null -> returns false
        assertFalse(versionedInventory.equals(null));

        // different types -> returns false
        assertFalse(versionedInventory.equals(1));

        // different state list -> returns false
        VersionedInventory differentInventoryList = prepareInventoryList(InventoryWithBob, InventoryWithCarl);
        assertFalse(versionedInventory.equals(differentInventoryList));

        // different current pointer index -> returns false
        VersionedInventory differentCurrentStatePointer = prepareInventoryList(
                InventoryWithAmy, InventoryWithBob);
        shiftCurrentStatePointerLeftwards(versionedInventory, 1);
        assertFalse(versionedInventory.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedInventory} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedInventory#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedInventory#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertInventoryListStatus(VersionedInventory versionedInventory,
                                             List<ReadOnlyInventory> expectedStatesBeforePointer,
                                             ReadOnlyInventory expectedCurrentState,
                                             List<ReadOnlyInventory> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new Inventory(versionedInventory), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedInventory.canUndo()) {
            versionedInventory.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyInventory expectedInventory : expectedStatesBeforePointer) {
            assertEquals(expectedInventory, new Inventory(versionedInventory));
            versionedInventory.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyInventory expectedInventory : expectedStatesAfterPointer) {
            versionedInventory.redo();
            assertEquals(expectedInventory, new Inventory(versionedInventory));
        }

        // check that there are no more states after pointer
        assertFalse(versionedInventory.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedInventory.undo());
    }

    /**
     * Creates and returns a {@code VersionedInventory} with the {@code InventoryStates} added into it, and the
     * {@code VersionedInventory#currentStatePointer} at the end of list.
     */
    private VersionedInventory prepareInventoryList(ReadOnlyInventory... InventoryStates) {
        assertFalse(InventoryStates.length == 0);

        VersionedInventory versionedInventory = new VersionedInventory(InventoryStates[0]);
        for (int i = 1; i < InventoryStates.length; i++) {
            versionedInventory.resetData(InventoryStates[i]);
            versionedInventory.commit();
        }

        return versionedInventory;
    }

    /**
     * Shifts the {@code versionedInventory#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedInventory versionedInventory, int count) {
        for (int i = 0; i < count; i++) {
            versionedInventory.undo();
        }
    }
}
