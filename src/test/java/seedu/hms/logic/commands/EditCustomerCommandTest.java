package seedu.hms.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
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
import seedu.hms.logic.commands.EditCustomerCommand.EditCustomerDescriptor;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.customer.Customer;
import seedu.hms.testutil.CustomerBuilder;
import seedu.hms.testutil.EditCustomerDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditCustomerCommand.
 */
public class EditCustomerCommandTest {

    private CustomerModel model =
        new CustomerManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Customer editedCustomer = new CustomerBuilder().build();
        EditCustomerCommand.EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(editedCustomer)
            .build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST_CUSTOMER, descriptor);

        String expectedMessage = String.format(EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, editedCustomer);

        CustomerModel expectedModel =
            new CustomerManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.setCustomer(model.getFilteredCustomerList().get(0), editedCustomer);
        expectedModel.commitHotelManagementSystem();

        assertCommandSuccess(editCustomerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCustomer = Index.fromOneBased(model.getFilteredCustomerList().size());
        Customer lastCustomer = model.getFilteredCustomerList().get(indexLastCustomer.getZeroBased());

        CustomerBuilder customerInList = new CustomerBuilder(lastCustomer);
        Customer editedCustomer = customerInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withTags(VALID_TAG_HUSBAND).build();

        EditCustomerCommand.EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(indexLastCustomer, descriptor);

        String expectedMessage = String.format(EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, editedCustomer);

        CustomerModel expectedModel =
            new CustomerManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.setCustomer(lastCustomer, editedCustomer);
        expectedModel.commitHotelManagementSystem();

        assertCommandSuccess(editCustomerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST_CUSTOMER,
            new EditCustomerCommand.EditCustomerDescriptor());
        Customer editedCustomer = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());

        String expectedMessage = String.format(EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, editedCustomer);

        CustomerModel expectedModel =
            new CustomerManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.commitHotelManagementSystem();

        assertCommandSuccess(editCustomerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);

        Customer customerInFilteredList = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        Customer editedCustomer = new CustomerBuilder(customerInFilteredList).withName(VALID_NAME_BOB).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST_CUSTOMER,
            new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, editedCustomer);

        CustomerModel expectedModel =
            new CustomerManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.setCustomer(model.getFilteredCustomerList().get(0), editedCustomer);
        expectedModel.commitHotelManagementSystem();

        assertCommandSuccess(editCustomerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCustomerUnfilteredList_failure() {
        Customer firstCustomer = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        EditCustomerCommand.EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(firstCustomer)
            .build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_SECOND_CUSTOMER, descriptor);

        assertCommandFailure(editCustomerCommand, model, commandHistory,
            EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    @Test
    public void execute_duplicateCustomerFilteredList_failure() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);

        // edit customer in filtered list into a duplicate in hms book
        Customer customerInList =
            model.getHotelManagementSystem().getCustomerList().get(INDEX_SECOND_CUSTOMER.getZeroBased());
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST_CUSTOMER,
            new EditCustomerDescriptorBuilder(customerInList).build());

        assertCommandFailure(editCustomerCommand, model, commandHistory,
            EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    @Test
    public void execute_invalidCustomerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        EditCustomerCommand.EditCustomerDescriptor descriptor =
            new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCustomerCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of hms book
     */
    @Test
    public void executeInvalidCustomerIndexFilteredListFailure() {
        showCustomerAtIndex(model, INDEX_FIRST_CUSTOMER);
        Index outOfBoundIndex = INDEX_SECOND_CUSTOMER;
        // ensures that outOfBoundIndex is still in bounds of hms book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getHotelManagementSystem().getCustomerList().size());

        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(outOfBoundIndex,
            new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCustomerCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Customer editedCustomer = new CustomerBuilder().build();
        Customer customerToEdit = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        EditCustomerCommand.EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(editedCustomer)
            .build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST_CUSTOMER, descriptor);
        CustomerModel expectedModel =
            new CustomerManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());
        expectedModel.setCustomer(customerToEdit, editedCustomer);
        expectedModel.commitHotelManagementSystem();

        // edit -> first customer edited
        editCustomerCommand.execute(model, commandHistory);

        // undo -> reverts hotelManagementSystem back to previous state and filtered customer list to show all customers
        expectedModel.undoHotelManagementSystem();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first customer edited again
        expectedModel.redoHotelManagementSystem();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        EditCustomerCommand.EditCustomerDescriptor descriptor =
            new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(outOfBoundIndex, descriptor);

        // execution failed -> hms book state not added into model
        assertCommandFailure(editCustomerCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);

        // single hms book state in model -> undoCommand and redoCommand fail
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
        EditCustomerCommand.EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(editedCustomer)
            .build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST_CUSTOMER, descriptor);
        CustomerModel expectedModel =
            new CustomerManager(new VersionedHotelManagementSystem(model.getHotelManagementSystem()),
                new UserPrefs());

        showCustomerAtIndex(model, INDEX_SECOND_CUSTOMER);
        Customer customerToEdit = model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased());
        expectedModel.setCustomer(customerToEdit, editedCustomer);
        expectedModel.commitHotelManagementSystem();

        // edit -> edits second customer in unfiltered customer list / first customer in filtered customer list
        editCustomerCommand.execute(model, commandHistory);

        // undo -> reverts hotelManagementSystem back to previous state and filtered customer list to show all customers
        expectedModel.undoHotelManagementSystem();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredCustomerList().get(INDEX_FIRST_CUSTOMER.getZeroBased()), customerToEdit);
        // redo -> edits same second customer in unfiltered customer list
        expectedModel.redoHotelManagementSystem();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCustomerCommand standardCommand = new EditCustomerCommand(INDEX_FIRST_CUSTOMER, DESC_AMY);

        // same values -> returns true
        EditCustomerDescriptor copyDescriptor = new EditCustomerCommand.EditCustomerDescriptor(DESC_AMY);
        EditCustomerCommand commandWithSameValues = new EditCustomerCommand(INDEX_FIRST_CUSTOMER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearHotelManagementSystemCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCustomerCommand(INDEX_SECOND_CUSTOMER, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCustomerCommand(INDEX_FIRST_CUSTOMER, DESC_BOB)));
    }

}
