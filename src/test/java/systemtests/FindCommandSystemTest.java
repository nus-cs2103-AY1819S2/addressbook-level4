package systemtests;

//import static org.junit.Assert.assertFalse;
//import static seedu.hms.commons.core.Messages.MESSAGE_CUSTOMERS_LISTED_OVERVIEW;
//import static seedu.hms.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
//import static seedu.hms.testutil.TypicalCustomers.BENSON;
//import static seedu.hms.testutil.TypicalCustomers.CARL;
//import static seedu.hms.testutil.TypicalCustomers.DANIEL;
//import static seedu.hms.testutil.TypicalCustomers.KEYWORD_MATCHING_MEIER;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//
//import seedu.hms.commons.core.index.Index;
//import seedu.hms.logic.commands.DeleteCustomerCommand;
//import seedu.hms.logic.commands.FindNameCommand;
//import seedu.hms.logic.commands.RedoCommand;
//import seedu.hms.logic.commands.UndoCommand;
//import seedu.hms.model.CustomerModel;
//import seedu.hms.model.tag.Tag;

public class FindCommandSystemTest extends HotelManagementSystemSystemTest {
//    @Test
//    public void find() {
//        /* Case: find multiple customers in address book, command with leading spaces and trailing spaces
//         * -> 2 customers found
//         */
//        String command = "   " + FindNameCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER + "   ";
//        CustomerModel expectedModel = getModel();
//        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel: "Meier"
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: repeat previous find command where customer list is displaying the customers we are finding
//         * -> 2 customers found
//         */
//        command = FindNameCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find customer where customer list is not displaying the customer we are finding -> 1 customer
//        found */
//        command = FindNameCommand.COMMAND_WORD + " Carl";
//        ModelHelper.setFilteredList(expectedModel, CARL);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple customers in address book, 2 keywords -> 2 customers found */
//        command = FindNameCommand.COMMAND_WORD + " Benson Daniel";
//        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple customers in address book, 2 keywords in reversed order -> 2 customers found */
//        command = FindNameCommand.COMMAND_WORD + " Daniel Benson";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple customers in address book, 2 keywords with 1 repeat -> 2 customers found */
//        command = FindNameCommand.COMMAND_WORD + " Daniel Benson Daniel";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple customers in address book, 2 matching keywords and 1 non-matching keyword
//         * -> 2 customers found
//         */
//        command = FindNameCommand.COMMAND_WORD + " Daniel Benson NonMatchingKeyWord";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: undo previous find command -> rejected */
//        command = UndoCommand.COMMAND_WORD;
//        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
//        assertCommandFailure(command, expectedResultMessage);
//
//        /* Case: redo previous find command -> rejected */
//        command = RedoCommand.COMMAND_WORD;
//        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
//        assertCommandFailure(command, expectedResultMessage);
//
//        /* Case: find same customers in address book after deleting 1 of them -> 1 customer found */
//        executeCommand(DeleteCustomerCommand.COMMAND_WORD + " 1");
//        assertFalse(getModel().getHotelManagementSystem().getCustomerList().contains(BENSON));
//        command = FindNameCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
//        expectedModel = getModel();
//        ModelHelper.setFilteredList(expectedModel, DANIEL);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find customer in address book, keyword is same as name but of different case -> 1 customer
//        found */
//        command = FindNameCommand.COMMAND_WORD + " MeIeR";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find customer in address book, keyword is substring of name -> 0 customers found */
//        command = FindNameCommand.COMMAND_WORD + " Mei";
//        ModelHelper.setFilteredList(expectedModel);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find customer in address book, name is substring of keyword -> 0 customers found */
//        command = FindNameCommand.COMMAND_WORD + " Meiers";
//        ModelHelper.setFilteredList(expectedModel);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find customer not in address book -> 0 customers found */
//        command = FindNameCommand.COMMAND_WORD + " Mark";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find phone number of customer in address book -> 0 customers found */
//        command = FindNameCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find address of customer in address book -> 0 customers found */
//        command = FindNameCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find email of customer in address book -> 0 customers found */
//        command = FindNameCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find tags of customer in address book -> 0 customers found */
//        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
//        command = FindNameCommand.COMMAND_WORD + " " + tags.get(0).tagName;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find while a customer is selected -> selected card deselected */
//        showAllCustomers();
//        selectCustomer(Index.fromOneBased(1));
//        assertFalse(getCustomerListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName));
//        command = FindNameCommand.COMMAND_WORD + " Daniel";
//        ModelHelper.setFilteredList(expectedModel, DANIEL);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardDeselected();
//
//        /* Case: find customer in empty address book -> 0 customers found */
//        deleteAllCustomers();
//        command = FindNameCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
//        expectedModel = getModel();
//        ModelHelper.setFilteredList(expectedModel, DANIEL);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: mixed case command word -> rejected */
//        command = "FiNd Meier";
//        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    /**
//     * Executes {@code command} and verifies that the command box displays an empty string, the result display
//     * box displays {@code Messages#MESSAGE_CUSTOMERS_LISTED_OVERVIEW} with the number of people in the filtered list,
//     * and the model related components equal to {@code expectedModel}.
//     * These verifications are done by
//     * {@code HotelManagementSystemSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
//     * selected card updated accordingly, depending on {@code cardStatus}.
//     *
//     * @see HotelManagementSystemSystemTest#assertApplicationDisplaysExpected(String, String, CustomerModel)
//     */
//    private void assertCommandSuccess(String command, CustomerModel expectedModel) {
//        String expectedResultMessage = String.format(
//            MESSAGE_CUSTOMERS_LISTED_OVERVIEW, expectedModel.getFilteredCustomerList().size());
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//        assertCommandBoxShowsDefaultStyle();
//        assertStatusBarUnchanged();
//    }
//
//    /**
//     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
//     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
//     * These verifications are done by
//     * {@code HotelManagementSystemSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
//     * error style.
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
