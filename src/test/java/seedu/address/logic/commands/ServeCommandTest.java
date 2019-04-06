package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ServeCommand.MESSAGE_FAILURE;
import static seedu.address.logic.commands.ServeCommand.MESSAGE_INVALID_ITEM_CODE;
import static seedu.address.logic.commands.ServeCommand.MESSAGE_INVALID_ORDER_ITEM;
import static seedu.address.logic.commands.ServeCommand.MESSAGE_SUCCESS;
import static seedu.address.model.order.OrderItemStatus.MESSAGE_OVER_SERVING;
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
import seedu.address.model.order.OrderItem;
import seedu.address.model.table.TableNumber;
import seedu.address.testutil.OrderItemBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ServeCommand}.
 */
public class ServeCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullItemCode_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ServeCommand(null, 5);
    }

    @Test
    public void constructor_nullItemQuantity_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ServeCommand(new Code("W09"), null);
    }

    @Test
    public void execute_validValue_success() {
        Code itemCode = new Code("W09");
        ServeCommand command = new ServeCommand(itemCode, 1);
        OrderItem result = new OrderItemBuilder().withQuantity(2, 1).build();
        model.setSelectedTable(model.getRestOrRant().getTables().getTableFromNumber(new TableNumber("1")).get());
        expectedModel.setSelectedTable(
                model.getRestOrRant().getTables().getTableFromNumber(new TableNumber("1")).get());
        expectedModel.setOrderItem(TABLE1_W09, result);
        String expectedMessage = String.format(MESSAGE_SUCCESS, result);
        assertCommandSuccess(Mode.TABLE_MODE, command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidItemCode_failure() {
        ServeCommand command = new ServeCommand(new Code("W13"), 3);
        model.setSelectedTable(model.getRestOrRant().getTables().getTableFromNumber(new TableNumber("1")).get());
        assertCommandFailure(Mode.TABLE_MODE, command, model, commandHistory,
                String.format(MESSAGE_FAILURE, new Code("W13"), MESSAGE_INVALID_ITEM_CODE));
    }

    @Test
    public void execute_invalidOrderItem_failure() {
        ServeCommand command = new ServeCommand(new Code("A05"), 4);
        model.setSelectedTable(model.getRestOrRant().getTables().getTableFromNumber(new TableNumber("1")).get());
        assertCommandFailure(Mode.TABLE_MODE, command, model, commandHistory,
                String.format(MESSAGE_FAILURE, new Code("A05"), MESSAGE_INVALID_ORDER_ITEM));
    }

    @Test
    public void execute_invalidQuantity_failure() {
        ServeCommand command = new ServeCommand(new Code("W09"), 3);
        model.setSelectedTable(model.getRestOrRant().getTables().getTableFromNumber(new TableNumber("1")).get());
        assertCommandFailure(Mode.TABLE_MODE, command, model, commandHistory,
                String.format(MESSAGE_FAILURE, new Code("W09"), String.format(MESSAGE_OVER_SERVING, 3, 2)));
    }

    @Test
    public void equals() {
        ServeCommand firstCommand = new ServeCommand(new Code("W09"), 3);
        ServeCommand secondCommand = new ServeCommand(new Code("W12"), 5);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        ServeCommand firstCommandCopy = new ServeCommand(new Code("W09"), 3);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different command -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }
}
