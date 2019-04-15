package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_EMPLOYEES_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalEmployees.CARL;
import static seedu.address.testutil.TypicalEmployees.DANIEL;
import static seedu.address.testutil.TypicalEmployees.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindEmployeeCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class FindEmployeeCommandSystemTest extends PocketProjectSystemTest {

    @Test
    public void find() {
        /* Case: find multiple employees in pocket project, command with leading spaces and trailing spaces
         * -> 2 employees found
         */
        String command = "   " + FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD
            + " " + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where employee list is displaying the employees we are finding
         * -> 2 employees found
         */
        command = FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " "
            + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find employee where employee list is not displaying the employee we are finding -> 1 employee found */
        command = FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple employees in pocket project, 2 keywords -> 2 employees found */
        command = FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple employees in pocket project, 2 keywords in reversed order -> 2 employees found */
        command = FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple employees in pocket project, 2 keywords with 1 repeat -> 2 employees found */
        command = FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD
            + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple employees in pocket project, 2 matching keywords and 1 non-matching keyword
         * -> 2 employees found
         */
        command = FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD
            + " Daniel Benson NonMatchingKeyWord";
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

        /* Case: find same employees in pocket project after deleting 1 of them -> 1 employee found */
        executeCommand(DeleteCommand.COMMAND_WORD + " employee 1");
        assertFalse(getModel().getPocketProject().getEmployeeList().contains(BENSON));
        command = FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " "
            + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find employee in pocket project, keyword is same as name but of different case -> 1 employee found */
        command = FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find employee in pocket project, command word is of mixed case -> 1 employee found */
        command = "FinD" + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " Meier";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find employee in pocket project, keyword is substring of name -> 0 employees found */
        command = FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find employee in pocket project, name is substring of keyword -> 0 employees found */
        command = FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find employee not in pocket project -> 0 employees found */
        command = FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a employee is selected -> selected card deselected */
        showAllEmployees();
        viewEmployee(Index.fromOneBased(1));
        assertFalse(getEmployeeListPanel().getHandleToSelectedCard().getName()
            .equals(DANIEL.getEmployeeName().fullName));
        command = FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find employee in empty pocket project -> 0 employees found */
        deleteAllEmployees();
        command = FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " "
            + KEYWORD_MATCHING_MEIER;
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
