package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1;

import org.junit.Test;

import seedu.address.logic.commands.EditPaxCommand;
import seedu.address.model.Model;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableStatus;
import seedu.address.testutil.TableBuilder;

public class EditPaxCommandSystemTest extends RestOrRantSystemTest {

    public static final String VALID_TABLE_NUMBER = "1";
    public static final String VALID_TABLE_STATUS = "1/4";
    public static final String VALID_TABLE_STATUS_SAME_AS_TABLE4 = "3/4";
    public static final String INVALID_TABLE_NUMBER = "!@#";
    public static final String INVALID_TABLE_NUMBER_TOO_LARGE = "100";
    public static final String INVALID_TABLE_STATUS_NO_CHANGE = "3";
    public static final String INVALID_TABLE_STATUS_TOO_LARGE = "5";
    public static final String INVALID_TABLE_STATUS = "@2";

    @Test
    public void update() {

        /* ------------------------ Perform update operations on the shown unfiltered list -------------------------- */

        /* Case: update a table restaurant, command with leading spaces and trailing spaces -> updated */
        Table updateTo = new TableBuilder().withTableStatus(VALID_TABLE_STATUS).build();
        String command = "   " + EditPaxCommand.COMMAND_WORD + "  " + VALID_TABLE_NUMBER + " "
                + updateTo.getTableStatus().getNumberOfTakenSeats();
        assertCommandSuccess(command, new TableBuilder(TABLE1).build(), updateTo);

        /* Case: update a table same table status as another table in the restaurant -> updated */
        updateTo = new TableBuilder().withTableStatus(VALID_TABLE_STATUS_SAME_AS_TABLE4).build();
        command = EditPaxCommand.COMMAND_WORD + " " + VALID_TABLE_NUMBER + " "
                + updateTo.getTableStatus().getNumberOfTakenSeats();
        assertCommandSuccess(command, new TableBuilder().withTableStatus("1/4").build(), updateTo);

        /* ----------------------------------- Perform invalid update operations ------------------------------------ */

        /* Case: invalid table number -> rejected */
        command = EditPaxCommand.COMMAND_WORD + " " + INVALID_TABLE_NUMBER + " 3";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPaxCommand.MESSAGE_USAGE));

        /* Case: table number does not exist -> rejected */
        command = EditPaxCommand.COMMAND_WORD + " " + INVALID_TABLE_NUMBER_TOO_LARGE + " 3";
        assertCommandFailure(command, String.format(EditPaxCommand.MESSAGE_INVALID_TABLE_NUMBER,
                INVALID_TABLE_NUMBER_TOO_LARGE));

        /* Case: missing status -> rejected */
        command = EditPaxCommand.COMMAND_WORD + " " + VALID_TABLE_NUMBER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPaxCommand.MESSAGE_USAGE));

        /* Case: invalid table status -> rejected */
        command = EditPaxCommand.COMMAND_WORD + " " + VALID_TABLE_NUMBER + " " + INVALID_TABLE_STATUS;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPaxCommand.MESSAGE_USAGE));

        /* Case: table status larger than seats available -> rejected */
        command = EditPaxCommand.COMMAND_WORD + " " + VALID_TABLE_NUMBER + " " + INVALID_TABLE_STATUS_TOO_LARGE;
        assertCommandFailure(command, String.format(TableStatus.MESSAGE_INVALID_NUMBER_OF_CUSTOMERS,
                TABLE1.getTableStatus().getNumberOfTakenSeats()));

        /* Case: same table status as current table status of target table -> rejected */
        command = EditPaxCommand.COMMAND_WORD + " " + VALID_TABLE_NUMBER + " " + INVALID_TABLE_STATUS_NO_CHANGE;
        assertCommandFailure(command, String.format(EditPaxCommand.MESSAGE_NO_CHANGE_IN_STATUS,
                TABLE1.getTableNumber(), VALID_TABLE_STATUS_SAME_AS_TABLE4));

    }

    /**
     * Executes the {@code EditPaxCommand} that updates {@code updateTo} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code EditPaxCommand} with the details of
     * {@code updateTo}.<br>
     * 4. {@code Storage} and {@code TableListPanel} equal to the corresponding components in
     * the current model updated with {@code updateTo}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Table target, Table updateTo) {
        Model expectedModel = getModel();
        expectedModel.setTable(target, updateTo);
        String expectedResultMessage = String.format(EditPaxCommand.MESSAGE_SUCCESS, updateTo.getTableNumber(),
                updateTo.getTableStatus());

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Table)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code TableListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see EditPaxCommandSystemTest#assertCommandSuccess(String, Table, Table)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code TableListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
