package seedu.hms.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.hms.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hms.logic.commands.CommandTestUtil.showCustomerAtIndex;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;
import static seedu.hms.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
import static seedu.hms.testutil.TypicalIndexes.INDEX_SECOND_CUSTOMER;

import org.junit.Test;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.CommandHistory;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.customer.Customer;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCustomerCommand}.
 */
public class DeleteCustomerCommandTest {

    private CustomerModel model =
        new CustomerManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Customer customerToDelete = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        DeleteCustomerCommand deleteCustomerCommand = new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER);

        String expectedMessage = String.format(DeleteCustomerCommand.MESSAGE_DELETE_CUSTOMER_SUCCESS, customerToDelete);

        CustomerManager expectedModel =
            new CustomerManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.deleteCustomer(customerToDelete);
        expectedModel.commitHotelManagementSystem();

        assertCommandSuccess(deleteCustomerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        DeleteCustomerCommand deleteCustomerCommand = new DeleteCustomerCommand(outOfBoundIndex);

        assertCommandFailure(deleteCustomerCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);

        Customer customerToDelete = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        DeleteCustomerCommand deleteCustomerCommand = new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER);

        String expectedMessage = String.format(DeleteCustomerCommand.MESSAGE_DELETE_CUSTOMER_SUCCESS, customerToDelete);

        CustomerModel expectedModel =
            new CustomerManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.deleteCustomer(customerToDelete);
        expectedModel.commitHotelManagementSystem();
        showNoCustomer(expectedModel);

        assertCommandSuccess(deleteCustomerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);

        Index outOfBoundIndex = INDEX_SECOND_CUSTOMER;
        // ensures that outOfBoundIndex is still in bounds of hms book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getHotelManagementSystem().getCustomerList().size());

        DeleteCustomerCommand deleteCustomerCommand = new DeleteCustomerCommand(outOfBoundIndex);

        assertCommandFailure(deleteCustomerCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Customer customerToDelete = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        DeleteCustomerCommand deleteCustomerCommand = new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER);
        CustomerModel expectedModel =
            new CustomerManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.deleteCustomer(customerToDelete);
        expectedModel.commitHotelManagementSystem();

        // delete -> first customer deleted
        deleteCustomerCommand.execute(model, commandHistory);

        // undo -> reverts hotelManagementSystem back to previous state and filtered customer list to show all customers
        expectedModel.undoHotelManagementSystem();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first customer deleted again
        expectedModel.redoHotelManagementSystem();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        DeleteCustomerCommand deleteCustomerCommand = new DeleteCustomerCommand(outOfBoundIndex);

        // execution failed -> hms book state not added into model
        assertCommandFailure(deleteCustomerCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);

        // single hms book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Customer} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted customer in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the customer object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameCustomerDeleted() throws Exception {
        DeleteCustomerCommand deleteCustomerCommand = new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER);
        CustomerModel expectedModel =
            new CustomerManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());

        showCustomerAtIndex(model, INDEX_SECOND_CUSTOMER);
        Customer customerToDelete = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        expectedModel.deleteCustomer(customerToDelete);
        expectedModel.commitHotelManagementSystem();

        // delete -> deletes second customer in unfiltered customer list / first customer in filtered customer list
        deleteCustomerCommand.execute(model, commandHistory);

        // undo -> reverts hotelManagementSystem back to previous state and filtered customer list to show all customers
        expectedModel.undoHotelManagementSystem();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(customerToDelete, model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased()));
        // redo -> deletes same second customer in unfiltered customer list
        expectedModel.redoHotelManagementSystem();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCustomerCommand deleteFirstCommand = new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER);
        DeleteCustomerCommand deleteSecondCommand = new DeleteCustomerCommand(INDEX_SECOND_CUSTOMER);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCustomerCommand deleteFirstCommandCopy = new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different customer -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoCustomer(CustomerModel model) {
        model.updateFilteredCustomerList(p -> false);

        assertTrue(model.getFilteredCustomerList().isEmpty());
    }
}
