package systemtests;

<<<<<<< HEAD
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_ID_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
//import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;
//import static seedu.address.testutil.TypicalCustomers.AMY;
//import static seedu.address.testutil.TypicalCustomers.BOB;
//import static seedu.address.testutil.TypicalCustomers.KEYWORD_MATCHING_MEIER;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CUSTOMER;
//
//import org.junit.Test;
//
//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.EditCustomerCommand;
//import seedu.address.logic.commands.RedoCommand;
//import seedu.address.logic.commands.UndoCommand;
//import seedu.address.model.CustomerModel;
////import seedu.address.model.Model;
//import seedu.address.model.customer.Address;
//import seedu.address.model.customer.Customer;
//import seedu.address.model.customer.Email;
//import seedu.address.model.customer.IdentificationNo;
//import seedu.address.model.customer.Name;
//import seedu.address.model.customer.Phone;
//import seedu.address.model.tag.Tag;
//import seedu.address.testutil.CustomerBuilder;
//import seedu.address.testutil.CustomerUtil;

public class EditCommandSystemTest extends AddressBookSystemTest {
    //
    //@Test
    //public void edit() {
    //CustomerModel model = getModel();
    //
    ///* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */
    //
    ///* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
    // * -> edited
    // */
    //Index index = INDEX_FIRST_CUSTOMER;
    //String command = " " + EditCustomerCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
    //    + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + "  " + ID_DESC_BOB + "  " + ADDRESS_DESC_BOB + " "
    //    + TAG_DESC_HUSBAND + " ";
    //Customer editedCustomer = new CustomerBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
    //assertCommandSuccess(command, index, editedCustomer);
    //
    ///* Case: undo editing the last customer in the list -> last customer restored */
    //command = UndoCommand.COMMAND_WORD;
    //String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
    //assertCommandSuccess(command, model, expectedResultMessage);
    //
    ///* Case: redo editing the last customer in the list -> last customer edited again */
    //command = RedoCommand.COMMAND_WORD;
    //expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
    //model.setCustomer(getModel().getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased()),
    //    editedCustomer);
    //assertCommandSuccess(command, model, expectedResultMessage);
    //
    ///* Case: edit a customer with new values same as existing values -> edited */
    //command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
    // + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
    //assertCommandSuccess(command, index, BOB);
    //
    ///* Case: edit a customer with new values same as another customer's values but with different name -> edited */
    //assertTrue(getModel().getAddressBook().getCustomerList().contains(BOB));
    //index = INDEX_SECOND_CUSTOMER;
    //assertNotEquals(getModel().getFilteredCustomerList().get(index.getZeroBased()), BOB);
    //command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_BOB
    // + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
    //editedCustomer = new CustomerBuilder(BOB).withName(VALID_NAME_AMY).build();
    //assertCommandSuccess(command, index, editedCustomer);
    //
    ///* Case: edit a customer with new values same as another customer's values but with different phone, email, id
    // * -> edited
    // */
    //index = INDEX_SECOND_CUSTOMER;
    //command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY
    // + EMAIL_DESC_AMY + ID_DESC_AMY + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
    //editedCustomer = new CustomerBuilder(BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
    //    .withIdNum(VALID_ID_AMY).build();
    //assertCommandSuccess(command, index, editedCustomer);
    //
    ///* Case: clear tags -> cleared */
    //index = INDEX_FIRST_CUSTOMER;
    //command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
    //Customer customerToEdit = getModel().getFilteredCustomerList().get(index.getZeroBased());
    //editedCustomer = new CustomerBuilder(customerToEdit).withTags().build();
    //assertCommandSuccess(command, index, editedCustomer);
    //
    ///* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */
    //
    ///* Case: filtered customer list, edit index within bounds of address book and customer list -> edited */
    //showCustomersWithName(KEYWORD_MATCHING_MEIER);
    //index = INDEX_FIRST_CUSTOMER;
    //assertTrue(index.getZeroBased() < getModel().getFilteredCustomerList().size());
    //command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
    //customerToEdit = getModel().getFilteredCustomerList().get(index.getZeroBased());
    //editedCustomer = new CustomerBuilder(customerToEdit).withName(VALID_NAME_BOB).build();
    //assertCommandSuccess(command, index, editedCustomer);
    //
    ///* Case: filtered customer list, edit index within bounds of address book but out of bounds of customer list
    // * -> rejected
    // */
    //showCustomersWithName(KEYWORD_MATCHING_MEIER);
    //int invalidIndex = getModel().getAddressBook().getCustomerList().size();
    //assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
    //    Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    //
    ///* --------------------- Performing edit operation while a customer card is selected ------------------------ */
    //
    ///* Case: selects first card in the customer list, edit a customer -> edited, card selection remains unchanged
    // * but
    // * browser url changes
    // */
    //showAllCustomers();
    //index = INDEX_FIRST_CUSTOMER;
    //selectCustomer(index);
    //command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY
    // + EMAIL_DESC_AMY + ID_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
    //// this can be misleading: card selection actually remains unchanged but the
    //// browser's url is updated to reflect the new customer's name
    //assertCommandSuccess(command, index, AMY, index);
    //
    ///* --------------------------------- Performing invalid edit operation -------------------------------------- */
    //
    ///* Case: invalid index (0) -> rejected */
    //assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
    //    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCustomerCommand.MESSAGE_USAGE));
    //
    ///* Case: invalid index (-1) -> rejected */
    //assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
    //    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCustomerCommand.MESSAGE_USAGE));
    //
    ///* Case: invalid index (size + 1) -> rejected */
    //invalidIndex = getModel().getFilteredCustomerList().size() + 1;
    //assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
    //    Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    //
    ///* Case: missing index -> rejected */
    //assertCommandFailure(EditCustomerCommand.COMMAND_WORD + NAME_DESC_BOB,
    //    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCustomerCommand.MESSAGE_USAGE));
    //
    ///* Case: missing all fields -> rejected */
    //assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased(),
    //    EditCustomerCommand.MESSAGE_NOT_EDITED);
    //
    ///* Case: invalid name -> rejected */
    //assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
    //        + INVALID_NAME_DESC,
    //    Name.MESSAGE_CONSTRAINTS);
    //
    ///* Case: invalid phone -> rejected */
    //assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
    //        + INVALID_PHONE_DESC,
    //    Phone.MESSAGE_CONSTRAINTS);
    //
    ///* Case: invalid email -> rejected */
    //assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
    //        + INVALID_EMAIL_DESC,
    //    Email.MESSAGE_CONSTRAINTS);
    //
    ///* Case: invalid id -> rejected */
    //assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
    //        + INVALID_ID_DESC,
    //    IdentificationNo.MESSAGE_CONSTRAINTS);
    //
    ///* Case: invalid address -> rejected */
    //assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
    //        + INVALID_ADDRESS_DESC,
    //    Address.MESSAGE_CONSTRAINTS);
    //
    ///* Case: invalid tag -> rejected */
    //assertCommandFailure(EditCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
    //        + INVALID_TAG_DESC,
    //    Tag.MESSAGE_CONSTRAINTS);
    //
    ///* Case: edit a customer with new values same as another customer's values -> rejected */
    //executeCommand(CustomerUtil.getAddCommand(BOB));
    //assertTrue(getModel().getAddressBook().getCustomerList().contains(BOB));
    //index = INDEX_FIRST_CUSTOMER;
    //assertFalse(getModel().getFilteredCustomerList().get(index.getZeroBased()).equals(BOB));
    //command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
    // + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
    //assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    //
    ///* Case: edit a customer with new values same as another customer's values but with different tags -> rejected*/
    //command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
    // + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;
    //assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    //
    ///* Case: edit a customer with new values same as another customer's values but with different address ->
    // * rejected */
    //command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
    // + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
    //assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    //
    ///* Case: edit a customer with new values same as another customer's values but with different phone ->
    // * rejected */
    //command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY
    // + EMAIL_DESC_BOB + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
    //assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    //
    ///* Case: edit a customer with new values same as another customer's values but with different email ->
    // * rejected */
    //command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
    // + EMAIL_DESC_AMY + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
    //assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    //
    ///* Case: edit a customer with new values same as another customer's values but with different id ->
    // * rejected */
    //command = EditCustomerCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
    // + EMAIL_DESC_BOB + ID_DESC_AMY + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
    //assertCommandFailure(command, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    //}
    //
    ///**
    // * Performs the same verification as {@code assertCommandSuccess(String, Index, Customer, Index)} except that
    // * the browser url and selected card remain unchanged.
    // *
    // * @param toEdit the index of the current model's filtered list
    // * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Customer, Index)
    // */
    //private void assertCommandSuccess(String command, Index toEdit, Customer editedCustomer) {
    //    assertCommandSuccess(command, toEdit, editedCustomer, null);
    //}
    //
    ///**
    // * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and, <br>
    // * 1. Asserts that result display box displays the success message of executing {@code EditCustomerCommand}.<br>
    // * 2. Asserts that the model related components are updated to reflect the customer at index {@code toEdit} being
    // * updated to values specified {@code editedCustomer}.<br>
    // *
    // * @param toEdit the index of the current model's filtered list.
    // * @see EditCommandSystemTest#assertCommandSuccess(String, CustomerModel, String, Index)
    // */
    //private void assertCommandSuccess(String command, Index toEdit, Customer editedCustomer,
    //                                  Index expectedSelectedCardIndex) {
    //    CustomerModel expectedModel = getModel();
    //    expectedModel.setCustomer(expectedModel.getFilteredCustomerList().get(toEdit.getZeroBased()), editedCustomer);
    //    expectedModel.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    //
    //    assertCommandSuccess(command, expectedModel,
    //        String.format(EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, editedCustomer),
    // expectedSelectedCardIndex);
    //}
    //
    ///**
    // * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
    // * browser url and selected card remain unchanged.
    // *
    // * @see EditCommandSystemTest#assertCommandSuccess(String, CustomerModel, String, Index)
    // */
    //private void assertCommandSuccess(String command, CustomerModel expectedModel, String expectedResultMessage) {
    //    assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    //}
    //
    ///**
    // * Executes {@code command} and in addition,<br>
    // * 1. Asserts that the command box displays an empty string.<br>
    // * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
    // * 3. Asserts that the browser url and selected card update accordingly depending on the card at
    // * {@code expectedSelectedCardIndex}.<br>
    // * 4. Asserts that the status bar's sync status changes.<br>
    // * 5. Asserts that the command box has the default style class.<br>
    // * Verifications 1 and 2 are performed by
    // * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
    // *
    // * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, CustomerModel)
    // * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
    // */
    //private void assertCommandSuccess(String command, CustomerModel expectedModel, String expectedResultMessage,
    //                                  Index expectedSelectedCardIndex) {
    //    executeCommand(command);
    //    expectedModel.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    //    assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
    //    assertCommandBoxShowsDefaultStyle();
    //    if (expectedSelectedCardIndex != null) {
    //        assertSelectedCardChanged(expectedSelectedCardIndex);
    //    } else {
    //        assertSelectedCardUnchanged();
    //    }
    //    assertStatusBarUnchangedExceptSyncStatus();
    //}
    //
    ///**
    // * Executes {@code command} and in addition,<br>
    // * 1. Asserts that the command box displays {@code command}.<br>
    // * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
    // * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
    // * 4. Asserts that the command box has the error style.<br>
    // * Verifications 1 and 2 are performed by
    // * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
    // *
    // * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, CustomerModel)
    // */
    //private void assertCommandFailure(String command, String expectedResultMessage) {
    //    CustomerModel expectedModel = getModel();
    //
    //    executeCommand(command);
    //    assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
    //    assertSelectedCardUnchanged();
    //    assertCommandBoxShowsErrorStyle();
    //    assertStatusBarUnchanged();
    //}
=======
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;
import static seedu.address.testutil.TypicalCustomers.AMY;
import static seedu.address.testutil.TypicalCustomers.BOB;
import static seedu.address.testutil.TypicalCustomers.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CUSTOMER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.CustomerModel;
import seedu.address.model.customer.Address;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.Email;
import seedu.address.model.customer.IdentificationNo;
import seedu.address.model.customer.Name;
import seedu.address.model.customer.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.CustomerUtil;

public class EditCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() {
        CustomerModel model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_CUSTOMER;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
            + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + "  " + ID_DESC_BOB + "  " + ADDRESS_DESC_BOB + " "
            + TAG_DESC_HUSBAND + " ";
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
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a customer with new values same as another customer's values but with different name -> edited */
        assertTrue(getModel().getAddressBook().getCustomerList().contains(BOB));
        index = INDEX_SECOND_CUSTOMER;
        assertNotEquals(getModel().getFilteredCustomerList().get(index.getZeroBased()), BOB);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedCustomer = new CustomerBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedCustomer);

        /* Case: edit a customer with new values same as another customer's values but with different phone, email, id
         * -> edited
         */
        index = INDEX_SECOND_CUSTOMER;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + ID_DESC_AMY + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedCustomer = new CustomerBuilder(BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withIdNum(VALID_ID_AMY).build();
        assertCommandSuccess(command, index, editedCustomer);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_CUSTOMER;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Customer customerToEdit = getModel().getFilteredCustomerList().get(index.getZeroBased());
        editedCustomer = new CustomerBuilder(customerToEdit).withTags().build();
        assertCommandSuccess(command, index, editedCustomer);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered customer list, edit index within bounds of address book and customer list -> edited */
        showCustomersWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_CUSTOMER;
        assertTrue(index.getZeroBased() < getModel().getFilteredCustomerList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB;
        customerToEdit = getModel().getFilteredCustomerList().get(index.getZeroBased());
        editedCustomer = new CustomerBuilder(customerToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedCustomer);

        /* Case: filtered customer list, edit index within bounds of address book but out of bounds of customer list
         * -> rejected
         */
        showCustomersWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getCustomerList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
            Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a customer card is selected ------------------------ */

        /* Case: selects first card in the customer list, edit a customer -> edited, card selection remains unchanged
         * but
         * browser url changes
         */
        showAllCustomers();
        index = INDEX_FIRST_CUSTOMER;
        selectCustomer(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + ID_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new customer's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredCustomerList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
            Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_BOB,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased(),
            EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
                + INVALID_NAME_DESC,
            Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
                + INVALID_PHONE_DESC,
            Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
                + INVALID_EMAIL_DESC,
            Email.MESSAGE_CONSTRAINTS);

        /* Case: invalid id -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
                + INVALID_ID_DESC,
            IdentificationNo.MESSAGE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
                + INVALID_ADDRESS_DESC,
            Address.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased()
                + INVALID_TAG_DESC,
            Tag.MESSAGE_CONSTRAINTS);

        /* Case: edit a customer with new values same as another customer's values -> rejected */
        executeCommand(CustomerUtil.getAddCommand(BOB));
        assertTrue(getModel().getAddressBook().getCustomerList().contains(BOB));
        index = INDEX_FIRST_CUSTOMER;
        assertFalse(getModel().getFilteredCustomerList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_CUSTOMER);

        /* Case: edit a customer with new values same as another customer's values but with different tags -> rejected*/
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_CUSTOMER);

        /* Case: edit a customer with new values same as another customer's values but with different address ->
         * rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ID_DESC_BOB + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_CUSTOMER);

        /* Case: edit a customer with new values same as another customer's values but with different phone ->
         * rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_BOB
            + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_CUSTOMER);

        /* Case: edit a customer with new values same as another customer's values but with different email ->
         * rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
            + ID_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_CUSTOMER);

        /* Case: edit a customer with new values same as another customer's values but with different id ->
         * rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ID_DESC_AMY + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Customer, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Customer, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Customer editedCustomer) {
        assertCommandSuccess(command, toEdit, editedCustomer, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and, <br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the customer at index {@code toEdit} being
     * updated to values specified {@code editedCustomer}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, CustomerModel, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Customer editedCustomer,
                                      Index expectedSelectedCardIndex) {
        CustomerModel expectedModel = getModel();
        expectedModel.setCustomer(expectedModel.getFilteredCustomerList().get(toEdit.getZeroBased()), editedCustomer);
        expectedModel.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);

        assertCommandSuccess(command, expectedModel,
            String.format(EditCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, editedCustomer), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see EditCommandSystemTest#assertCommandSuccess(String, CustomerModel, String, Index)
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, CustomerModel)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, CustomerModel)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        CustomerModel expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
>>>>>>> 5f2e2654add99fce359c59ef1c32ffe4733ed00d
}
