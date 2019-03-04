package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCustomerAtIndex;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CUSTOMER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditCustomerDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.CustomerManager;
import seedu.address.model.CustomerModel;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.EditCustomerDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private CustomerModel model = new CustomerManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Customer editedCustomer = new CustomerBuilder().build();
        EditCommand.EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(editedCustomer).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CUSTOMER, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, editedCustomer);

        CustomerModel expectedModel = new CustomerManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCustomer(model.getFilteredCustomerList().get(0), editedCustomer);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCustomer = Index.fromOneBased(model.getFilteredCustomerList().size());
        Customer lastCustomer = model.getFilteredCustomerList().get(indexLastCustomer.getZeroBased());

        CustomerBuilder customerInList = new CustomerBuilder(lastCustomer);
        Customer editedCustomer = customerInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastCustomer, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, editedCustomer);

        CustomerModel expectedModel = new CustomerManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCustomer(lastCustomer, editedCustomer);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CUSTOMER, new EditCommand.EditCustomerDescriptor());
        Customer editedCustomer = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, editedCustomer);

        CustomerModel expectedModel = new CustomerManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);

        Customer customerInFilteredList = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        Customer editedCustomer = new CustomerBuilder(customerInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CUSTOMER,
            new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, editedCustomer);

        CustomerModel expectedModel = new CustomerManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCustomer(model.getFilteredCustomerList().get(0), editedCustomer);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCustomerUnfilteredList_failure() {
        Customer firstCustomer = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        EditCommand.EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(firstCustomer).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_CUSTOMER, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    @Test
    public void execute_duplicateCustomerFilteredList_failure() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);

        // edit customer in filtered list into a duplicate in address book
        Customer customerInList = model.getAddressBook().getCustomerList().get(INDEX_SECOND_CUSTOMER.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CUSTOMER,
            new EditCustomerDescriptorBuilder(customerInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    @Test
    public void execute_invalidCustomerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        EditCommand.EditCustomerDescriptor descriptor =
            new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void executeInvalidCustomerIndexFilteredListFailure() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);
        Index outOfBoundIndex = INDEX_SECOND_CUSTOMER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCustomerList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
            new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Customer editedCustomer = new CustomerBuilder().build();
        Customer customerToEdit = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        EditCommand.EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(editedCustomer).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CUSTOMER, descriptor);
        CustomerModel expectedModel = new CustomerManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCustomer(customerToEdit, editedCustomer);
        expectedModel.commitAddressBook();

        // edit -> first customer edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered customer list to show all customers
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first customer edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        EditCommand.EditCustomerDescriptor descriptor =
            new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Customer} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited customer in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the customer object regardless of indexing.
     */
    @Test
    public void executeUndoRedoValidIndexFilteredListSameCustomerEdited() throws Exception {
        Customer editedCustomer = new CustomerBuilder().build();
        EditCommand.EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(editedCustomer).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CUSTOMER, descriptor);
        CustomerModel expectedModel = new CustomerManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showCustomerAtIndex(model, INDEX_SECOND_CUSTOMER);
        Customer customerToEdit = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        expectedModel.setCustomer(customerToEdit, editedCustomer);
        expectedModel.commitAddressBook();

        // edit -> edits second customer in unfiltered customer list / first customer in filtered customer list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered customer list to show all customers
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased()), customerToEdit);
        // redo -> edits same second customer in unfiltered customer list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_CUSTOMER, DESC_AMY);

        // same values -> returns true
        EditCustomerDescriptor copyDescriptor = new EditCommand.EditCustomerDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_CUSTOMER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_CUSTOMER, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_CUSTOMER, DESC_BOB)));
    }

}
