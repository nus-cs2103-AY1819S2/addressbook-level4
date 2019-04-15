package seedu.hms.logic.commands;

import static seedu.hms.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.hms.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hms.logic.commands.CommandTestUtil.deleteFirstCustomer;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import org.junit.Before;
import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;

public class UndoCommandTest {

    private final CustomerModel model = new CustomerManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private final CustomerModel expectedModel = new CustomerManager(
        new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstCustomer(model);
        deleteFirstCustomer(model);

        deleteFirstCustomer(expectedModel);
        deleteFirstCustomer(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoHotelManagementSystem();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoHotelManagementSystem();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
