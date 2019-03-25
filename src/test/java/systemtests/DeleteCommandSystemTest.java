package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;
import static seedu.hms.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.hms.logic.commands.DeleteCustomerCommand.MESSAGE_DELETE_CUSTOMER_SUCCESS;
import static seedu.hms.testutil.TestUtil.getCustomer;
import static seedu.hms.testutil.TestUtil.getLastIndex;
import static seedu.hms.testutil.TestUtil.getMidIndex;
import static seedu.hms.testutil.TypicalCustomers.KEYWORD_MATCHING_MEIER;
import static seedu.hms.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;

import org.junit.Test;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.DeleteCustomerCommand;
import seedu.hms.logic.commands.RedoCommand;
import seedu.hms.logic.commands.UndoCommand;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.customer.Customer;

public class DeleteCommandSystemTest extends HotelManagementSystemSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
        String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCustomerCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first customer in the list, command with leading spaces and trailing spaces -> deleted */
        CustomerModel expectedModel = getModel();
        String command = "     " + DeleteCustomerCommand.COMMAND_WORD + "      "
            + INDEX_FIRST_CUSTOMER.getOneBased() + "       ";
        Customer deletedCustomer = removeCustomer(expectedModel, INDEX_FIRST_CUSTOMER);
        String expectedResultMessage = String.format(MESSAGE_DELETE_CUSTOMER_SUCCESS, deletedCustomer);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last customer in the list -> deleted */
        CustomerModel modelBeforeDeletingLast = getModel();
        Index lastCustomerIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastCustomerIndex);

        /* Case: undo deleting the last customer in the list -> last customer restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last customer in the list -> last customer deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeCustomer(modelBeforeDeletingLast, lastCustomerIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle customer in the list -> deleted */
        Index middleCustomerIndex = getMidIndex(getModel());
        assertCommandSuccess(middleCustomerIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered customer list, delete index within bounds of address book and customer list -> deleted */
        showCustomersWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_CUSTOMER;
        assertTrue(index.getZeroBased() < getModel().getFilteredCustomerList().size());
        assertCommandSuccess(index);

        /* Case: filtered customer list, delete index within bounds of address book but out of bounds of customer list
         * -> rejected
         */
        showCustomersWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getHotelManagementSystem().getCustomerList().size();
        command = DeleteCustomerCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a customer card is selected ---------------------- */

        /* Case: delete the selected customer -> customer list panel selects the customer before the deleted customer */
        showAllCustomers();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectCustomer(selectedIndex);
        command = DeleteCustomerCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedCustomer = removeCustomer(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_CUSTOMER_SUCCESS, deletedCustomer);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCustomerCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCustomerCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
            getModel().getHotelManagementSystem().getCustomerList().size() + 1);
        command = DeleteCustomerCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCustomerCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCustomerCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
    * Removes the {@code Customer} at the specified {@code index} in {@code model}'s address book.
    *
    * @return the removed customer
    */
    private Customer removeCustomer(CustomerModel model, Index index) {
        Customer targetCustomer = getCustomer(model, index);
        model.deleteCustomer(targetCustomer);
        return targetCustomer;
    }

    /**
     * Deletes the customer at {@code toDelete} by creating a default {@code DeleteCustomerCommand}
     using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     *
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, CustomerModel, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        CustomerModel expectedModel = getModel();
        Customer deletedCustomer = removeCustomer(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_CUSTOMER_SUCCESS, deletedCustomer);

        assertCommandSuccess(
            DeleteCustomerCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code HotelManagementSystemSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     *
     * @see HotelManagementSystemSystemTest#assertApplicationDisplaysExpected(String, String, CustomerModel)
     */
    private void assertCommandSuccess(String command, CustomerModel expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}
     *
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, CustomerModel, String)
     * @see HotelManagementSystemSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, CustomerModel expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            //Todo: ServiceTypeAndRoomTypeListPanel test
            //assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code HotelManagementSystemSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see HotelManagementSystemSystemTest#assertApplicationDisplaysExpected(String, String, CustomerModel)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        CustomerModel expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
