//TODO @Lukaz help me pls
//package seedu.address.model;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
//import static seedu.address.testutil.TypicalHealthWorkers.BETTY;
//import static seedu.address.testutil.TypicalHealthWorkers.CARLIE;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.Test;
//
//import seedu.address.testutil.Assert;
//import seedu.address.testutil.HealthWorkerBookBuilder;
//
//public class VersionedHealthWorkerBookTest {
//
//    private final ReadOnlyHealthWorkerBook healthWorkerBookWithAndy = new HealthWorkerBookBuilder()
//            .withHealthWorker(ANDY).build();
//    private final ReadOnlyHealthWorkerBook healthWorkerBookWithBetty = new HealthWorkerBookBuilder()
//            .withHealthWorker(BETTY).build();
//    private final ReadOnlyHealthWorkerBook healthWorkerBookWithCarlie = new HealthWorkerBookBuilder()
//            .withHealthWorker(CARLIE).build();
//    private final ReadOnlyHealthWorkerBook emptyHealthWorkerBook = new HealthWorkerBookBuilder().build();
//
//    @Test
//    public void commit() {
//        // no states
//        VersionedHealthWorkerBook versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook);
//        versionedHealthWorkerBook.commit();
//        assertHealthWorkerBookListStatus(versionedHealthWorkerBook, Collections.singletonList(emptyHealthWorkerBook),
//                emptyHealthWorkerBook, Collections.emptyList());
//
//        // multiple states, all saved
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook, healthWorkerBookWithAndy,
//                healthWorkerBookWithBetty);
//        versionedHealthWorkerBook.commit();
//        assertHealthWorkerBookListStatus(versionedHealthWorkerBook,
//                Arrays.asList(emptyHealthWorkerBook, healthWorkerBookWithAndy, healthWorkerBookWithBetty),
//                healthWorkerBookWithBetty, Collections.emptyList());
//
//        // multiple states, current state pointer not at the end of list
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook, healthWorkerBookWithAndy,
//                healthWorkerBookWithBetty);
//        VersionedAddressBookTest.shiftCurrentStatePointerLeftwards(versionedHealthWorkerBook, 2);
//        versionedHealthWorkerBook.commit();
//        assertHealthWorkerBookListStatus(versionedHealthWorkerBook,
//                Arrays.asList(emptyHealthWorkerBook),
//                emptyHealthWorkerBook, Collections.emptyList());
//
//    }
//
//    @Test
//    public void canUndo() {
//        // multiple states -> returns true
//        VersionedHealthWorkerBook versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy, healthWorkerBookWithBetty);
//        assertTrue(versionedHealthWorkerBook.canUndo());
//
//        // multiple states, current pointer in middle of state -> returns true
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy, healthWorkerBookWithBetty);
//        VersionedAddressBookTest.shiftCurrentStatePointerLeftwards(versionedHealthWorkerBook, 1);
//        assertTrue(versionedHealthWorkerBook.canUndo());
//
//        // single state -> returns false
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook);
//        assertFalse(versionedHealthWorkerBook.canUndo());
//
//        // multiple states, starting state -> returns false
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy, healthWorkerBookWithBetty);
//        VersionedAddressBookTest.shiftCurrentStatePointerLeftwards(versionedHealthWorkerBook, 2);
//        assertFalse(versionedHealthWorkerBook.canUndo());
//
//    }
//
//    @Test
//    public void canRedo() {
//        // state pointer not at end -> returns true
//        VersionedHealthWorkerBook versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy, healthWorkerBookWithBetty);
//        VersionedAddressBookTest.shiftCurrentStatePointerLeftwards(versionedHealthWorkerBook, 1);
//        assertTrue(versionedHealthWorkerBook.canRedo());
//
//        // state pointer at beginning of list -> return true
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy, healthWorkerBookWithBetty);
//        VersionedAddressBookTest.shiftCurrentStatePointerLeftwards(versionedHealthWorkerBook, 2);
//        assertTrue(versionedHealthWorkerBook.canRedo());
//
//        // state pointer at end of list -> return false
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy, healthWorkerBookWithBetty);
//        assertFalse(versionedHealthWorkerBook.canRedo());
//
//        // single state -> return false
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook);
//        assertFalse(versionedHealthWorkerBook.canRedo());
//    }
//
//    @Test
//    public void undo() {
//        // multiple states, end of state list
//        VersionedHealthWorkerBook versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy, healthWorkerBookWithBetty);
//        versionedHealthWorkerBook.undo();
//        assertHealthWorkerBookListStatus(versionedHealthWorkerBook, Collections.singletonList(emptyHealthWorkerBook),
//                healthWorkerBookWithAndy, Collections.singletonList(healthWorkerBookWithBetty));
//
//        // multiple states, middle of state list
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy, healthWorkerBookWithBetty);
//        VersionedAddressBookTest.shiftCurrentStatePointerLeftwards(versionedHealthWorkerBook, 1);
//        versionedHealthWorkerBook.undo();
//        assertHealthWorkerBookListStatus(versionedHealthWorkerBook, Collections.emptyList(),
//                emptyHealthWorkerBook, Arrays.asList(healthWorkerBookWithAndy,
//                        healthWorkerBookWithBetty));
//
//        // single state
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook);
//        Assert.assertThrows(VersionedBook.NoUndoableStateException.class, versionedHealthWorkerBook::undo);
//
//        // multiple states, start of state list
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy, healthWorkerBookWithBetty);
//        VersionedAddressBookTest.shiftCurrentStatePointerLeftwards(versionedHealthWorkerBook, 2);
//        Assert.assertThrows(VersionedBook.NoUndoableStateException.class, versionedHealthWorkerBook::undo);
//    }
//
//    @Test
//    public void redo() {
//        // multiple states, current pointer not at end of state list
//        VersionedHealthWorkerBook versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy, healthWorkerBookWithBetty);
//        VersionedAddressBookTest.shiftCurrentStatePointerLeftwards(versionedHealthWorkerBook, 1);
//        versionedHealthWorkerBook.redo();
//        assertHealthWorkerBookListStatus(versionedHealthWorkerBook, Arrays.asList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy), healthWorkerBookWithBetty, Collections.emptyList());
//
//        // multiple states, current pointer at start of list
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy, healthWorkerBookWithBetty);
//        VersionedAddressBookTest.shiftCurrentStatePointerLeftwards(versionedHealthWorkerBook, 2);
//        versionedHealthWorkerBook.redo();
//        assertHealthWorkerBookListStatus(versionedHealthWorkerBook, Collections.singletonList(emptyHealthWorkerBook),
//                healthWorkerBookWithAndy, Collections.singletonList(healthWorkerBookWithBetty));
//
//        // single state
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook);
//        Assert.assertThrows(VersionedBook.NoRedoableStateException.class, versionedHealthWorkerBook::redo);
//
//        // multiple states, current pointer at end of state list
//        versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy, healthWorkerBookWithBetty);
//        Assert.assertThrows(VersionedBook.NoRedoableStateException.class, versionedHealthWorkerBook::redo);
//    }
//
//    @Test
//    public void equals() {
//        VersionedHealthWorkerBook versionedHealthWorkerBook = prepareHealthWorkerBookList(emptyHealthWorkerBook,
//                healthWorkerBookWithAndy);
//
//        // same object -> returns true
//        assertTrue(versionedHealthWorkerBook.equals(versionedHealthWorkerBook));
//
//        // same values -> return true
//        VersionedHealthWorkerBook copy = prepareHealthWorkerBookList(emptyHealthWorkerBook, healthWorkerBookWithAndy);
//        assertTrue(versionedHealthWorkerBook.equals(copy));
//
//        // null value -> return false
//        assertFalse(versionedHealthWorkerBook.equals(null));
//
//        // different types -> return false
//        assertFalse(versionedHealthWorkerBook.equals(1));
//
//        // different states -> return false
//        copy = prepareHealthWorkerBookList(healthWorkerBookWithBetty, healthWorkerBookWithCarlie);
//        assertFalse(versionedHealthWorkerBook.equals(copy));
//
//        copy = prepareHealthWorkerBookList(emptyHealthWorkerBook, healthWorkerBookWithAndy);
//        VersionedAddressBookTest.shiftCurrentStatePointerLeftwards(copy, 1);
//        assertFalse(versionedHealthWorkerBook.equals(copy));
//    }
//
//    /**
//     * Creates and returns a {@code VersionedHealthWorkerBook} with the {@code healthWorkerBookStates} added into it,
//     * and the {@code VersionedHealthWorkerBook#currStatePointer} at the end of list.
//     */
//    private VersionedHealthWorkerBook prepareHealthWorkerBookList(ReadOnlyHealthWorkerBook... healthWorkerBookStates) {
//        assertFalse(healthWorkerBookStates.length == 0);
//
//        VersionedHealthWorkerBook versionedHealthWorkerBook = new VersionedHealthWorkerBook(healthWorkerBookStates[0]);
//        for (int i = 1; i < healthWorkerBookStates.length; i++) {
//            versionedHealthWorkerBook.resetData(healthWorkerBookStates[i]);
//            versionedHealthWorkerBook.commit();
//        }
//
//        return versionedHealthWorkerBook;
//    }
//
//    /**
//     * Asserts that {@code versionedHealthWorkerBook} is currently pointing at {@code expectedCurrentState},
//     * states before {@code versionedHealthWorkerBook#currentStatePointer}
//     * is equal to {@code expectedStatesBeforePointer},
//     * and states after {@code versionedHealthWorkerBook#currentStatePointer}
//     * is equal to {@code expectedStatesAfterPointer}.
//     */
//    private void assertHealthWorkerBookListStatus(VersionedHealthWorkerBook versionedHealthWorkerBook,
//                                             List<ReadOnlyHealthWorkerBook> expectedStatesBeforePointer,
//                                             ReadOnlyHealthWorkerBook expectedCurrentState,
//                                             List<ReadOnlyHealthWorkerBook> expectedStatesAfterPointer) {
//        // check state currently pointing at is correct
//        assertEquals(new HealthWorkerBook(versionedHealthWorkerBook), expectedCurrentState);
//
//        // shift pointer to start of state list
//        while (versionedHealthWorkerBook.canUndo()) {
//            versionedHealthWorkerBook.undo();
//        }
//
//        // check states before pointer are correct
//        for (ReadOnlyHealthWorkerBook expectedAddressBook : expectedStatesBeforePointer) {
//            assertEquals(expectedAddressBook, new HealthWorkerBook(versionedHealthWorkerBook));
//            versionedHealthWorkerBook.redo();
//        }
//
//        // check states after pointer are correct
//        for (ReadOnlyHealthWorkerBook expectedAddressBook : expectedStatesAfterPointer) {
//            versionedHealthWorkerBook.redo();
//            assertEquals(expectedAddressBook, new HealthWorkerBook(versionedHealthWorkerBook));
//        }
//
//        // check that there are no more states after pointer
//        assertFalse(versionedHealthWorkerBook.canRedo());
//
//        // revert pointer to original position
//        expectedStatesAfterPointer.forEach(unused -> versionedHealthWorkerBook.undo());
//    }
//}
