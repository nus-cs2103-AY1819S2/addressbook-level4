package seedu.finance.logic;

import static org.junit.Assert.assertEquals;
import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX;
import static seedu.finance.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.finance.logic.commands.CommandTestUtil.AMOUNT_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.CATEGORY_DESC_FRIEND;
import static seedu.finance.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static seedu.finance.testutil.TypicalRecords.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.finance.logic.commands.CommandResult;
import seedu.finance.logic.commands.HistoryCommand;
import seedu.finance.logic.commands.ListCommand;
import seedu.finance.logic.commands.SpendCommand;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.logic.parser.exceptions.ParseException;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.ReadOnlyFinanceTracker;
import seedu.finance.model.UserPrefs;
import seedu.finance.model.record.Record;
import seedu.finance.storage.JsonFinanceTrackerStorage;
import seedu.finance.storage.JsonUserPrefsStorage;
import seedu.finance.storage.StorageManager;
import seedu.finance.testutil.RecordBuilder;


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
        JsonFinanceTrackerStorage financeTrackerStorage = new JsonFinanceTrackerStorage(
                temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(financeTrackerStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistoryCorrect(invalidCommand);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        assertHistoryCorrect(deleteCommand);
    }

    @Test
    public void execute_validCommand_success() {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listCommand);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() throws Exception {
        // Setup LogicManager with JsonFinanceTrackerIoExceptionThrowingStub
        JsonFinanceTrackerStorage financeTrackerStorage =
                new JsonFinanceTrackerIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(financeTrackerStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String spendCommand = SpendCommand.COMMAND_WORD + NAME_DESC_AMY + AMOUNT_DESC_AMY + DATE_DESC_AMY
                + CATEGORY_DESC_FRIEND + DESCRIPTION_DESC_AMY;
        Record expectedRecord = new RecordBuilder(AMY).withCategory(VALID_CATEGORY_FRIEND).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addRecord(expectedRecord);
        expectedModel.commitFinanceTracker();
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandBehavior(CommandException.class, spendCommand, expectedMessage, expectedModel);
        assertHistoryCorrect(spendCommand);
    }

    @Test
    public void getFilteredRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredRecordList().remove(0);
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
        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s finance tracker was saved to the storage file.
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

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonFinanceTrackerIoExceptionThrowingStub extends JsonFinanceTrackerStorage {
        private JsonFinanceTrackerIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveFinanceTracker(ReadOnlyFinanceTracker financeTracker, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
