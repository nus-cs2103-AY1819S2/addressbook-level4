package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalApparels.ARMANY;
import static seedu.address.testutil.TypicalApparels.BOBYIN;
import static seedu.address.testutil.TypicalApparels.SHIRT2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.AddressBookBuilder;

public class VersionedFashionMatchTest {

    private final ReadOnlyFashionMatch addressBookWithAmy = new AddressBookBuilder().withPerson(ARMANY).build();
    private final ReadOnlyFashionMatch addressBookWithBob = new AddressBookBuilder().withPerson(BOBYIN).build();
    private final ReadOnlyFashionMatch addressBookWithCarl = new AddressBookBuilder().withPerson(SHIRT2).build();
    private final ReadOnlyFashionMatch emptyAddressBook = new AddressBookBuilder().build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(emptyAddressBook);

        versionedFashionMatch.commit();
        assertAddressBookListStatus(versionedFashionMatch,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedFashionMatch.commit();
        assertAddressBookListStatus(versionedFashionMatch,
                Arrays.asList(emptyAddressBook, addressBookWithAmy, addressBookWithBob),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFashionMatch, 2);

        versionedFashionMatch.commit();
        assertAddressBookListStatus(versionedFashionMatch,
                Collections.singletonList(emptyAddressBook),
                emptyAddressBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertTrue(versionedFashionMatch.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFashionMatch, 1);

        assertTrue(versionedFashionMatch.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedFashionMatch.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFashionMatch, 2);

        assertFalse(versionedFashionMatch.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFashionMatch, 1);

        assertTrue(versionedFashionMatch.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFashionMatch, 2);

        assertTrue(versionedFashionMatch.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(emptyAddressBook);

        assertFalse(versionedFashionMatch.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertFalse(versionedFashionMatch.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        versionedFashionMatch.undo();
        assertAddressBookListStatus(versionedFashionMatch,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFashionMatch, 1);

        versionedFashionMatch.undo();
        assertAddressBookListStatus(versionedFashionMatch,
                Collections.emptyList(),
                emptyAddressBook,
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedFashionMatch.NoUndoableStateException.class, versionedFashionMatch::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFashionMatch, 2);

        assertThrows(VersionedFashionMatch.NoUndoableStateException.class, versionedFashionMatch::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFashionMatch, 1);

        versionedFashionMatch.redo();
        assertAddressBookListStatus(versionedFashionMatch,
                Arrays.asList(emptyAddressBook, addressBookWithAmy),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFashionMatch, 2);

        versionedFashionMatch.redo();
        assertAddressBookListStatus(versionedFashionMatch,
                Collections.singletonList(emptyAddressBook),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(emptyAddressBook);

        assertThrows(VersionedFashionMatch.NoRedoableStateException.class, versionedFashionMatch::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(
                emptyAddressBook, addressBookWithAmy, addressBookWithBob);

        assertThrows(VersionedFashionMatch.NoRedoableStateException.class, versionedFashionMatch::redo);
    }

    @Test
    public void equals() {
        VersionedFashionMatch versionedFashionMatch = prepareAddressBookList(addressBookWithAmy, addressBookWithBob);

        // same values -> returns true
        VersionedFashionMatch copy = prepareAddressBookList(addressBookWithAmy, addressBookWithBob);
        assertTrue(versionedFashionMatch.equals(copy));

        // same object -> returns true
        assertTrue(versionedFashionMatch.equals(versionedFashionMatch));

        // null -> returns false
        assertFalse(versionedFashionMatch.equals(null));

        // different types -> returns false
        assertFalse(versionedFashionMatch.equals(1));

        // different state list -> returns false
        VersionedFashionMatch differentAddressBookList =
                prepareAddressBookList(addressBookWithBob, addressBookWithCarl);
        assertFalse(versionedFashionMatch.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedFashionMatch differentCurrentStatePointer = prepareAddressBookList(
                addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedFashionMatch, 1);
        assertFalse(versionedFashionMatch.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedFashionMatch} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedFashionMatch#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedFashionMatch#currentStatePointer}
     * is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAddressBookListStatus(VersionedFashionMatch versionedFashionMatch,
                                             List<ReadOnlyFashionMatch> expectedStatesBeforePointer,
                                             ReadOnlyFashionMatch expectedCurrentState,
                                             List<ReadOnlyFashionMatch> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new FashionMatch(versionedFashionMatch), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedFashionMatch.canUndo()) {
            versionedFashionMatch.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyFashionMatch expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new FashionMatch(versionedFashionMatch));
            versionedFashionMatch.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyFashionMatch expectedAddressBook : expectedStatesAfterPointer) {
            versionedFashionMatch.redo();
            assertEquals(expectedAddressBook, new FashionMatch(versionedFashionMatch));
        }

        // check that there are no more states after pointer
        assertFalse(versionedFashionMatch.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedFashionMatch.undo());
    }

    /**
     * Creates and returns a {@code VersionedFashionMatch} with the {@code addressBookStates} added into it, and the
     * {@code VersionedFashionMatch#currentStatePointer} at the end of list.
     */
    private VersionedFashionMatch prepareAddressBookList(ReadOnlyFashionMatch... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedFashionMatch versionedFashionMatch = new VersionedFashionMatch(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedFashionMatch.resetData(addressBookStates[i]);
            versionedFashionMatch.commit();
        }

        return versionedFashionMatch;
    }

    /**
     * Shifts the {@code versionedFashionMatch#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedFashionMatch versionedFashionMatch, int count) {
        for (int i = 0; i < count; i++) {
            versionedFashionMatch.undo();
        }
    }
}
