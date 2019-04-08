package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRestOrRant.TABLE2;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;
import seedu.address.testutil.TableBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditSeatsCommand
 */
public class EditSeatsCommandTest {

    public static final String CURRENT_TABLE_STATUS = "4/5";
    public static final String VALID_TABLE_STATUS = "4/6";
    public static final String INVALID_NUMBER_OF_SEATS_LESS_THAN_CUSTOMERS = "3";
    public static final String INVALID_NUMBER_OF_SEATS_SAME_AS_CURRENT = "5";
    public static final String INVALID_TABLE_NUMBER = "9";

    private Model model = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_tableUpdatedByModel_updateSuccessful() {
        Table editedTable = new TableBuilder(TABLE2).withTableStatus(VALID_TABLE_STATUS).build();
        EditSeatsCommand editSeatsCommand = new EditSeatsCommand(editedTable.getTableNumber(),
                editedTable.getTableStatus().getNumberOfSeats());
        Model expectedModel = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
        expectedModel.setTable(TABLE2, editedTable);
        assertCommandSuccess(Mode.RESTAURANT_MODE, editSeatsCommand, model, commandHistory,
                new CommandResult(String.format(EditSeatsCommand.MESSAGE_SUCCESS, editedTable.getTableNumber(),
                        editedTable.getTableStatus())), expectedModel);
    }

    @Test
    public void execute_invalidTableNumber_updateFailure() {
        EditSeatsCommand editSeatsCommand =
                new EditSeatsCommand(new TableNumber(INVALID_TABLE_NUMBER), VALID_TABLE_STATUS);
        assertCommandFailure(Mode.RESTAURANT_MODE, editSeatsCommand, model, commandHistory,
                String.format(EditSeatsCommand.INVALID_TABLE_NUMBER, INVALID_TABLE_NUMBER));
    }

    @Test
    public void execute_invalidNumberOfSeatsLessThanNumberOfCustomers_updateFailure() {
        EditSeatsCommand editSeatsCommand =
                new EditSeatsCommand(TABLE2.getTableNumber(), INVALID_NUMBER_OF_SEATS_LESS_THAN_CUSTOMERS);
        assertCommandFailure(Mode.RESTAURANT_MODE, editSeatsCommand, model, commandHistory,
                TableStatus.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void execute_invalidNumberOfSeatsSameAsCurrent_updateFailure() {
        EditSeatsCommand editSeatsCommand =
                new EditSeatsCommand(TABLE2.getTableNumber(), INVALID_NUMBER_OF_SEATS_SAME_AS_CURRENT);
        assertCommandFailure(Mode.RESTAURANT_MODE, editSeatsCommand, model, commandHistory,
                String.format(EditPaxCommand.MESSAGE_NO_CHANGE_IN_STATUS, TABLE2.getTableNumber().toString(),
                        CURRENT_TABLE_STATUS));
    }

    @Test
    public void equals() {
        final EditSeatsCommand standardCommand = new EditSeatsCommand(TABLE2.getTableNumber(), VALID_TABLE_STATUS);

        // same values -> returns true
        EditSeatsCommand editedEditaSeatsCommand = new EditSeatsCommand(TABLE2.getTableNumber(), VALID_TABLE_STATUS);
        assertTrue(standardCommand.equals(editedEditaSeatsCommand));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> return false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different table number -> return false
        assertFalse(standardCommand.equals(new EditSeatsCommand(new TableNumber("1"), VALID_TABLE_STATUS)));

        // different number of seats -> return false
        assertFalse(standardCommand.equals(new EditSeatsCommand(TABLE2.getTableNumber(),
                INVALID_NUMBER_OF_SEATS_SAME_AS_CURRENT)));
    }
}
