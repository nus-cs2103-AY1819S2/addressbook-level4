package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddTableCommand;
import seedu.address.model.Model;
import seedu.address.model.table.Table;
import seedu.address.testutil.TableBuilder;
import seedu.address.testutil.RestOrRantUtil;

public class AddTableCommandSystemTest extends RestOrRantSystemTest {

    public static final String INVALID_TABLE_STATUS = "@";
    public static final String VALID_EMPTY_TABLE_STATUS = "0/4";
    public static final String VALID_TABLE_STATUS_DESC = " 4";

    @Test
    public void add() {
        Model model = getModel();

      /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a table to a non-empty restaurant, command with leading spaces and trailing spaces -> added */
        Table toAdd = new TableBuilder().withTableNumber("9").withTableStatus(VALID_EMPTY_TABLE_STATUS).build();
        String command = "   " + AddTableCommand.COMMAND_WORD + VALID_TABLE_STATUS_DESC;
        assertCommandSuccess(command, toAdd);

        /* Case: add a table with same table status as another table but different table number -> added */
        toAdd = new TableBuilder().withTableNumber("10").withTableStatus(VALID_EMPTY_TABLE_STATUS).build();
        command = AddTableCommand.COMMAND_WORD + VALID_TABLE_STATUS_DESC;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty restaurant -> added */
        deleteAllTables();
        assertCommandSuccess(new TableBuilder(TABLE1).withTableStatus(VALID_EMPTY_TABLE_STATUS).build());

      /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: missing table status -> rejected */
        command = AddTableCommand.COMMAND_WORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTableCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + RestOrRantUtil.getTableDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid table status -> rejected */
        command = AddTableCommand.COMMAND_WORD + " " + INVALID_TABLE_STATUS;
        assertCommandFailure(command,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTableCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the {@code AddTableCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddTableCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code TableListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Table toAdd) {
        assertCommandSuccess(RestOrRantUtil.getAddTableCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Table)}. Executes {@code command}
     * instead.
     * @see AddTableCommandSystemTest#assertCommandSuccess(Table)
     */
    private void assertCommandSuccess(String command, Table toAdd) {
        Model expectedModel = getModel();
        expectedModel.addTable(toAdd);
        String expectedResultMessage = AddTableCommand.MESSAGE_SUCCESS
                + String.format(AddTableCommand.MESSAGE_TABLE_ADDED, toAdd.getTableNumber(), toAdd.getTableStatus());

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Table)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code TableListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddTableCommandSystemTest#assertCommandSuccess(String, Table)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
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
