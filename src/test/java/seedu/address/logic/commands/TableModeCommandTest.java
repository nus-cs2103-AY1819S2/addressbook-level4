package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODE_CHANGE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TABLE_NUMBER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TableModeCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.table.TableNumber;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code TableModeCommand}.
 */
public class TableModeCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullTableNumber_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new TableModeCommand(null);
    }

    @Test
    public void execute_validTableNumber_success() {
        TableNumber tableNumber = new TableNumber("1");
        TableModeCommand tableModeCommand = new TableModeCommand(tableNumber);
        expectedModel.updateFilteredTableList(Model.PREDICATE_SHOW_ALL_TABLES);
        expectedModel.setSelectedTable(model.getRestOrRant().getTables().getTableFromNumber(tableNumber).get());
        expectedModel.updateFilteredOrderItemList(orderItem -> orderItem.getTableNumber().equals(tableNumber));
        CommandResult expectedResult =
                new CommandResult(String.format(MESSAGE_SUCCESS, tableNumber), false, false, Mode.TABLE_MODE);
        assertCommandSuccess(Mode.RESTAURANT_MODE, tableModeCommand, model, commandHistory, expectedResult,
                expectedModel);
    }

    @Test
    public void execute_invalidModeChange_failure() {
        TableModeCommand tableModeCommand = new TableModeCommand(new TableNumber("1"));
        model.setSelectedTable(model.getRestOrRant().getTables().getTableFromNumber(new TableNumber("1")).get());
        assertCommandFailure(Mode.TABLE_MODE, tableModeCommand, model, commandHistory, MESSAGE_INVALID_MODE_CHANGE);
    }

    @Test
    public void execute_invalidTableNumber_failure() {
        TableModeCommand tableModeCommand = new TableModeCommand(new TableNumber("10"));
        assertCommandFailure(Mode.RESTAURANT_MODE, tableModeCommand, model, commandHistory,
                MESSAGE_INVALID_TABLE_NUMBER);
    }

    @Test
    public void equals() {
        TableModeCommand firstCommand = new TableModeCommand(new TableNumber("1"));
        TableModeCommand secondCommand = new TableModeCommand(new TableNumber("2"));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        TableModeCommand firstCommandCopy = new TableModeCommand(new TableNumber("1"));
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different command -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }
}
