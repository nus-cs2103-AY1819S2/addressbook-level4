package seedu.knowitall.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.knowitall.testutil.TypicalCards.CARD_1;
import static seedu.knowitall.testutil.TypicalCards.CARD_2;
import static seedu.knowitall.testutil.TypicalCards.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.knowitall.testutil.CardFolderBuilder;

public class VersionedCardFolderTest {

    private final ReadOnlyCardFolder cardFolderWithAmy = new CardFolderBuilder().withCard(CARD_1).build();
    private final ReadOnlyCardFolder cardFolderWithBob = new CardFolderBuilder().withCard(CARD_2).build();
    private final ReadOnlyCardFolder cardFolderWithCarl = new CardFolderBuilder().withCard(CARL).build();
    private final ReadOnlyCardFolder emptyCardFolder = new CardFolderBuilder().build();

    @Test
    public void commit_singleCardFolder_noStatesRemovedCurrentStateSaved() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(emptyCardFolder);

        versionedCardFolder.commit();
        assertCardFolderListStatus(versionedCardFolder,
                Collections.singletonList(emptyCardFolder),
                emptyCardFolder,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleCardFolderPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);

        versionedCardFolder.commit();
        assertCardFolderListStatus(versionedCardFolder,
                Arrays.asList(emptyCardFolder, cardFolderWithAmy, cardFolderWithBob),
                cardFolderWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleCardFolderPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);
        shiftCurrentStatePointerLeftwards(versionedCardFolder, 2);

        versionedCardFolder.commit();
        assertCardFolderListStatus(versionedCardFolder,
                Collections.singletonList(emptyCardFolder),
                emptyCardFolder,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleCardFolderPointerAtEndOfStateList_returnsTrue() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);

        assertTrue(versionedCardFolder.canUndo());
    }

    @Test
    public void canUndo_multipleCardFolderPointerAtStartOfStateList_returnsTrue() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);
        shiftCurrentStatePointerLeftwards(versionedCardFolder, 1);

        assertTrue(versionedCardFolder.canUndo());
    }

    @Test
    public void canUndo_singleCardFolder_returnsFalse() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(emptyCardFolder);

        assertFalse(versionedCardFolder.canUndo());
    }

    @Test
    public void canUndo_multipleCardFolderPointerAtStartOfStateList_returnsFalse() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);
        shiftCurrentStatePointerLeftwards(versionedCardFolder, 2);

        assertFalse(versionedCardFolder.canUndo());
    }

    @Test
    public void canRedo_multipleCardFolderPointerNotAtEndOfStateList_returnsTrue() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);
        shiftCurrentStatePointerLeftwards(versionedCardFolder, 1);

        assertTrue(versionedCardFolder.canRedo());
    }

    @Test
    public void canRedo_multipleCardFolderPointerAtStartOfStateList_returnsTrue() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);
        shiftCurrentStatePointerLeftwards(versionedCardFolder, 2);

        assertTrue(versionedCardFolder.canRedo());
    }

    @Test
    public void canRedo_singleCardFolder_returnsFalse() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(emptyCardFolder);

        assertFalse(versionedCardFolder.canRedo());
    }

    @Test
    public void canRedo_multipleCardFolderPointerAtEndOfStateList_returnsFalse() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);

        assertFalse(versionedCardFolder.canRedo());
    }

    @Test
    public void undo_multipleCardFolderPointerAtEndOfStateList_success() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);

        versionedCardFolder.undo();
        assertCardFolderListStatus(versionedCardFolder,
                Collections.singletonList(emptyCardFolder),
                cardFolderWithAmy,
                Collections.singletonList(cardFolderWithBob));
    }

    @Test
    public void undo_multipleCardFolderPointerNotAtStartOfStateList_success() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);
        shiftCurrentStatePointerLeftwards(versionedCardFolder, 1);

        versionedCardFolder.undo();
        assertCardFolderListStatus(versionedCardFolder,
                Collections.emptyList(),
                emptyCardFolder,
                Arrays.asList(cardFolderWithAmy, cardFolderWithBob));
    }

    @Test
    public void undo_singleCardFolder_throwsNoUndoableStateException() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(emptyCardFolder);

        assertThrows(VersionedCardFolder.NoUndoableStateException.class, versionedCardFolder::undo);
    }

    @Test
    public void undo_multipleCardFolderPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);
        shiftCurrentStatePointerLeftwards(versionedCardFolder, 2);

        assertThrows(VersionedCardFolder.NoUndoableStateException.class, versionedCardFolder::undo);
    }

    @Test
    public void redo_multipleCardFolderPointerNotAtEndOfStateList_success() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);
        shiftCurrentStatePointerLeftwards(versionedCardFolder, 1);

        versionedCardFolder.redo();
        assertCardFolderListStatus(versionedCardFolder,
                Arrays.asList(emptyCardFolder, cardFolderWithAmy),
                cardFolderWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleCardFolderPointerAtStartOfStateList_success() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);
        shiftCurrentStatePointerLeftwards(versionedCardFolder, 2);

        versionedCardFolder.redo();
        assertCardFolderListStatus(versionedCardFolder,
                Collections.singletonList(emptyCardFolder),
                cardFolderWithAmy,
                Collections.singletonList(cardFolderWithBob));
    }

    @Test
    public void redo_singleCardFolder_throwsNoRedoableStateException() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(emptyCardFolder);

        assertThrows(VersionedCardFolder.NoRedoableStateException.class, versionedCardFolder::redo);
    }

    @Test
    public void redo_multipleCardFolderPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(
                emptyCardFolder, cardFolderWithAmy, cardFolderWithBob);

        assertThrows(VersionedCardFolder.NoRedoableStateException.class, versionedCardFolder::redo);
    }

    @Test
    public void equals() {
        VersionedCardFolder versionedCardFolder = prepareCardFolderList(cardFolderWithAmy, cardFolderWithBob);

        // same values -> returns true
        VersionedCardFolder copy = prepareCardFolderList(cardFolderWithAmy, cardFolderWithBob);
        assertTrue(versionedCardFolder.equals(copy));

        // same object -> returns true
        assertTrue(versionedCardFolder.equals(versionedCardFolder));

        // null -> returns false
        assertFalse(versionedCardFolder.equals(null));

        // different types -> returns false
        assertFalse(versionedCardFolder.equals(1));

        // different state list -> returns false
        VersionedCardFolder differentCardFolderList = prepareCardFolderList(cardFolderWithBob, cardFolderWithCarl);
        assertFalse(versionedCardFolder.equals(differentCardFolderList));

        // different current pointer index -> returns false
        VersionedCardFolder differentCurrentStatePointer = prepareCardFolderList(
                cardFolderWithAmy, cardFolderWithBob);
        shiftCurrentStatePointerLeftwards(versionedCardFolder, 1);
        assertFalse(versionedCardFolder.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedCardFolder} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedCardFolder#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedCardFolder#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertCardFolderListStatus(VersionedCardFolder versionedCardFolder,
                                             List<ReadOnlyCardFolder> expectedStatesBeforePointer,
                                             ReadOnlyCardFolder expectedCurrentState,
                                             List<ReadOnlyCardFolder> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new CardFolder(versionedCardFolder), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedCardFolder.canUndo()) {
            versionedCardFolder.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyCardFolder expectedCardFolder : expectedStatesBeforePointer) {
            assertEquals(expectedCardFolder, new CardFolder(versionedCardFolder));
            versionedCardFolder.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyCardFolder expectedCardFolder : expectedStatesAfterPointer) {
            versionedCardFolder.redo();
            assertEquals(expectedCardFolder, new CardFolder(versionedCardFolder));
        }

        // check that there are no more states after pointer
        assertFalse(versionedCardFolder.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedCardFolder.undo());
    }

    /**
     * Creates and returns a {@code VersionedCardFolder} with the {@code cardFolderStates} added into it, and the
     * {@code VersionedCardFolder#currentStatePointer} at the end of list.
     */
    private VersionedCardFolder prepareCardFolderList(ReadOnlyCardFolder... cardFolderStates) {
        assertFalse(cardFolderStates.length == 0);

        VersionedCardFolder versionedCardFolder = new VersionedCardFolder(cardFolderStates[0]);
        for (int i = 1; i < cardFolderStates.length; i++) {
            versionedCardFolder.resetData(cardFolderStates[i]);
            versionedCardFolder.commit();
        }

        return versionedCardFolder;
    }

    /**
     * Shifts the {@code versionedCardFolder#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedCardFolder versionedCardFolder, int count) {
        for (int i = 0; i < count; i++) {
            versionedCardFolder.undo();
        }
    }
}
