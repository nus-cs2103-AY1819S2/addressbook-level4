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

    private final ReadOnlyEquipmentManager addressBookWithAmy = new EquipmentManagerBuilder().withPerson(AMY).build();
    private final ReadOnlyEquipmentManager addressBookWithBob = new EquipmentManagerBuilder().withPerson(BOB).build();
    private final ReadOnlyEquipmentManager addressBookWithCarl = new EquipmentManagerBuilder()
            .withPerson(TECKGHEECC).build();
    private final ReadOnlyEquipmentManager emptyAddressBook = new EquipmentManagerBuilder().build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(emptyAddressBook);

        versionedEquipmentManager.commit();
        assertAddressBookListStatus(versionedEquipmentManager,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedEquipmentManager.commit();
        assertAddressBookListStatus(versionedEquipmentManager,
                Arrays.asList(emptyAddressBook, addressBookWithAmy, addressBookWithBob),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 2);

        versionedEquipmentManager.commit();
        assertAddressBookListStatus(versionedEquipmentManager,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertTrue(versionedEquipmentManager.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 1);

        assertTrue(versionedEquipmentManager.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedEquipmentManager.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 2);

        assertFalse(versionedEquipmentManager.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 1);

        assertTrue(versionedEquipmentManager.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 2);

        assertTrue(versionedEquipmentManager.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedEquipmentManager.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertFalse(versionedEquipmentManager.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedEquipmentManager.undo();
        assertAddressBookListStatus(versionedEquipmentManager,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 1);

        versionedEquipmentManager.undo();
        assertAddressBookListStatus(versionedEquipmentManager,
                Collections.emptyList(),
                emptyAddressBook,
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedEquipmentManager.NoUndoableStateException.class, versionedEquipmentManager::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 2);

        assertThrows(VersionedEquipmentManager.NoUndoableStateException.class, versionedEquipmentManager::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 1);

        versionedEquipmentManager.redo();
        assertAddressBookListStatus(versionedEquipmentManager,
                Arrays.asList(emptyAddressBook, addressBookWithAmy),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 2);

        versionedEquipmentManager.redo();
        assertAddressBookListStatus(versionedEquipmentManager,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedEquipmentManager.NoRedoableStateException.class, versionedEquipmentManager::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertThrows(VersionedEquipmentManager.NoRedoableStateException.class, versionedEquipmentManager::redo);
    }

    @Test
    public void equals() {
        VersionedEquipmentManager versionedEquipmentManager = prepareAddressBookList(addressBookWithAmy,
                addressBookWithBob);

        // same values -> returns true
        VersionedEquipmentManager copy = prepareAddressBookList(addressBookWithAmy, addressBookWithBob);
        assertTrue(versionedEquipmentManager.equals(copy));

        // same object -> returns true
        assertTrue(versionedEquipmentManager.equals(versionedEquipmentManager));

        // null -> returns false
        assertFalse(versionedEquipmentManager.equals(null));

        // different types -> returns false
        assertFalse(versionedEquipmentManager.equals(1));

        // different state list -> returns false
        VersionedEquipmentManager differentAddressBookList = prepareAddressBookList(addressBookWithBob,
                addressBookWithCarl);
        assertFalse(versionedEquipmentManager.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedEquipmentManager differentCurrentStatePointer = prepareAddressBookList(
                addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedEquipmentManager, 1);
        assertFalse(versionedEquipmentManager.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedEquipmentManager} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedEquipmentManager#currentStatePointer} is equal to
     * {@code expectedStatesBeforePointer}, and states after {@code versionedEquipmentManager#currentStatePointer}
     * is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedEquipmentManager versionedEquipmentManager,
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
        for (ReadOnlyEquipmentManager expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new EquipmentManager(versionedEquipmentManager));
            versionedEquipmentManager.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyEquipmentManager expectedAddressBook : expectedStatesAfterPointer) {
            versionedEquipmentManager.redo();
            assertEquals(expectedAddressBook, new EquipmentManager(versionedEquipmentManager));
        }

        // check that there are no more states after pointer
        assertFalse(versionedEquipmentManager.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedEquipmentManager.undo());
    }

    /**
     * Creates and returns a {@code VersionedEquipmentManager} with the {@code addressBookStates} added into it, and the
     * {@code VersionedEquipmentManager#currentStatePointer} at the end of list.
     */
    private VersionedEquipmentManager prepareAddressBookList(ReadOnlyEquipmentManager... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedEquipmentManager versionedEquipmentManager = new VersionedEquipmentManager(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedEquipmentManager.resetData(addressBookStates[i]);
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
