package seedu.hms.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.customer.Customer;
import seedu.hms.testutil.CustomerBuilder;

public class AddCustomerCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCustomer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCustomerCommand(null);
    }

    @Test
    public void execute_customerAcceptedByModel_addSuccessful() throws Exception {
        CustomerModel modelStub =
            new CustomerManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
                new UserPrefs());
        Customer validCustomer = new CustomerBuilder().build();

        CommandResult commandResult = new AddCustomerCommand(validCustomer).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCustomerCommand.MESSAGE_SUCCESS, validCustomer),
            commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateCustomer_throwsCommandException() throws Exception {
        Customer validCustomer = new CustomerBuilder().build();
        AddCustomerCommand addCommand = new AddCustomerCommand(validCustomer);
        CustomerModel modelStub =
            new CustomerManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
                new UserPrefs());

        addCommand.execute(modelStub, commandHistory);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Customer alice = new CustomerBuilder().withName("Alice").build();
        Customer bob = new CustomerBuilder().withName("Bob").build();
        AddCustomerCommand addAliceCommand = new AddCustomerCommand(alice);
        AddCustomerCommand addBobCommand = new AddCustomerCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCustomerCommand addAliceCommandCopy = new AddCustomerCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different customer -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    //
    ///**
    // * A default model stub that have all of the methods failing.
    // */
    //private class ModelStub implements Model {
    //    @Override
    //    public ReadOnlyUserPrefs getUserPrefs() {
    //        throw new AssertionError("This method should not be called.");
    //    }
    //
    //    @Override
    //    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
    //        throw new AssertionError("This method should not be called.");
    //    }
    //
    //    @Override
    //    public GuiSettings getGuiSettings() {
    //        throw new AssertionError("This method should not be called.");
    //    }
    //
    //    @Override
    //    public void setGuiSettings(GuiSettings guiSettings) {
    //        throw new AssertionError("This method should not be called.");
    //    }
    //
    //    @Override
    //    public Path getHotelManagementSystemFilePath() {
    //        throw new AssertionError("This method should not be called.");
    //    }
    //
    //    @Override
    //    public void setHotelManagementSystemFilePath(Path hotelManagementSystemFilePath) {
    //        throw new AssertionError("This method should not be called.");
    //    }
    //
    //    @Override
    //    public ReadOnlyHotelManagementSystem getHotelManagementSystem() {
    //        throw new AssertionError("This method should not be called.");
    //    }
    //
    //    @Override
    //    public void setHotelManagementSystem(ReadOnlyHotelManagementSystem newData) {
    //        throw new AssertionError("This method should not be called.");
    //    }
    //
    //    @Override
    //    public boolean canUndoHotelManagementSystem() {
    //        throw new AssertionError("This method should not be called.");
    //    }
    //
    //    @Override
    //    public boolean canRedoHotelManagementSystem() {
    //        throw new AssertionError("This method should not be called.");
    //    }
    //
    //    @Override
    //    public void undoHotelManagementSystem() {
    //        throw new AssertionError("This method should not be called.");
    //    }
    //
    //    @Override
    //    public void redoHotelManagementSystem() {
    //        throw new AssertionError("This method should not be called.");
    //    }
    //
    //    @Override
    //    public void commitHotelManagementSystem() {
    //        throw new AssertionError("This method should not be called.");
    //    }
    //}
    //
    ///**
    // * A Model stub that contains a single customer.
    // */
    //private class ModelStubWithCustomer extends ModelStub {
    //    private final Customer customer;
    //
    //    ModelStubWithCustomer(Customer customer) {
    //        requireNonNull(customer);
    //        this.customer = customer;
    //    }
    //}
    //
    ///**
    // * A Model stub that always accept the customer being added.
    // */
    //private class ModelStubAcceptingCustomerAdded extends ModelStub {
    //    final ArrayList<Customer> customersAdded = new ArrayList<>();
    //
    //    @Override
    //    public void commitHotelManagementSystem() {
    //        // called by {@code AddCustomerCommand#execute()}
    //    }
    //
    //    @Override
    //    public ReadOnlyHotelManagementSystem getHotelManagementSystem() {
    //        return new HotelManagementSystem();
    //    }
    //}

}
