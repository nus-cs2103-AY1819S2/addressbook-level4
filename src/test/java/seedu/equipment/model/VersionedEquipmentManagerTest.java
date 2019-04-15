package seedu.equipment.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.equipment.testutil.TypicalEquipments.AMY;
import static seedu.equipment.testutil.TypicalEquipments.BOB;
import static seedu.equipment.testutil.TypicalEquipments.TECKGHEECC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.equipment.testutil.EquipmentManagerBuilder;

public class VersionedEquipmentManagerTest {

    private final ReadOnlyEquipmentManager equipManagerWithAmy = new EquipmentManagerBuilder().withPerson(AMY).build();
    private final ReadOnlyEquipmentManager equipManagerWithBob = new EquipmentManagerBuilder().withPerson(BOB).build();
    private final ReadOnlyEquipmentManager equipManagerWithCarl = new EquipmentManagerBuilder()
            .withPerson(TECKGHEECC).build();
    private final ReadOnlyEquipmentManager emptyEquipmentManager = new EquipmentManagerBuilder().build();

    @Test
    public void commit_singleEquipmentManager_noStatesRemovedCurrentStateSaved() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(emptyEquipmentManager);

        versionedEquipmentManager.commit();
        assertEquipmentManagerListStatus(versionedEquipmentManager,
                Collections.singletonList(emptyEquipmentManager),
                emptyEquipmentManager,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleEquipmentManagerPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);

        versionedEquipmentManager.commit();
        assertEquipmentManagerListStatus(versionedEquipmentManager,
                Arrays.asList(emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob),
                equipManagerWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleEquipmentManagerPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 2);

        versionedEquipmentManager.commit();
        assertEquipmentManagerListStatus(versionedEquipmentManager,
                Collections.singletonList(emptyEquipmentManager),
                emptyEquipmentManager,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleEquipmentManagerPointerAtEndOfStateList_returnsTrue() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);

        assertTrue(versionedEquipmentManager.canUndo());
    }

    @Test
    public void canUndo_multipleEquipmentManagerPointerAtStartOfStateList_returnsTrue() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 1);

        assertTrue(versionedEquipmentManager.canUndo());
    }

    @Test
    public void canUndo_singleEquipmentManager_returnsFalse() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(emptyEquipmentManager);

        assertFalse(versionedEquipmentManager.canUndo());
    }

    @Test
    public void canUndo_multipleEquipmentManagerPointerAtStartOfStateList_returnsFalse() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 2);

        assertFalse(versionedEquipmentManager.canUndo());
    }

    @Test
    public void canRedo_multipleEquipmentManagerPointerNotAtEndOfStateList_returnsTrue() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 1);

        assertTrue(versionedEquipmentManager.canRedo());
    }

    @Test
    public void canRedo_multipleEquipmentManagerPointerAtStartOfStateList_returnsTrue() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 2);

        assertTrue(versionedEquipmentManager.canRedo());
    }

    @Test
    public void canRedo_singleEquipmentManager_returnsFalse() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(emptyEquipmentManager);

        assertFalse(versionedEquipmentManager.canRedo());
    }

    @Test
    public void canRedo_multipleEquipmentManagerPointerAtEndOfStateList_returnsFalse() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);

        assertFalse(versionedEquipmentManager.canRedo());
    }

    @Test
    public void undo_multipleEquipmentManagerPointerAtEndOfStateList_success() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);

        versionedEquipmentManager.undo();
        assertEquipmentManagerListStatus(versionedEquipmentManager,
                Collections.singletonList(emptyEquipmentManager),
                equipManagerWithAmy,
                Collections.singletonList(equipManagerWithBob));
    }

    @Test
    public void undo_multipleEquipmentManagerPointerNotAtStartOfStateList_success() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 1);

        versionedEquipmentManager.undo();
        assertEquipmentManagerListStatus(versionedEquipmentManager,
                Collections.emptyList(),
                emptyEquipmentManager,
                Arrays.asList(equipManagerWithAmy, equipManagerWithBob));
    }

    @Test
    public void undo_singleEquipmentManager_throwsNoUndoableStateException() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(emptyEquipmentManager);

        assertThrows(VersionedEquipmentManager.NoUndoableStateException.class, versionedEquipmentManager::undo);
    }

    @Test
    public void undo_multipleEquipmentManagerPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 2);

        assertThrows(VersionedEquipmentManager.NoUndoableStateException.class, versionedEquipmentManager::undo);
    }

    @Test
    public void redo_multipleEquipmentManagerPointerNotAtEndOfStateList_success() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 1);

        versionedEquipmentManager.redo();
        assertEquipmentManagerListStatus(versionedEquipmentManager,
                Arrays.asList(emptyEquipmentManager, equipManagerWithAmy),
                equipManagerWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleEquipmentManagerPointerAtStartOfStateList_success() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 2);

        versionedEquipmentManager.redo();
        assertEquipmentManagerListStatus(versionedEquipmentManager,
                Collections.singletonList(emptyEquipmentManager),
                equipManagerWithAmy,
                Collections.singletonList(equipManagerWithBob));
    }

    @Test
    public void redo_singleEquipmentManager_throwsNoRedoableStateException() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(emptyEquipmentManager);

        assertThrows(VersionedEquipmentManager.NoRedoableStateException.class, versionedEquipmentManager::redo);
    }

    @Test
    public void redo_multipleEquipmentManagerPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(
                emptyEquipmentManager, equipManagerWithAmy, equipManagerWithBob);

        assertThrows(VersionedEquipmentManager.NoRedoableStateException.class, versionedEquipmentManager::redo);
    }

    @Test
    public void equals() {
        VersionedEquipmentManager versionedEquipmentManager = prepareEquipmentManagerList(equipManagerWithAmy,
                equipManagerWithBob);

        // same values -> returns true
        VersionedEquipmentManager copy = prepareEquipmentManagerList(equipManagerWithAmy, equipManagerWithBob);
        assertTrue(versionedEquipmentManager.equals(copy));

        // same object -> returns true
        assertTrue(versionedEquipmentManager.equals(versionedEquipmentManager));

        // null -> returns false
        assertFalse(versionedEquipmentManager.equals(null));

        // different types -> returns false
        assertFalse(versionedEquipmentManager.equals(1));

        // different state list -> returns false
        VersionedEquipmentManager differentEquipmentManagerList = prepareEquipmentManagerList(equipManagerWithBob,
                equipManagerWithCarl);
        assertFalse(versionedEquipmentManager.equals(differentEquipmentManagerList));

        // different current pointer index -> returns false
        VersionedEquipmentManager differentCurrentStatePointer = prepareEquipmentManagerList(
                equipManagerWithAmy, equipManagerWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 1);
        assertFalse(versionedEquipmentManager.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedEquipmentManager} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedEquipmentManager#currentStatePointer} is equal to
     * {@code expectedStatesBeforePointer}, and states after {@code versionedEquipmentManager#currentStatePointer}
     * is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertEquipmentManagerListStatus(VersionedEquipmentManager versionedEquipmentManager,
                                                  List<ReadOnlyEquipmentManager> expectedStatesBeforePointer,
                                                  ReadOnlyEquipmentManager expectedCurrentState,
                                                  List<ReadOnlyEquipmentManager> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new EquipmentManager(versionedEquipmentManager), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedEquipmentManager.canUndo()) {
            versionedEquipmentManager.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyEquipmentManager expectedEquipmentManager : expectedStatesBeforePointer) {
            assertEquals(expectedEquipmentManager, new EquipmentManager(versionedEquipmentManager));
            versionedEquipmentManager.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyEquipmentManager expectedEquipmentManager : expectedStatesAfterPointer) {
            versionedEquipmentManager.redo();
            assertEquals(expectedEquipmentManager, new EquipmentManager(versionedEquipmentManager));
        }

        // check that there are no more states after pointer
        assertFalse(versionedEquipmentManager.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedEquipmentManager.undo());
    }

    /**
     * Creates and returns a {@code VersionedEquipmentManager} with the {@code equipmentManagerStates} added into it,
     * and the
     * {@code VersionedEquipmentManager#currentStatePointer} at the end of list.
     */
    private VersionedEquipmentManager prepareEquipmentManagerList(ReadOnlyEquipmentManager... equipmentManagerStates) {
        assertFalse(equipmentManagerStates.length == 0);

        VersionedEquipmentManager versionedEquipmentManager = new VersionedEquipmentManager(equipmentManagerStates[0]);
        for (int i = 1; i < equipmentManagerStates.length; i++) {
            versionedEquipmentManager.resetData(equipmentManagerStates[i]);
            versionedEquipmentManager.commit();
        }

        return versionedEquipmentManager;
    }

    /**
     * Shifts the {@code versionedEquipmentManager#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedEquipmentManager versionedEquipmentManager, int count) {
        for (int i = 0; i < count; i++) {
            versionedEquipmentManager.undo();
        }
    }
}
