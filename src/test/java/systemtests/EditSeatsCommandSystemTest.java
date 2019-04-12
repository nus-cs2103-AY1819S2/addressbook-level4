package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalRestOrRant.TABLE2;

import org.junit.Test;

import seedu.address.logic.commands.EditPaxCommand;
import seedu.address.logic.commands.EditSeatsCommand;
import seedu.address.model.Model;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;
import seedu.address.testutil.TableBuilder;

public class EditSeatsCommandSystemTest extends RestOrRantSystemTest {

    public static final String VALID_TABLE_NUMBER = "2";
    public static final String VALID_TABLE_STATUS = "4/7";
    public static final String INVALID_TABLE_NUMBER = "!@#";
    public static final String INVALID_TABLE_NUMBER_TOO_LARGE = "100";
    public static final String INVALID_TABLE_STATUS_NO_CHANGE = "7";
    public static final String INVALID_TABLE_STATUS_TOO_SMALL = "2";
    public static final String INVALID_TABLE_STATUS = "@2";

    @Test
    public void update() {

        /* ------------------------ Perform update operations on the shown unfiltered list -------------------------- */

        /* Case: update a table restaurant, command with leading spaces and trailing spaces -> updated */
        Table updateTo = new TableBuilder(TABLE2).withTableStatus(VALID_TABLE_STATUS).build();
        String command = "   " + EditSeatsCommand.COMMAND_WORD + "  " + VALID_TABLE_NUMBER + " "
                + updateTo.getTableStatus().getNumberOfSeats();
        assertCommandSuccess(command, TABLE2, updateTo);

        /* ----------------------------------- Perform invalid update operations ------------------------------------ */

        /* Case: invalid table number -> rejected */
        command = EditSeatsCommand.COMMAND_WORD + " " + INVALID_TABLE_NUMBER + " 3";
        assertCommandFailure(command, TableNumber.MESSAGE_CONSTRAINTS);

        /* Case: table number does not exist -> rejected */
        command = EditSeatsCommand.COMMAND_WORD + " " + INVALID_TABLE_NUMBER_TOO_LARGE + " 3";
        assertCommandFailure(command, String.format(EditSeatsCommand.INVALID_TABLE_NUMBER,
                INVALID_TABLE_NUMBER_TOO_LARGE));

        /* Case: missing status -> rejected */
        command = EditSeatsCommand.COMMAND_WORD + " " + VALID_TABLE_NUMBER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSeatsCommand.MESSAGE_USAGE));

        /* Case: invalid table status -> rejected */
        command = EditSeatsCommand.COMMAND_WORD + " " + VALID_TABLE_NUMBER + " " + INVALID_TABLE_STATUS;
        assertCommandFailure(command, TableStatus.MESSAGE_CONSTRAINTS);

        /* Case: number of seats less than number of customers -> rejected */
        command = EditSeatsCommand.COMMAND_WORD + " " + VALID_TABLE_NUMBER + " " + INVALID_TABLE_STATUS_TOO_SMALL;
        assertCommandFailure(command, String.format(TableStatus.MESSAGE_CONSTRAINTS));

        /* Case: same table status as current table status of target table -> rejected */
        command = EditSeatsCommand.COMMAND_WORD + " " + VALID_TABLE_NUMBER + " " + INVALID_TABLE_STATUS_NO_CHANGE;
        assertCommandFailure(command, String.format(EditPaxCommand.MESSAGE_NO_CHANGE_IN_STATUS,
                TABLE2.getTableNumber(), VALID_TABLE_STATUS));

    }

    /**
     * Executes the {@code EditSeatsCommand} that updates {@code updateTo} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code EditSeatsCommand} with the details of
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
        String expectedResultMessage = String.format(EditSeatsCommand.MESSAGE_SUCCESS, updateTo.getTableNumber(),
                updateTo.getTableStatus());

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Table)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code TableListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see EditSeatsCommandSystemTest#assertCommandSuccess(String, Table, Table)
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
