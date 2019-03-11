package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalRequests.ALICE_REQUEST;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.RequestBookBuilder;
import seedu.address.testutil.TypicalRequests;

class VersionedRequestBookTest {

    private final ReadOnlyRequestBook requestBookWithAlice =
        new RequestBookBuilder().withRequest(ALICE_REQUEST).build();
    private final ReadOnlyRequestBook requestBookWithBenson =
        new RequestBookBuilder().withRequest(TypicalRequests.BENSON_REQUEST).build();
    private final ReadOnlyRequestBook requestBookWithCarl =
        new RequestBookBuilder().withRequest(TypicalRequests.CARL_REQUEST).build();
    private final ReadOnlyRequestBook emptyRequestBook = new RequestBookBuilder().build();

    @Test
    public void commit_singleRequestBook_noStatesRemovedCurrentStateSaved() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(emptyRequestBook);

        versionedRequestBook.commit();
        assertRequestBookListStatus(versionedRequestBook,
            Collections.singletonList(emptyRequestBook),
            emptyRequestBook,
            Collections.emptyList());
    }

    @org.junit.jupiter.api.Test
    public void canUndo_multipleRequestBookPointerAtEndOfStateList_returnsTrue() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);

        assertTrue(versionedRequestBook.canUndo());
    }

    @org.junit.jupiter.api.Test
    public void prepareRequestBookList_equals_returnsTrue() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);

        VersionedRequestBook secondBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);

        assertTrue(versionedRequestBook.equals(secondBook));
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);
        shiftCurrentStatePointerLeftwards(versionedRequestBook, 1);

        assertTrue(versionedRequestBook.canUndo());
    }

    @Test
    public void canUndo_singleRequestBook_returnsFalse() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(emptyRequestBook);

        assertFalse(versionedRequestBook.canUndo());
    }

    @Test
    public void canUndo_multipleRequestBookPointerAtStartOfStateList_returnsFalse() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);
        shiftCurrentStatePointerLeftwards(versionedRequestBook, 2);

        assertFalse(versionedRequestBook.canUndo());
    }

    @Test
    public void canRedo_multipleRequestBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);
        shiftCurrentStatePointerLeftwards(versionedRequestBook, 1);

        assertTrue(versionedRequestBook.canRedo());
    }

    @Test
    public void canRedo_multipleRequestBookPointerAtStartOfStateList_returnsTrue() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);
        shiftCurrentStatePointerLeftwards(versionedRequestBook, 2);

        assertTrue(versionedRequestBook.canRedo());
    }

    @Test
    public void canRedo_singleRequestBook_returnsFalse() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(emptyRequestBook);

        assertFalse(versionedRequestBook.canRedo());
    }

    @Test
    public void canRedo_multipleRequestBookPointerAtEndOfStateList_returnsFalse() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);

        assertFalse(versionedRequestBook.canRedo());
    }

    @Test
    public void undo_multipleRequestBookPointerAtEndOfStateList_success() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);

        versionedRequestBook.undo();
        assertRequestBookListStatus(versionedRequestBook,
            Collections.singletonList(emptyRequestBook),
            requestBookWithAlice,
            Collections.singletonList(requestBookWithBenson));
    }

    @Test
    public void undo_multipleRequestBookPointerNotAtStartOfStateList_success() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);
        shiftCurrentStatePointerLeftwards(versionedRequestBook, 1);

        versionedRequestBook.undo();
        assertRequestBookListStatus(versionedRequestBook,
            Collections.emptyList(),
            emptyRequestBook,
            Arrays.asList(requestBookWithAlice, requestBookWithBenson));
    }

    @Test
    public void undo_singleRequestBook_throwsNoUndoableStateException() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(emptyRequestBook);

        assertThrows(VersionedRequestBook.NoUndoableStateException.class, versionedRequestBook::undo);
    }

    @Test
    public void undo_multipleRequestBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);
        shiftCurrentStatePointerLeftwards(versionedRequestBook, 2);

        assertThrows(VersionedRequestBook.NoUndoableStateException.class, versionedRequestBook::undo);
    }

    @Test
    public void redo_multipleRequestBookPointerNotAtEndOfStateList_success() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);
        shiftCurrentStatePointerLeftwards(versionedRequestBook, 1);

        versionedRequestBook.redo();
        assertRequestBookListStatus(versionedRequestBook,
            Arrays.asList(emptyRequestBook, requestBookWithAlice),
            requestBookWithBenson,
            Collections.emptyList());
    }

    @Test
    public void redo_multipleRequestBookPointerAtStartOfStateList_success() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);
        shiftCurrentStatePointerLeftwards(versionedRequestBook, 2);

        versionedRequestBook.redo();
        assertRequestBookListStatus(versionedRequestBook,
            Collections.singletonList(emptyRequestBook),
            requestBookWithAlice,
            Collections.singletonList(requestBookWithBenson));
    }

    @Test
    public void redo_singleRequestBook_throwsNoRedoableStateException() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(emptyRequestBook);

        assertThrows(VersionedRequestBook.NoRedoableStateException.class, versionedRequestBook::redo);
    }

    @Test
    public void redo_multipleRequestBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(
            emptyRequestBook, requestBookWithAlice, requestBookWithBenson);

        assertThrows(VersionedRequestBook.NoRedoableStateException.class, versionedRequestBook::redo);
    }

    @Test
    public void equals() {
        VersionedRequestBook versionedRequestBook = prepareRequestBookList(requestBookWithAlice, requestBookWithBenson);

        // same values -> returns true
        VersionedRequestBook copy = prepareRequestBookList(requestBookWithAlice, requestBookWithBenson);
        assertTrue(versionedRequestBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedRequestBook.equals(versionedRequestBook));

        // null -> returns false
        assertFalse(versionedRequestBook.equals(null));

        // different types -> returns false
        assertFalse(versionedRequestBook.equals(1));

        // different state list -> returns false
        VersionedRequestBook differentAddressBookList = prepareRequestBookList(requestBookWithBenson,
            requestBookWithCarl);
        assertFalse(versionedRequestBook.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedRequestBook differentCurrentStatePointer = prepareRequestBookList(
            requestBookWithAlice, requestBookWithBenson);
        shiftCurrentStatePointerLeftwards(versionedRequestBook, 1);
        assertFalse(versionedRequestBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedRequestBook} is currently pointing at {@code
     * expectedCurrentState},
     * states before {@code versionedRequestBook#currentStatePointer} is equal to {@code
     * expectedStatesBeforePointer}, and states after {@code versionedRequestBook
     * #currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertRequestBookListStatus(VersionedRequestBook versionedRequestBook,
                                             List<ReadOnlyRequestBook> expectedStatesBeforePointer,
                                             ReadOnlyRequestBook expectedCurrentState,
                                             List<ReadOnlyRequestBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new RequestBook(versionedRequestBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedRequestBook.canUndo()) {
            versionedRequestBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyRequestBook readOnlyRequestBook : expectedStatesBeforePointer) {
            assertEquals(readOnlyRequestBook, new RequestBook(versionedRequestBook));
            versionedRequestBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyRequestBook readOnlyRequestBook : expectedStatesAfterPointer) {
            versionedRequestBook.redo();
            assertEquals(readOnlyRequestBook, new RequestBook(versionedRequestBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedRequestBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedRequestBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedRequestBook} with the {@code requestBookStates} added
     * into it, and the
     * {@code VersionedRequestBook#currentStatePointer} at the end of list.
     */
    private VersionedRequestBook prepareRequestBookList(ReadOnlyRequestBook... requestBookStates) {
        assertFalse(requestBookStates.length == 0);

        VersionedRequestBook versionedRequestBook = new VersionedRequestBook(requestBookStates[0]);
        for (int i = 1; i < requestBookStates.length; i++) {
            versionedRequestBook.resetData(requestBookStates[i]);
            versionedRequestBook.commit();
        }

        return versionedRequestBook;
    }

    /**
     * Shifts the {@code versionedRequestBook#currentStatePointer} by {@code count} to the left of
     * its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedRequestBook versionedRequestBook,
                                                   int count) {
        for (int i = 0; i < count; i++) {
            versionedRequestBook.undo();
        }
    }
}
