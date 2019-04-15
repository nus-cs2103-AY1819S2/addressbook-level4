package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PROJECTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.DeleteProjectCommand.DELETE_PROJECT_KEYWORD;
import static seedu.address.testutil.TypicalProjects.PROJECT_ALICE;
import static seedu.address.testutil.TypicalProjects.PROJECT_BENSON;
import static seedu.address.testutil.TypicalProjects.PROJECT_CARL;
import static seedu.address.testutil.TypicalProjects.PROJECT_DANIEL;

import org.junit.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindProjectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListProjectCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;


public class FindProjectCommandSystemTest extends PocketProjectSystemTest {

    @Test
    public void find() {
        /* Case: find multiple projects in Pocket Project, command with leading spaces and trailing spaces
         * -> 2 projects found
         */
        String command = "   " + FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD
            + " " + "hey" + "   ";
        Model expectedModel = getModel();
        ModelHelper.setProjectFilteredList(expectedModel, PROJECT_ALICE, PROJECT_CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();

        /* Case: repeat previous find command where project list is displaying the projects we are finding
         * -> 2 projects found
         */
        command = FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD + " "
            + "hey";
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();

        /* Case: find project where project list is not displaying the project we are finding -> 1 project found */
        command = FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD + " Carl";
        ModelHelper.setProjectFilteredList(expectedModel, PROJECT_CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();

        /* Case: find multiple projects in Pocket Project, 2 keywords -> 2 projects found */
        command = FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD + " Benson Daniel";
        ModelHelper.setProjectFilteredList(expectedModel, PROJECT_BENSON, PROJECT_DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();

        /* Case: find multiple projects in Pocket Project, 2 keywords in reversed order -> 2 projects found */
        command = FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();

        /* Case: find multiple projects in Pocket Project, 2 keywords with 1 repeat -> 2 projects found */
        command = FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD
            + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();

        /* Case: find multiple projects in Pocket Project, 2 matching keywords and 1 non-matching keyword
         * -> 2 projects found
         */
        command = FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD
            + " Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same projects in Pocket Project after deleting 1 of them -> 1 project found */
        executeCommand(ListCommand.COMMAND_WORD + " " + ListProjectCommand.LIST_PROJECT_KEYWORD);
        executeCommand(DeleteCommand.COMMAND_WORD + " " + DELETE_PROJECT_KEYWORD + " 3");
        assertFalse(getModel().getPocketProject().getProjectList().contains(PROJECT_CARL));
        command = FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD + " "
            + "hey";
        expectedModel = getModel();
        ModelHelper.setProjectFilteredList(expectedModel, PROJECT_ALICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();

        /* Case: find project in Pocket Project, keyword is same as name but of different case -> 1 project found */
        command = FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD + " HeY";
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();

        /* Case: find project in Pocket Project, command word is of mixed case -> 1 project found */
        command = "FinD" + " " + FindProjectCommand.FIND_PROJECT_KEYWORD + " Hey";
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();

        /* Case: find project in Pocket Project, keyword is substring of name -> 0 projects found */
        command = FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD + " he";
        ModelHelper.setProjectFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();

        /* Case: find project in Pocket Project, name is substring of keyword -> 0 projects found */
        command = FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD + " heeeeeeey";
        ModelHelper.setProjectFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();

        /* Case: find project not in Pocket Project -> 0 projects found */
        command = FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();

        /* Case: find employee in empty Pocket Project -> 0 projects found */
        deleteAllProjects();
        command = FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD + " "
            + "hey";
        expectedModel = getModel();
        ModelHelper.setProjectFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedProjectCardUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_projects_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
            MESSAGE_PROJECTS_LISTED_OVERVIEW, expectedModel.getFilteredProjectList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedProjectCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
