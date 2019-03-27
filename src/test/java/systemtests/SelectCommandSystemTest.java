package systemtests;

//import static org.junit.Assert.assertTrue;
//import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;
//import static seedu.hms.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
//import static seedu.hms.logic.commands.SelectCustomerCommand.MESSAGE_SELECT_CUSTOMER_SUCCESS;
//import static seedu.hms.testutil.TestUtil.getLastIndex;
//import static seedu.hms.testutil.TestUtil.getMidIndex;
//import static seedu.hms.testutil.TypicalCustomers.KEYWORD_MATCHING_MEIER;
//import static seedu.hms.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
//
//import org.junit.Test;
//
//import seedu.hms.commons.core.index.Index;
//import seedu.hms.logic.commands.RedoCommand;
//import seedu.hms.logic.commands.SelectCustomerCommand;
//import seedu.hms.logic.commands.UndoCommand;
//import seedu.hms.model.CustomerModel;

public class SelectCommandSystemTest extends HotelManagementSystemSystemTest {
//    @Test
//    public void select() {
//        /* ------------------------ Perform select operations on the shown unfiltered list
//        -------------------------- */
//
//        /* Case: select the first card in the customer list, command with leading spaces and trailing spaces
//         * -> selected
//         */
//        String command = "   " + SelectCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
//                + "   ";
//        assertCommandSuccess(command, INDEX_FIRST_CUSTOMER);
//
//        /* Case: select the last card in the customer list -> selected */
//        Index customerCount = getLastIndex(getModel());
//        command = SelectCustomerCommand.COMMAND_WORD + " " + customerCount.getOneBased();
//        assertCommandSuccess(command, customerCount);
//
//        /* Case: undo previous selection -> rejected */
//        command = UndoCommand.COMMAND_WORD;
//        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
//        assertCommandFailure(command, expectedResultMessage);
//
//        /* Case: redo selecting last card in the list -> rejected */
//        command = RedoCommand.COMMAND_WORD;
//        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
//        assertCommandFailure(command, expectedResultMessage);
//
//        /* Case: select the middle card in the customer list -> selected */
//        Index middleIndex = getMidIndex(getModel());
//        command = SelectCustomerCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
//        assertCommandSuccess(command, middleIndex);
//
//        /* Case: select the current selected card -> selected */
//        assertCommandSuccess(command, middleIndex);
//
//        /* ------------------------ Perform select operations on the shown filtered list
//        ---------------------------- */
//
//        /* Case: filtered customer list, select index within bounds of address book but out of bounds of
//        customer list
//         * -> rejected
//         */
//        showCustomersWithName(KEYWORD_MATCHING_MEIER);
//        int invalidIndex = getModel().getHotelManagementSystem().getCustomerList().size();
//        assertCommandFailure(SelectCustomerCommand.COMMAND_WORD + " " + invalidIndex,
//                MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
//
//        /* Case: filtered customer list, select index within bounds of address book and customer list ->
//        selected */
//        Index validIndex = Index.fromOneBased(1);
//        assertTrue(validIndex.getZeroBased() < getModel().getFilteredCustomerList().size());
//        command = SelectCustomerCommand.COMMAND_WORD + " " + validIndex.getOneBased();
//        assertCommandSuccess(command, validIndex);
//
//        /* ----------------------------------- Perform invalid select operations
//        ------------------------------------ */
//
//        /* Case: invalid index (0) -> rejected */
//        assertCommandFailure(SelectCustomerCommand.COMMAND_WORD + " " + 0,
//            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCustomerCommand.MESSAGE_USAGE));
//
//        /* Case: invalid index (-1) -> rejected */
//        assertCommandFailure(SelectCustomerCommand.COMMAND_WORD + " " + -1,
//            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCustomerCommand.MESSAGE_USAGE));
//
//        /* Case: invalid index (size + 1) -> rejected */
//        invalidIndex = getModel().getFilteredCustomerList().size() + 1;
//        assertCommandFailure(SelectCustomerCommand.COMMAND_WORD + " " + invalidIndex,
//                MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
//
//        /* Case: invalid arguments (alphabets) -> rejected */
//        assertCommandFailure(SelectCustomerCommand.COMMAND_WORD + " abc",
//            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCustomerCommand.MESSAGE_USAGE));
//
//        /* Case: invalid arguments (extra argument) -> rejected */
//        assertCommandFailure(SelectCustomerCommand.COMMAND_WORD + " 1 abc",
//            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCustomerCommand.MESSAGE_USAGE));
//
//        /* Case: mixed case command word -> rejected */
//        assertCommandFailure("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);
//
//        /* Case: select from empty address book -> rejected */
//        deleteAllCustomers();
//        assertCommandFailure(SelectCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased(),
//            MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
//    }
//
//    /**
//     * Executes {@code command} and asserts that the,<br>
//     * 1. Command box displays an empty string.<br>
//     * 2. Command box has the default style class.<br>
//     * 3. Result display box displays the success message of executing select command with the
//     * {@code expectedSelectedCardIndex} of the selected customer.<br>
//     * 4. {@code Storage} and {@code CustomerListPanel} remain unchanged.<br>
//     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
//     * 6. Status bar remains unchanged.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code HotelManagementSystemSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     *
//     * @see HotelManagementSystemSystemTest#assertApplicationDisplaysExpected(String, String, CustomerModel)
//     * @see HotelManagementSystemSystemTest#assertSelectedCardChanged(Index)
//     */
//    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
//        CustomerModel expectedModel = getModel();
//        String expectedResultMessage = String.format(
//            MESSAGE_SELECT_CUSTOMER_SUCCESS, expectedSelectedCardIndex.getOneBased());
//        int preExecutionSelectedCardIndex = getCustomerListPanel().getSelectedCardIndex();
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//
//        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
//            assertSelectedCardUnchanged();
//        } else {
//            //Todo: ServiceTypeAndRoomTypePanel test
//            //assertSelectedCardChanged(expectedSelectedCardIndex);
//        }
//
//        assertCommandBoxShowsDefaultStyle();
//        assertStatusBarUnchanged();
//    }
//
//    /**
//     * Executes {@code command} and asserts that the,<br>
//     * 1. Command box displays {@code command}.<br>
//     * 2. Command box has the error style class.<br>
//     * 3. Result display box displays {@code expectedResultMessage}.<br>
//     * 4. {@code Storage} and {@code CustomerListPanel} remain unchanged.<br>
//     * 5. Browser url, selected card and status bar remain unchanged.<br>
//     * Verifications 1, 3 and 4 are performed by
//     * {@code HotelManagementSystemSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     *
//     * @see HotelManagementSystemSystemTest#assertApplicationDisplaysExpected(String, String, CustomerModel)
//     */
//    private void assertCommandFailure(String command, String expectedResultMessage) {
//        CustomerModel expectedModel = getModel();
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsErrorStyle();
//        assertStatusBarUnchanged();
//    }
}
