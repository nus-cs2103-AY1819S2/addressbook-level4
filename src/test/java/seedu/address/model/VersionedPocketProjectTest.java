package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalEmployees.AMY;
import static seedu.address.testutil.TypicalEmployees.BOB;
import static seedu.address.testutil.TypicalEmployees.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PocketProjectBuilder;

public class VersionedPocketProjectTest {

    private final ReadOnlyPocketProject pocketProjectWithAmy = new PocketProjectBuilder().withEmployee(AMY).build();
    private final ReadOnlyPocketProject pocketProjectWithBob = new PocketProjectBuilder().withEmployee(BOB).build();
    private final ReadOnlyPocketProject pocketProjectWithCarl = new PocketProjectBuilder().withEmployee(CARL).build();
    private final ReadOnlyPocketProject emptyPocketProject = new PocketProjectBuilder().build();

    @Test
    public void commit_singlePocketProject_noStatesRemovedCurrentStateSaved() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(emptyPocketProject);

        versionedPocketProject.commit();
        assertPocketProjectListStatus(versionedPocketProject,
                Collections.singletonList(emptyPocketProject),
                emptyPocketProject,
                Collections.emptyList());
    }

    @Test
    public void commit_multiplePocketProjectPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);

        versionedPocketProject.commit();
        assertPocketProjectListStatus(versionedPocketProject,
                Arrays.asList(emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob),
                pocketProjectWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multiplePocketProjectPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);
        shiftCurrentStatePointerLeftwards(versionedPocketProject, 2);

        versionedPocketProject.commit();
        assertPocketProjectListStatus(versionedPocketProject,
                Collections.singletonList(emptyPocketProject),
                emptyPocketProject,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multiplePocketProjectPointerAtEndOfStateList_returnsTrue() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);

        assertTrue(versionedPocketProject.canUndo());
    }

    @Test
    public void canUndo_multiplePocketProjectPointerAtStartOfStateList_returnsTrue() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);
        shiftCurrentStatePointerLeftwards(versionedPocketProject, 1);

        assertTrue(versionedPocketProject.canUndo());
    }

    @Test
    public void canUndo_singlePocketProject_returnsFalse() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(emptyPocketProject);

        assertFalse(versionedPocketProject.canUndo());
    }

    @Test
    public void canUndo_multiplePocketProjectPointerAtStartOfStateList_returnsFalse() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);
        shiftCurrentStatePointerLeftwards(versionedPocketProject, 2);

        assertFalse(versionedPocketProject.canUndo());
    }

    @Test
    public void canRedo_multiplePocketProjectPointerNotAtEndOfStateList_returnsTrue() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);
        shiftCurrentStatePointerLeftwards(versionedPocketProject, 1);

        assertTrue(versionedPocketProject.canRedo());
    }

    @Test
    public void canRedo_multiplePocketProjectPointerAtStartOfStateList_returnsTrue() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);
        shiftCurrentStatePointerLeftwards(versionedPocketProject, 2);

        assertTrue(versionedPocketProject.canRedo());
    }

    @Test
    public void canRedo_singlePocketProject_returnsFalse() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(emptyPocketProject);

        assertFalse(versionedPocketProject.canRedo());
    }

    @Test
    public void canRedo_multiplePocketProjectPointerAtEndOfStateList_returnsFalse() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);

        assertFalse(versionedPocketProject.canRedo());
    }

    @Test
    public void undo_multiplePocketProjectPointerAtEndOfStateList_success() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);

        versionedPocketProject.undo();
        assertPocketProjectListStatus(versionedPocketProject,
                Collections.singletonList(emptyPocketProject),
                pocketProjectWithAmy,
                Collections.singletonList(pocketProjectWithBob));
    }

    @Test
    public void undo_multiplePocketProjectPointerNotAtStartOfStateList_success() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);
        shiftCurrentStatePointerLeftwards(versionedPocketProject, 1);

        versionedPocketProject.undo();
        assertPocketProjectListStatus(versionedPocketProject,
                Collections.emptyList(),
                emptyPocketProject,
                Arrays.asList(pocketProjectWithAmy, pocketProjectWithBob));
    }

    @Test
    public void undo_singlePocketProject_throwsNoUndoableStateException() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(emptyPocketProject);

        assertThrows(VersionedPocketProject.NoUndoableStateException.class, versionedPocketProject::undo);
    }

    @Test
    public void undo_multiplePocketProjectPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);
        shiftCurrentStatePointerLeftwards(versionedPocketProject, 2);

        assertThrows(VersionedPocketProject.NoUndoableStateException.class, versionedPocketProject::undo);
    }

    @Test
    public void redo_multiplePocketProjectPointerNotAtEndOfStateList_success() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);
        shiftCurrentStatePointerLeftwards(versionedPocketProject, 1);

        versionedPocketProject.redo();
        assertPocketProjectListStatus(versionedPocketProject,
                Arrays.asList(emptyPocketProject, pocketProjectWithAmy),
                pocketProjectWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multiplePocketProjectPointerAtStartOfStateList_success() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);
        shiftCurrentStatePointerLeftwards(versionedPocketProject, 2);

        versionedPocketProject.redo();
        assertPocketProjectListStatus(versionedPocketProject,
                Collections.singletonList(emptyPocketProject),
                pocketProjectWithAmy,
                Collections.singletonList(pocketProjectWithBob));
    }

    @Test
    public void redo_singlePocketProject_throwsNoRedoableStateException() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(emptyPocketProject);

        assertThrows(VersionedPocketProject.NoRedoableStateException.class, versionedPocketProject::redo);
    }

    @Test
    public void redo_multiplePocketProjectPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedPocketProject versionedPocketProject = preparePocketProjectList(
                emptyPocketProject, pocketProjectWithAmy, pocketProjectWithBob);

        assertThrows(VersionedPocketProject.NoRedoableStateException.class, versionedPocketProject::redo);
    }

    @Test
    public void equals() {
        VersionedPocketProject versionedPocketProject =
                preparePocketProjectList(pocketProjectWithAmy, pocketProjectWithBob);

        // same values -> returns true
        VersionedPocketProject copy = preparePocketProjectList(pocketProjectWithAmy, pocketProjectWithBob);
        assertTrue(versionedPocketProject.equals(copy));

        // same object -> returns true
        assertTrue(versionedPocketProject.equals(versionedPocketProject));

        // null -> returns false
        assertFalse(versionedPocketProject.equals(null));

        // different types -> returns false
        assertFalse(versionedPocketProject.equals(1));

        // different state list -> returns false
        VersionedPocketProject differentPocketProjectList =
            preparePocketProjectList(pocketProjectWithBob, pocketProjectWithCarl);
        assertFalse(versionedPocketProject.equals(differentPocketProjectList));

        // different current pointer index -> returns false
        VersionedPocketProject differentCurrentStatePointer = preparePocketProjectList(
                pocketProjectWithAmy, pocketProjectWithBob);
        shiftCurrentStatePointerLeftwards(versionedPocketProject, 1);
        assertFalse(versionedPocketProject.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedPocketProject} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedPocketProject#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedPocketProject#currentStatePointer} is equal to
     * {@code expectedStatesAfterPointer}.
     */
    private void assertPocketProjectListStatus(VersionedPocketProject versionedPocketProject,
                                               List<ReadOnlyPocketProject> expectedStatesBeforePointer,
                                               ReadOnlyPocketProject expectedCurrentState,
                                               List<ReadOnlyPocketProject> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new PocketProject(versionedPocketProject), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedPocketProject.canUndo()) {
            versionedPocketProject.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyPocketProject expectedPocketProject : expectedStatesBeforePointer) {
            assertEquals(expectedPocketProject, new PocketProject(versionedPocketProject));
            versionedPocketProject.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyPocketProject expectedPocketProject : expectedStatesAfterPointer) {
            versionedPocketProject.redo();
            assertEquals(expectedPocketProject, new PocketProject(versionedPocketProject));
        }

        // check that there are no more states after pointer
        assertFalse(versionedPocketProject.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedPocketProject.undo());
    }

    /**
     * Creates and returns a {@code VersionedPocketProject} with the {@code pocketProjectStates} added into it, and the
     * {@code VersionedPocketProject#currentStatePointer} at the end of list.
     */
    private VersionedPocketProject preparePocketProjectList(ReadOnlyPocketProject... pocketProjectStates) {
        assertFalse(pocketProjectStates.length == 0);

        VersionedPocketProject versionedPocketProject = new VersionedPocketProject(pocketProjectStates[0]);
        for (int i = 1; i < pocketProjectStates.length; i++) {
            versionedPocketProject.resetData(pocketProjectStates[i]);
            versionedPocketProject.commit();
        }

        return versionedPocketProject;
    }

    /**
     * Shifts the {@code versionedPocketProject#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedPocketProject versionedPocketProject, int count) {
        for (int i = 0; i < count; i++) {
            versionedPocketProject.undo();
        }
    }
}
