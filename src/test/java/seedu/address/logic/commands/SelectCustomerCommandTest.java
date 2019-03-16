package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCustomerAtIndex;
import static seedu.address.testutil.TypicalCustomers.getTypicalHotelManagementSystem;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CUSTOMER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CUSTOMER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.CustomerManager;
import seedu.address.model.CustomerModel;
import seedu.address.model.UserPrefs;
import seedu.address.model.VersionedHotelManagementSystem;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCustomerCommand}.
 */
public class SelectCustomerCommandTest {
    private CustomerModel model = new CustomerManager(
            new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private CustomerModel expectedModel = new CustomerManager(
            new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastCustomerIndex = Index.fromOneBased(model.getFilteredCustomerList().size());

        assertExecutionSuccess(INDEX_FIRST_CUSTOMER);
        assertExecutionSuccess(INDEX_THIRD_CUSTOMER);
        assertExecutionSuccess(lastCustomerIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);
        showCustomerAtIndex(expectedModel, INDEX_FIRST_CUSTOMER);

        assertExecutionSuccess(INDEX_FIRST_CUSTOMER);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);
        showCustomerAtIndex(expectedModel, INDEX_FIRST_CUSTOMER);

        Index outOfBoundsIndex = INDEX_SECOND_CUSTOMER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getHotelManagementSystem().getCustomerList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCustomerCommand selectFirstCommand = new SelectCustomerCommand(INDEX_FIRST_CUSTOMER);
        SelectCustomerCommand selectSecondCommand = new SelectCustomerCommand(INDEX_SECOND_CUSTOMER);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCustomerCommand selectFirstCommandCopy = new SelectCustomerCommand(INDEX_FIRST_CUSTOMER);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different customer -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCustomerCommand} with the given {@code index},
     * and checks that the model's selected customer is set to the customer at {@code index} in the filtered customer
     * list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCustomerCommand selectCustomerCommand = new SelectCustomerCommand(index);
        String expectedMessage = String.format(SelectCustomerCommand.MESSAGE_SELECT_CUSTOMER_SUCCESS,
                index.getOneBased());
        expectedModel.setSelectedCustomer(model.getFilteredCustomerList().get(index.getZeroBased()));

        assertCommandSuccess(selectCustomerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectCustomerCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCustomerCommand selectCustomerCommand = new SelectCustomerCommand(index);
        assertCommandFailure(selectCustomerCommand, model, commandHistory, expectedMessage);
    }
}
