package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalRestOrRant.TABLE6;

import org.junit.Test;

import seedu.address.logic.commands.SpaceForCommand;
import seedu.address.model.Model;
import seedu.address.model.table.Table;
import seedu.address.testutil.TableBuilder;

public class SpaceForCommandSystemTest extends RestOrRantSystemTest {

    public static final String VALID_TABLE_STATUS = "3/3";
    public static final String VALID_NUMBER_OF_CUSTOMERS = "3";
    public static final String INVALID_NUMBER_OF_CUSTOMERS = "0";
    public static final String INVALID_NUMBER_OF_CUSTOMERS_NO_AVAILABLE_TABLES = "100";

    @Test
    public void update() {

        /* ------------------------ Perform update operations on the shown unfiltered list -------------------------- */

        /* Case: update a table restaurant, command with leading spaces and trailing spaces -> updated */
        Table updateTo = new TableBuilder(TABLE6).withTableStatus(VALID_TABLE_STATUS).build();
        String command = "   " + SpaceForCommand.COMMAND_WORD + "  " + VALID_NUMBER_OF_CUSTOMERS;
        assertCommandSuccess(command, TABLE6, updateTo);

        /* ----------------------------------- Perform invalid update operations ------------------------------------ */

        /* Case: missing number of customers -> rejected */
        command = SpaceForCommand.COMMAND_WORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SpaceForCommand.MESSAGE_USAGE));

        /* Case: too many customers -> rejected */
        command = SpaceForCommand.COMMAND_WORD + " " + INVALID_NUMBER_OF_CUSTOMERS_NO_AVAILABLE_TABLES;
        assertCommandFailure(command,
                String.format(SpaceForCommand.MESSAGE_NO_AVAILABLE_TABLE,
                        INVALID_NUMBER_OF_CUSTOMERS_NO_AVAILABLE_TABLES));

        /* Case: invalid number of customers -> rejected */
        command = SpaceForCommand.COMMAND_WORD + " " + INVALID_NUMBER_OF_CUSTOMERS;
        assertCommandFailure(command, SpaceForCommand.MESSAGE_CONSTRAINT);

        /* Case: too many arguments -> rejected */
        command = SpaceForCommand.COMMAND_WORD + " " + VALID_NUMBER_OF_CUSTOMERS + " " + VALID_NUMBER_OF_CUSTOMERS;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SpaceForCommand.MESSAGE_USAGE));

    }

    /**
     * Executes the {@code SpaceForCommand} that updates {@code updateTo} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code SpaceForCommand} with the details of
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
        String expectedResultMessage = String.format(SpaceForCommand.MESSAGE_SUCCESS,
                updateTo.getTableStatus().getNumberOfTakenSeats(), updateTo.getTableNumber());

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Table)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code TableListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see SpaceForCommandSystemTest#assertCommandSuccess(String, Table, Table)
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
