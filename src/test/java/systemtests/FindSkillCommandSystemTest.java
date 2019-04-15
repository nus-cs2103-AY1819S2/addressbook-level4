package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_EMPLOYEES_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalEmployees.DANIEL;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindSkillCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;


public class FindSkillCommandSystemTest extends PocketProjectSystemTest {

    @Test
    public void find() {
        /* Case: find multiple employees in Pocket Project, command with leading spaces and trailing spaces
         * -> 2 employees found
         */
        String command = "   " + FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD
            + " " + "java" + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where employee list is displaying the employees we are finding
         * -> 2 employees found
         */
        command = FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD + " "
            + "java";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find employee where employee list is not displaying the employee we are finding -> 1 employee found */
        command = FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD + " Assembly";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple employees in Pocket Project, 2 keywords -> 2 employees found */
        command = FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD + " CSS Assembly";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple employees in Pocket Project, 2 keywords in reversed order -> 2 employees found */
        command = FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD + " Assembly CSS";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple employees in Pocket Project, 2 keywords with 1 repeat -> 2 employees found */
        command = FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD
            + " CSS Assembly CSS";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple employees in Pocket Project, 2 matching keywords and 1 non-matching keyword
         * -> 2 employees found
         */
        command = FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD
            + " Css Assembly suppppp";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same employees in Pocket Project after deleting 1 of them -> 1 employee found */
        executeCommand(DeleteCommand.COMMAND_WORD + " employee 1");
        assertFalse(getModel().getPocketProject().getEmployeeList().contains(BENSON));
        command = FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD + " "
            + "CSS Assembly";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find employee in Pocket Project, keyword is same as name but of different case -> 1 employee found */
        command = FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD + " aSseMBly";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find employee in Pocket Project, command word is of mixed case -> 1 employee found */
        command = "FinD" + " " + FindSkillCommand.FIND_SKILL_KEYWORD + " Assembly";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find employee in Pocket Project, keyword is substring of name -> 0 employees found */
        command = FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD + " Asa";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find employee in Pocket Project, name is substring of keyword -> 0 employees found */
        command = FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD + " assemblyyyyyyyy";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find employee not in Pocket Project -> 0 employees found */
        command = FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a employee is selected -> selected card deselected */
        showAllEmployees();
        viewEmployee(Index.fromOneBased(1));
        assertFalse(getEmployeeListPanel().getHandleToSelectedCard().getName()
            .equals(DANIEL.getEmployeeName().fullName));
        command = FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD + " Assembly";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find employee in empty Pocket Project -> 0 employees found */
        deleteAllEmployees();
        command = FindSkillCommand.COMMAND_WORD + " " + FindSkillCommand.FIND_SKILL_KEYWORD + " "
            + "Assembly";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_EMPLOYEES_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
            MESSAGE_EMPLOYEES_LISTED_OVERVIEW, expectedModel.getFilteredEmployeeList().size());

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
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
