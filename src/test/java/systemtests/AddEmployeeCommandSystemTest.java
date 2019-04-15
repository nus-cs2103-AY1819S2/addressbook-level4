package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GITHUB_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GITHUB_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GITHUB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SKILL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_C;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.AMY;
import static seedu.address.testutil.TypicalEmployees.BOB;
import static seedu.address.testutil.TypicalEmployees.CARL;
import static seedu.address.testutil.TypicalEmployees.HOON;
import static seedu.address.testutil.TypicalEmployees.IDA;
import static seedu.address.testutil.TypicalEmployees.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEmployeeCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.GitHubAccount;
import seedu.address.model.employee.Phone;
import seedu.address.model.skill.Skill;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.EmployeeUtil;

public class AddEmployeeCommandSystemTest extends PocketProjectSystemTest {

    @Test
    public void addEmployee() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a employee without skills to a non-empty pocket project, command with leading spaces and trailing
         * spaces
         * -> added
         */
        Employee toAdd = AMY;
        String command = " " + AddEmployeeCommand.COMMAND_WORD + " " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + " "
            + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY + "   " + GITHUB_DESC_AMY + "   "
            + SKILL_DESC_C + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addEmployee(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a employee with all fields same as another employee in the pocket project except name -> added */
        toAdd = new EmployeeBuilder(AMY).withName(VALID_NAME_BOB).build();
        command = AddEmployeeCommand.COMMAND_WORD + " " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB
            + PHONE_DESC_AMY + EMAIL_DESC_AMY + GITHUB_DESC_AMY + SKILL_DESC_C;
        assertCommandSuccess(command, toAdd);

        /* Case: add a employee with all fields same as another employee in the pocket project except phone, email
         * and github account.
         * -> added
         */
        toAdd = new EmployeeBuilder(AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withGitHubAccount(VALID_GITHUB_BOB).build();
        command = EmployeeUtil.getAddEmployeeCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty pocket project -> added */
        deleteAllEmployees();
        assertCommandSuccess(ALICE);

        /* Case: add a employee with mixed case command words
         * -> added
         */
        toAdd = AMY;
        command = "   " + "AdD" + "  " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + " "
                + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY + "   " + GITHUB_DESC_AMY + "   "
                + SKILL_DESC_C + " ";
        assertCommandSuccess(command, toAdd);
        deleteAllEmployees();

        /* Case: add a employee with mixed case key words
         * -> added
         */
        toAdd = AMY;
        command = "   " + AddEmployeeCommand.COMMAND_WORD + "  " + "EmPloyee" + " "
                + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY + "   " + GITHUB_DESC_AMY + "   "
                + SKILL_DESC_C + " ";
        assertCommandSuccess(command, toAdd);
        deleteAllEmployees();

        /* Case: add a employee with skills, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddEmployeeCommand.COMMAND_WORD + " " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + SKILL_DESC_C
            + PHONE_DESC_BOB + GITHUB_DESC_BOB + NAME_DESC_BOB + SKILL_DESC_JAVA + EMAIL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a employee, missing skills -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the employee list before adding -> added */
        showEmployeesWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a employee card is selected ------------------------- */

        /* Case: selects first card in the employee list, add a employee -> added, card selection remains unchanged */
        viewEmployee(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate employee -> rejected */
        command = EmployeeUtil.getAddEmployeeCommand(HOON);
        assertCommandFailure(command, Messages.MESSAGE_DUPLICATE_EMPLOYEE);

        /* Case: add a duplicate employee except with different phone -> rejected */
        toAdd = new EmployeeBuilder(HOON).withPhone(VALID_PHONE_BOB).build();
        command = EmployeeUtil.getAddEmployeeCommand(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_DUPLICATE_EMPLOYEE);

        /* Case: add a duplicate employee except with different email -> rejected */
        toAdd = new EmployeeBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
        command = EmployeeUtil.getAddEmployeeCommand(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_DUPLICATE_EMPLOYEE);

        /* Case: add a duplicate employee except with different github -> rejected */
        toAdd = new EmployeeBuilder(HOON).withGitHubAccount(VALID_GITHUB_BOB).build();
        command = EmployeeUtil.getAddEmployeeCommand(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_DUPLICATE_EMPLOYEE);

        /* Case: add a duplicate employee except with different skills -> rejected */
        command = EmployeeUtil.getAddEmployeeCommand(HOON) + " " + PREFIX_SKILL.getPrefix() + "friends";
        assertCommandFailure(command, Messages.MESSAGE_DUPLICATE_EMPLOYEE);

        /* Case: missing name -> rejected */
        command = AddEmployeeCommand.COMMAND_WORD + " " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + PHONE_DESC_AMY
            + EMAIL_DESC_AMY + GITHUB_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEmployeeCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddEmployeeCommand.COMMAND_WORD + " " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_AMY
            + EMAIL_DESC_AMY + GITHUB_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEmployeeCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddEmployeeCommand.COMMAND_WORD + " " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_AMY
            + PHONE_DESC_AMY + GITHUB_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEmployeeCommand.MESSAGE_USAGE));

        /* Case: missing github -> rejected */
        command = AddEmployeeCommand.COMMAND_WORD + " " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_AMY
            + PHONE_DESC_AMY + EMAIL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEmployeeCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + EmployeeUtil.getEmployeeDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddEmployeeCommand.COMMAND_WORD + " " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + INVALID_NAME_DESC
            + PHONE_DESC_AMY + EMAIL_DESC_AMY + GITHUB_DESC_AMY;
        assertCommandFailure(command, EmployeeName.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddEmployeeCommand.COMMAND_WORD + " " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_AMY
            + INVALID_PHONE_DESC + EMAIL_DESC_AMY + GITHUB_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddEmployeeCommand.COMMAND_WORD + " " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_AMY
            + PHONE_DESC_AMY + INVALID_EMAIL_DESC + GITHUB_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_CONSTRAINTS);

        /* Case: invalid github -> rejected */
        command = AddEmployeeCommand.COMMAND_WORD + " " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_AMY
            + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_GITHUB_DESC;
        assertCommandFailure(command, GitHubAccount.MESSAGE_CONSTRAINTS);

        /* Case: invalid skill -> rejected */
        command = AddEmployeeCommand.COMMAND_WORD + " " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_AMY
            + PHONE_DESC_AMY + EMAIL_DESC_AMY + GITHUB_DESC_AMY + INVALID_SKILL_DESC;
        assertCommandFailure(command, Skill.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddEmployeeCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddEmployeeCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code EmployeeListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Employee toAdd) {
        assertCommandSuccess(EmployeeUtil.getAddEmployeeCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Employee)}. Executes {@code command}
     * instead.
     * @see AddEmployeeCommandSystemTest#assertCommandSuccess(Employee)
     */
    private void assertCommandSuccess(String command, Employee toAdd) {
        Model expectedModel = getModel();
        expectedModel.addEmployee(toAdd);
        String expectedResultMessage = String.format(AddEmployeeCommand.MESSAGE_ADD_EMPLOYEE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Employee)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code EmployeeListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddEmployeeCommandSystemTest#assertCommandSuccess(String, Employee)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code EmployeeListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
