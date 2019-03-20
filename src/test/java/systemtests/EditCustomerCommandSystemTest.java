package systemtests;

//import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.DATE_OF_BIRTH_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.DATE_OF_BIRTH_DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.ID_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.ID_DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.INVALID_DATE_OF_BIRTH_DESC;
import static seedu.hms.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.hms.logic.commands.CommandTestUtil.INVALID_ID_DESC;
import static seedu.hms.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.hms.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.hms.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.hms.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.hms.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.hms.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;
import static seedu.hms.testutil.TypicalCustomers.BOB;
import static seedu.hms.testutil.TypicalCustomers.KEYWORD_MATCHING_MEIER;
import static seedu.hms.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
import static seedu.hms.testutil.TypicalIndexes.INDEX_SECOND_CUSTOMER;

import org.junit.Test;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.DeleteCustomerCommand;
import seedu.hms.logic.commands.EditCustomerCommand;
import seedu.hms.logic.commands.RedoCommand;
import seedu.hms.logic.commands.UndoCommand;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.DateOfBirth;
import seedu.hms.model.customer.Email;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.customer.Name;
import seedu.hms.model.customer.Phone;
import seedu.hms.model.customer.exceptions.DuplicateCustomerException;
import seedu.hms.model.tag.Tag;
import seedu.hms.testutil.CustomerBuilder;
import seedu.hms.testutil.CustomerUtil;
//import seedu.hms.testutil.CustomerUtil;

public class EditCustomerCommandSystemTest extends HotelManagementSystemSystemTest {

    @Test
    public void edit() {
        CustomerModel model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_CUSTOMER;
        String command = " " + EditCustomerCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB
            + "  " + PHONE_DESC_BOB + " " + DATE_OF_BIRTH_DESC_BOB + " " + EMAIL_DESC_BOB
            + "  " + ID_DESC_BOB + "  " + ADDRESS_DESC_BOB + " " + TAG_DESC_HUSBAND + " ";
        Customer editedCustomer = new CustomerBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedCustomer);

        /* Case: undo editing the last customer in the list -> last customer restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last customer in the list -> last customer edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setCustomer(getModel().getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased()),
            editedCustomer);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a customer with new values same as existing values -> edited */
        command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a customer with new values same as another customer's values but with different name -> edited */
        assertTrue(getModel().getHotelManagementSystem().getCustomerList().contains(BOB));
        index = INDEX_SECOND_CUSTOMER;
        assertNotEquals(getModel().getFilteredCustomerList().get(index.getZeroBased()), BOB);
        command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedCustomer = new CustomerBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);

        /* Case: edit a customer with new values same as another customer's values but with different phone, email, id
         * -> edited
         */
        index = INDEX_SECOND_CUSTOMER;
        command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY
            + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_AMY + ID_DESC_AMY + ADDRESS_DESC_BOB
            + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedCustomer = new CustomerBuilder(BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withIdNum(VALID_ID_AMY).build();
        assertCommandSuccess(command, index, editedCustomer);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_CUSTOMER;
        command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Customer customerToEdit = getModel().getFilteredCustomerList().get(index.getZeroBased());
        editedCustomer = new CustomerBuilder(customerToEdit).withTags().build();
        assertCommandSuccess(command, index, editedCustomer);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered customer list, edit index within bounds of hms book and customer list -> edited */
        showCustomersWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_CUSTOMER;
        assertTrue(index.getZeroBased() < getModel().getFilteredCustomerList().size());
        command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB;
        customerToEdit = getModel().getFilteredCustomerList().get(index.getZeroBased());
        editedCustomer = new CustomerBuilder(customerToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedCustomer);

        /* Case: filtered customer list, edit index within bounds of hms book but out of bounds of customer list
         * -> rejected
         */
        showCustomersWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getHotelManagementSystem().getCustomerList().size();
        assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
            Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a customer card is selected ------------------------ */

        /* Case: selects first card in the customer list, edit a customer -> not edited, card selection remains
        unchanged
         * but
         * browser url changes
         */
        showAllCustomers();
        index = INDEX_FIRST_CUSTOMER;
        selectCustomer(index);
        command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY
            + EMAIL_DESC_AMY + ID_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + DATE_OF_BIRTH_DESC_AMY;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new customer's name
        assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCustomerCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCustomerCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredCustomerList().size() + 1;
        assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
            Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCustomerCommand.COMMAND_WORD + NAME_DESC_BOB,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCustomerCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased(),
            EditCustomerCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
                + INVALID_NAME_DESC,
            Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
                + INVALID_PHONE_DESC,
            Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
                + INVALID_EMAIL_DESC,
            Email.MESSAGE_CONSTRAINTS);

        /* Case: invalid dateOfBirth -> rejected */
        assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
                + INVALID_DATE_OF_BIRTH_DESC,
            DateOfBirth.MESSAGE_CONSTRAINTS);

        /* Case: invalid id -> rejected */
        assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
                + INVALID_ID_DESC,
            IdentificationNo.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
                + INVALID_TAG_DESC,
            Tag.MESSAGE_CONSTRAINTS);

        /* Reject commands where editing a customer will result in duplicates */
        // We have two Bobs in the front now. We delete them and add the only Bob.
        String deleteBobCommand = DeleteCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased();
        executeCommand(deleteBobCommand);
        executeCommand(deleteBobCommand);
        executeCommand(CustomerUtil.getAddCommand(BOB));
        assertTrue(getModel().getHotelManagementSystem().getCustomerList().contains(BOB));

        /* Case: edit a customer with new values same as another customer's values -> rejected */
        index = INDEX_FIRST_CUSTOMER;
        assertFalse(getModel().getFilteredCustomerList().get(index.getZeroBased()).equals(BOB));
        command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);

        /* Case: edit a customer with new values same as another customer's values but with different tag -> rejected*/
        command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_BOB + ID_DESC_BOB
            + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);

        /* Case: edit a customer with new values same as another customer's values but with different address ->
         * rejected */
        command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_AMY
            + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);

        /* Case: edit a customer with new values same as another customer's values but with different phone ->
         * rejected */
        command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY
            + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        //assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);

        /* Case: edit a customer with new values same as another customer's values but with different email ->
         * rejected */
        command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_AMY + ID_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);

        /* Case: edit a customer with new values same as another customer's values but with different id ->
         * rejected */
        command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
            + DATE_OF_BIRTH_DESC_BOB + EMAIL_DESC_BOB + ID_DESC_AMY + ADDRESS_DESC_BOB
            + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Customer, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @see EditCustomerCommandSystemTest#assertCommandSuccess(String, Index, Customer, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Customer editedCustomer) {
        assertCommandSuccess(command, toEdit, editedCustomer, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and, <br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCustomerCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the customer at index {@code toEdit} being
     * updated to values specified {@code editedCustomer}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see EditCustomerCommandSystemTest#assertCommandSuccess(String, CustomerModel, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Customer editedCustomer,
                                      Index expectedSelectedCardIndex) {
        CustomerModel expectedModel = getModel();
        expectedModel.setCustomer(expectedModel.getFilteredCustomerList().get(toEdit.getZeroBased()), editedCustomer);
        expectedModel.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);

        assertCommandSuccess(command, expectedModel,
            String.format(EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, editedCustomer),
            expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see EditCustomerCommandSystemTest#assertCommandSuccess(String, CustomerModel, String, Index)
     */
    private void assertCommandSuccess(String command, CustomerModel expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code HotelManagementSystemSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see HotelManagementSystemSystemTest#assertApplicationDisplaysExpected(String, String, CustomerModel)
     * @see HotelManagementSystemSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, CustomerModel expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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
