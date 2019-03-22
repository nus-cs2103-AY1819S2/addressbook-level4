package seedu.travel.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.travel.testutil.TypicalPlaces.AMK;
import static seedu.travel.testutil.TypicalPlaces.BEDOK;
import static seedu.travel.testutil.TypicalPlaces.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.travel.testutil.TravelBuddyBuilder;

public class VersionedTravelBuddyTest {

    private final ReadOnlyTravelBuddy travelBuddyAtAmk = new TravelBuddyBuilder().withPlace(AMK).build();
    private final ReadOnlyTravelBuddy travelBuddyAtBedok = new TravelBuddyBuilder().withPlace(BEDOK).build();
    private final ReadOnlyTravelBuddy travelBuddyAtCarl = new TravelBuddyBuilder().withPlace(CARL).build();
    private final ReadOnlyTravelBuddy emptyTravelBuddy = new TravelBuddyBuilder().build();

    @Test
    public void commit_singleTravelBuddy_noStatesRemovedCurrentStateSaved() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(emptyTravelBuddy);

        versionedTravelBuddy.commit();
        assertTravelBuddyListStatus(versionedTravelBuddy,
                Collections.singletonList(emptyTravelBuddy),
                emptyTravelBuddy,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTravelBuddyPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);

        versionedTravelBuddy.commit();
        assertTravelBuddyListStatus(versionedTravelBuddy,
                Arrays.asList(emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok),
                travelBuddyAtBedok,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTravelBuddyPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);
        shiftCurrentStatePointerLeftwards(versionedTravelBuddy, 2);

        versionedTravelBuddy.commit();
        assertTravelBuddyListStatus(versionedTravelBuddy,
                Collections.singletonList(emptyTravelBuddy),
                emptyTravelBuddy,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleTravelBuddyPointerAtEndOfStateList_returnsTrue() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);

        assertTrue(versionedTravelBuddy.canUndo());
    }

    @Test
    public void canUndo_multipleTravelBuddyPointerAtStartOfStateList_returnsTrue() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);
        shiftCurrentStatePointerLeftwards(versionedTravelBuddy, 1);

        assertTrue(versionedTravelBuddy.canUndo());
    }

    @Test
    public void canUndo_singleTravelBuddy_returnsFalse() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(emptyTravelBuddy);

        assertFalse(versionedTravelBuddy.canUndo());
    }

    @Test
    public void canUndo_multipleTravelBuddyPointerAtStartOfStateList_returnsFalse() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);
        shiftCurrentStatePointerLeftwards(versionedTravelBuddy, 2);

        assertFalse(versionedTravelBuddy.canUndo());
    }

    @Test
    public void canRedo_multipleTravelBuddyPointerNotAtEndOfStateList_returnsTrue() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);
        shiftCurrentStatePointerLeftwards(versionedTravelBuddy, 1);

        assertTrue(versionedTravelBuddy.canRedo());
    }

    @Test
    public void canRedo_multipleTravelBuddyPointerAtStartOfStateList_returnsTrue() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);
        shiftCurrentStatePointerLeftwards(versionedTravelBuddy, 2);

        assertTrue(versionedTravelBuddy.canRedo());
    }

    @Test
    public void canRedo_singleTravelBuddy_returnsFalse() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(emptyTravelBuddy);

        assertFalse(versionedTravelBuddy.canRedo());
    }

    @Test
    public void canRedo_multipleTravelBuddyPointerAtEndOfStateList_returnsFalse() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);

        assertFalse(versionedTravelBuddy.canRedo());
    }

    @Test
    public void undo_multipleTravelBuddyPointerAtEndOfStateList_success() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);

        versionedTravelBuddy.undo();
        assertTravelBuddyListStatus(versionedTravelBuddy,
                Collections.singletonList(emptyTravelBuddy),
                travelBuddyAtAmk,
                Collections.singletonList(travelBuddyAtBedok));
    }

    @Test
    public void undo_multipleTravelBuddyPointerNotAtStartOfStateList_success() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);
        shiftCurrentStatePointerLeftwards(versionedTravelBuddy, 1);

        versionedTravelBuddy.undo();
        assertTravelBuddyListStatus(versionedTravelBuddy,
                Collections.emptyList(),
                emptyTravelBuddy,
                Arrays.asList(travelBuddyAtAmk, travelBuddyAtBedok));
    }

    @Test
    public void undo_singleTravelBuddy_throwsNoUndoableStateException() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(emptyTravelBuddy);

        assertThrows(VersionedTravelBuddy.NoUndoableStateException.class, versionedTravelBuddy::undo);
    }

    @Test
    public void undo_multipleTravelBuddyPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);
        shiftCurrentStatePointerLeftwards(versionedTravelBuddy, 2);

        assertThrows(VersionedTravelBuddy.NoUndoableStateException.class, versionedTravelBuddy::undo);
    }

    @Test
    public void redo_multipleTravelBuddyPointerNotAtEndOfStateList_success() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);
        shiftCurrentStatePointerLeftwards(versionedTravelBuddy, 1);

        versionedTravelBuddy.redo();
        assertTravelBuddyListStatus(versionedTravelBuddy,
                Arrays.asList(emptyTravelBuddy, travelBuddyAtAmk),
                travelBuddyAtBedok,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleTravelBuddyPointerAtStartOfStateList_success() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);
        shiftCurrentStatePointerLeftwards(versionedTravelBuddy, 2);

        versionedTravelBuddy.redo();
        assertTravelBuddyListStatus(versionedTravelBuddy,
                Collections.singletonList(emptyTravelBuddy),
                travelBuddyAtAmk,
                Collections.singletonList(travelBuddyAtBedok));
    }

    @Test
    public void redo_singleTravelBuddy_throwsNoRedoableStateException() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(emptyTravelBuddy);

        assertThrows(VersionedTravelBuddy.NoRedoableStateException.class, versionedTravelBuddy::redo);
    }

    @Test
    public void redo_multipleTravelBuddyPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(
                emptyTravelBuddy, travelBuddyAtAmk, travelBuddyAtBedok);

        assertThrows(VersionedTravelBuddy.NoRedoableStateException.class, versionedTravelBuddy::redo);
    }

    @Test
    public void equals() {
        VersionedTravelBuddy versionedTravelBuddy = prepareTravelBuddyList(travelBuddyAtAmk, travelBuddyAtBedok);

        // same values -> returns true
        VersionedTravelBuddy copy = prepareTravelBuddyList(travelBuddyAtAmk, travelBuddyAtBedok);
        assertTrue(versionedTravelBuddy.equals(copy));

        // same object -> returns true
        assertTrue(versionedTravelBuddy.equals(versionedTravelBuddy));

        // null -> returns false
        assertFalse(versionedTravelBuddy.equals(null));

        // different types -> returns false
        assertFalse(versionedTravelBuddy.equals(1));

        // different state list -> returns false
        VersionedTravelBuddy differentTravelBuddyList = prepareTravelBuddyList(travelBuddyAtBedok,
                travelBuddyAtCarl);
        assertFalse(versionedTravelBuddy.equals(differentTravelBuddyList));

        // different current pointer index -> returns false
        VersionedTravelBuddy differentCurrentStatePointer = prepareTravelBuddyList(
                travelBuddyAtAmk, travelBuddyAtBedok);
        shiftCurrentStatePointerLeftwards(versionedTravelBuddy, 1);
        assertFalse(versionedTravelBuddy.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedTravelBuddy} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedTravelBuddy#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedTravelBuddy#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertTravelBuddyListStatus(VersionedTravelBuddy versionedTravelBuddy,
                                             List<ReadOnlyTravelBuddy> expectedStatesBeforePointer,
                                             ReadOnlyTravelBuddy expectedCurrentState,
                                             List<ReadOnlyTravelBuddy> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new TravelBuddy(versionedTravelBuddy), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedTravelBuddy.canUndo()) {
            versionedTravelBuddy.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyTravelBuddy expectedTravelBuddy : expectedStatesBeforePointer) {
            assertEquals(expectedTravelBuddy, new TravelBuddy(versionedTravelBuddy));
            versionedTravelBuddy.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyTravelBuddy expectedTravelBuddy : expectedStatesAfterPointer) {
            versionedTravelBuddy.redo();
            assertEquals(expectedTravelBuddy, new TravelBuddy(versionedTravelBuddy));
        }

        // check that there are no more states after pointer
        assertFalse(versionedTravelBuddy.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedTravelBuddy.undo());
    }

    /**
     * Creates and returns a {@code VersionedTravelBuddy} with the {@code travelBuddyStates} added into it, and the
     * {@code VersionedTravelBuddy#currentStatePointer} at the end of list.
     */
    private VersionedTravelBuddy prepareTravelBuddyList(ReadOnlyTravelBuddy... travelBuddyStates) {
        assertFalse(travelBuddyStates.length == 0);

        VersionedTravelBuddy versionedTravelBuddy = new VersionedTravelBuddy(travelBuddyStates[0]);
        for (int i = 1; i < travelBuddyStates.length; i++) {
            versionedTravelBuddy.resetData(travelBuddyStates[i]);
            versionedTravelBuddy.commit();
        }

        return versionedTravelBuddy;
    }

    /**
     * Shifts the {@code versionedTravelBuddy#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedTravelBuddy versionedTravelBuddy, int count) {
        for (int i = 0; i < count; i++) {
            versionedTravelBuddy.undo();
        }
    }
}
