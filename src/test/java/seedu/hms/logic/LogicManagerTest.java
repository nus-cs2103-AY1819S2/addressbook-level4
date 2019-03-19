package seedu.hms.logic;

import static org.junit.Assert.assertEquals;
import static seedu.hms.commons.core.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;
import static seedu.hms.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.hms.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.DATE_OF_BIRTH_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.ID_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.hms.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.hms.testutil.TypicalCustomers.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.hms.logic.commands.AddCustomerCommand;
import seedu.hms.logic.commands.CommandResult;
import seedu.hms.logic.commands.HistoryCommand;
import seedu.hms.logic.commands.ListCustomerCommand;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.model.BookingManager;
import seedu.hms.model.BookingModel;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.ReadOnlyHotelManagementSystem;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.customer.Customer;
import seedu.hms.storage.JsonHotelManagementSystemStorage;
import seedu.hms.storage.JsonUserPrefsStorage;
import seedu.hms.storage.StorageManager;
import seedu.hms.testutil.CustomerBuilder;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private CustomerModel customerModel = new CustomerManager();
    private BookingModel bookingModel = new BookingManager();
    private Logic logic;

    @Before
    public void setUp() throws Exception {
        JsonHotelManagementSystemStorage hotelManagementSystemStorage =
            new JsonHotelManagementSystemStorage(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(hotelManagementSystemStorage, userPrefsStorage);
        logic = new LogicManager(customerModel, bookingModel, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistoryCorrect(invalidCommand);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "deletecustomer 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        assertHistoryCorrect(deleteCommand);
    }

    @Test
    public void execute_validCommand_success() {
        String listCommand = ListCustomerCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCustomerCommand.MESSAGE_SUCCESS, customerModel);
        assertHistoryCorrect(listCommand);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() throws Exception {
        // Setup LogicManager with JsonHotelManagementSystemIoExceptionThrowingStub
        JsonHotelManagementSystemStorage hotelManagementSystemStorage =
            new JsonHotelManagementSystemIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(hotelManagementSystemStorage, userPrefsStorage);
        logic = new LogicManager(customerModel, bookingModel, storage);

        // Execute add command

        String addCommand =
            AddCustomerCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + DATE_OF_BIRTH_DESC_AMY + EMAIL_DESC_AMY
                + ID_DESC_AMY + ADDRESS_DESC_AMY;

        Customer expectedCustomer = new CustomerBuilder(AMY).withTags().build();
        CustomerManager expectedModel = new CustomerManager();
        expectedModel.addCustomer(expectedCustomer);
        expectedModel.commitHotelManagementSystem();
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandBehavior(CommandException.class, addCommand, expectedMessage, expectedModel);
        assertHistoryCorrect(addCommand);
    }

    @Test
    public void getFilteredCustomerList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredCustomerList().remove(0);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     *
     * @see #assertCommandBehavior(Class, String, String, CustomerModel)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, CustomerModel expectedModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, CustomerModel)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, CustomerModel)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, CustomerModel)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage) {
        CustomerModel expectedModel =
            new CustomerManager(new VersionedHotelManagementSystem(customerModel.getHotelManagementSystem()),
                new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     * - the internal model manager data are same as those in the {@code expectedModel} <br>
     * - {@code expectedModel}'s hms book was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                       String expectedMessage, CustomerModel expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, customerModel);
    }

    /**
     * Asserts that the result display shows all the {@code expectedCommands} upon the execution of
     * {@code HistoryCommand}.
     */
    private void assertHistoryCorrect(String... expectedCommands) {
        try {
            CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                HistoryCommand.MESSAGE_SUCCESS, String.join("\n", expectedCommands));
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (ParseException | CommandException e) {

            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.",
                e);
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonHotelManagementSystemIoExceptionThrowingStub extends JsonHotelManagementSystemStorage {
        private JsonHotelManagementSystemIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveHotelManagementSystem(ReadOnlyHotelManagementSystem hotelManagementSystem, Path filePath)
            throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
