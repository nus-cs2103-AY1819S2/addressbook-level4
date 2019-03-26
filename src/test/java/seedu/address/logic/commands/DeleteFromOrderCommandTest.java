package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteFromOrderCommand.MESSAGE_INVALID_ITEM_CODE;
import static seedu.address.logic.commands.DeleteFromOrderCommand.MESSAGE_INVALID_ORDER_ITEM;
import static seedu.address.logic.commands.DeleteFromOrderCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W09;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.menu.Code;
import seedu.address.model.table.TableNumber;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteFromOrderCommand}.
 */
public class DeleteFromOrderCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullItemCode_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteFromOrderCommand(null);
    }

    @Test
    public void execute_validItemCode_success() {
        Code itemCode = new Code("W09");
        DeleteFromOrderCommand command = new DeleteFromOrderCommand(itemCode);
        model.setSelectedTable(model.getRestOrRant().getTables().getTableFromNumber(new TableNumber("1")).get());
        expectedModel.setSelectedTable(
                model.getRestOrRant().getTables().getTableFromNumber(new TableNumber("1")).get());
        expectedModel.deleteOrderItem(TABLE1_W09);
        String expectedMessage = String.format(MESSAGE_SUCCESS, TABLE1_W09);
        assertCommandSuccess(Mode.TABLE_MODE, command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidItemCode_failure() {
        DeleteFromOrderCommand command = new DeleteFromOrderCommand(new Code("W13"));
        model.setSelectedTable(model.getRestOrRant().getTables().getTableFromNumber(new TableNumber("1")).get());
        assertCommandFailure(Mode.TABLE_MODE, command, model, commandHistory,
                String.format(MESSAGE_INVALID_ITEM_CODE, new Code("W13")));
    }

    @Test
    public void execute_invalidOrderItem_failure() {
        DeleteFromOrderCommand command = new DeleteFromOrderCommand(new Code("A05"));
        model.setSelectedTable(model.getRestOrRant().getTables().getTableFromNumber(new TableNumber("1")).get());
        assertCommandFailure(Mode.TABLE_MODE, command, model, commandHistory,
                String.format(MESSAGE_INVALID_ORDER_ITEM, new Code("A05")));
    }

    @Test
    public void equals() {
        DeleteFromOrderCommand firstCommand = new DeleteFromOrderCommand(new Code("W09"));
        DeleteFromOrderCommand secondCommand = new DeleteFromOrderCommand(new Code("W12"));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        DeleteFromOrderCommand firstCommandCopy = new DeleteFromOrderCommand(new Code("W09"));
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different command -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }
}
