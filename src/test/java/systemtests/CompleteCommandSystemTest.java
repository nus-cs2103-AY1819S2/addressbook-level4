package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CompleteCommand.MESSAGE_COMPLETE_PROJECT_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastProjectIndex;
import static seedu.address.testutil.TestUtil.getMidProjectIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CompleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.util.PocketProjectDate;

public class CompleteCommandSystemTest extends PocketProjectSystemTest {

    private static final String MESSAGE_INVALID_COMPLETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE);
    private static final PocketProjectDate VALID_DATE = new PocketProjectDate("11/11/2011");

    @Test
    public void complete() {
        /* --------------- Performing complete operation while an unfiltered list is being shown -------------------- */

        /* Case: complete the first project in the list, command with leading spaces and trailing spaces -> completed */
        Model expectedModel = getModel();
        String command = "     " + CompleteCommand.COMMAND_WORD + "      "
                + INDEX_FIRST_PROJECT.getOneBased() + " 11/11/2011      ";
        Project completedProject = completeProject(expectedModel, INDEX_FIRST_PROJECT);
        String expectedResultMessage = String.format(MESSAGE_COMPLETE_PROJECT_SUCCESS, completedProject);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: complete the last project in the list -> completed */
        Model modelBeforeCompletingLast = getModel();
        Index lastProjectIndex = getLastProjectIndex(modelBeforeCompletingLast);
        assertCommandSuccess(lastProjectIndex);

        /* Case: undo deleting the last project in the list -> last project restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeCompletingLast, expectedResultMessage);

        /* Case: redo deleting the last project in the list -> last project completed again */
        command = RedoCommand.COMMAND_WORD;
        completeProject(modelBeforeCompletingLast, lastProjectIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeCompletingLast, expectedResultMessage);

        /* Case: complete the middle project in the list -> completed */
        Index middleProjectIndex = getMidProjectIndex(getModel());
        assertCommandSuccess(middleProjectIndex);

        /* ------------------ Performing complete operation while a filtered list is being shown ------------------- */
        /* Case: filtered project list, complete index within bounds of pocket project and project list -> completed */
        showProjectsWithName("Fiona");
        Index index = INDEX_FIRST_PROJECT;
        assertTrue(index.getZeroBased() < getModel().getFilteredProjectList().size());
        assertCommandSuccess(index);

        /* Case: filtered project list, complete index within bounds of pocket project but out of bounds of project list
         * -> rejected
         */
        showProjectsWithName("HAHA");
        int invalidIndex = getModel().getPocketProject().getProjectList().size();
        command = CompleteCommand.COMMAND_WORD + " " + invalidIndex + " 11/11/2011";
        assertCommandFailure(command, MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);


        /* --------------------------------- Performing invalid complete operation --------------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = CompleteCommand.COMMAND_WORD + "  0" + " 11/11/2011";
        assertCommandFailure(command, MESSAGE_INVALID_COMPLETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = CompleteCommand.COMMAND_WORD + "  -1" + " 11/11/2011";
        assertCommandFailure(command, MESSAGE_INVALID_COMPLETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getPocketProject().getProjectList().size() + 1);
        command = CompleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased() + " 11/11/2011";
        assertCommandFailure(command, MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);

    }

    /**
     * Completes the {@code Project} at the specified {@code index} in {@code model}'s pocket project.
     * @return the removed project
     */
    private Project completeProject(Model model, Index index) {
        Project targetProject = model.getFilteredProjectList().get(index.getZeroBased());
        model.completeProject(targetProject, VALID_DATE);
        return targetProject;
    }

    /**
     * Completes the project at {@code toComplete} by
     * creating a default {@code CompleteCommand} using {@code toComplete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see CompleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toComplete) {
        Model expectedModel = getModel();
        Project completedProject = completeProject(expectedModel, toComplete);
        String expectedResultMessage = String.format(MESSAGE_COMPLETE_PROJECT_SUCCESS, completedProject);

        assertCommandSuccess(
                CompleteCommand.COMMAND_WORD + " " + toComplete.getOneBased() + " 11/11/2011",
                expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see CompleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see PocketProjectSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedProjectCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedProjectCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
