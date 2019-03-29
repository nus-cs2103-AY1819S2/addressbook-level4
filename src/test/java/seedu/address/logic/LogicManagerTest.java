package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.ORGANIZATION_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.SKILLS_DESC_ANDY;
import static seedu.address.logic.parser.CommandMode.MODE_HEALTHWORKER;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.request.ListRequestCommand;
import seedu.address.logic.parser.DeleteCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyHealthWorkerBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.storage.JsonHealthWorkerBookStorage;
import seedu.address.storage.JsonRequestBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.Assert;
import seedu.address.testutil.HealthWorkerBuilder;

//import java.nio.file.Path;

//import seedu.address.logic.parser.DeleteCommandParser;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private Model model = new ModelManager();
    private Logic logic;

    @Before
    public void setUp() throws Exception {
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        JsonRequestBookStorage requestBookStorage =
            new JsonRequestBookStorage(temporaryFolder.newFile().toPath());
        JsonHealthWorkerBookStorage jsonHealthWorkerBookStorage =
            new JsonHealthWorkerBookStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(userPrefsStorage,
            requestBookStorage, jsonHealthWorkerBookStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "wnfookdasd";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistoryCorrect(invalidCommand);
    }

     @Test
     public void execute_commandExecutionError_throwsParseException() {
        String deleteCommand = "delete 9";
        assertParseException(deleteCommand, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommandParser.INVALID_COMMAND_USAGE));
        assertHistoryCorrect(deleteCommand);
     }

    @Test
    public void execute_validCommand_success() {
        String listCommand = ListCommand.COMMAND_WORD + " request";
        assertCommandSuccess(listCommand, ListRequestCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listCommand);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() throws Exception {
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        JsonRequestBookStorage requestBookStorage =
            new JsonRequestBookStorage(temporaryFolder.newFile().toPath());
        JsonHealthWorkerBookStorage jsonHealthWorkerBookStorage =
            new JsonHealthWorkerBookIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(userPrefsStorage,
            requestBookStorage, jsonHealthWorkerBookStorage);
        logic = new LogicManager(model, storage);

        String addHealthWorkerCommand = AddCommand.COMMAND_WORD + " " + MODE_HEALTHWORKER + NAME_DESC_ANDY
                + PHONE_DESC_ANDY + NRIC_DESC_ANDY + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY;
        HealthWorker expectedHealthWorker = new HealthWorkerBuilder(ANDY).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addHealthWorker(expectedHealthWorker);
        expectedModel.commitHealthWorkerBook();
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandBehavior(CommandException.class, addHealthWorkerCommand, expectedMessage, expectedModel);
        assertHistoryCorrect(addHealthWorkerCommand);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredHealthWorkerList().remove(0));
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage) {
        Model expectedModel = new ModelManager(model.getHealthWorkerBook(),
            model.getRequestBook(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s address book was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                           String expectedMessage, Model expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
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
            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.", e);
        }
    }

    //private static class JsonAddressBookIoExceptionThrowingStub extends JsonAddressBookStorage {
    //  private JsonAddressBookIoExceptionThrowingStub(Path filePath) {
    //    super(filePath);
    // }
    //@Override
    // public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
    //  throw DUMMY_IO_EXCEPTION;
    // }
    //}

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonHealthWorkerBookIoExceptionThrowingStub extends JsonHealthWorkerBookStorage {
        private JsonHealthWorkerBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveHealthWorkerBook(ReadOnlyHealthWorkerBook healthWorkerBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
