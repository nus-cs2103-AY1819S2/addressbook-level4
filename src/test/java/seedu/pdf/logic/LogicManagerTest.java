package seedu.pdf.logic;

import static org.junit.Assert.assertEquals;
import static seedu.pdf.commons.core.Messages.MESSAGE_INVALID_PDF_DISPLAYED_INDEX;
import static seedu.pdf.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.pdf.logic.commands.CommandTestUtil.FILEPATH_1_VALID;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_1;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.pdf.logic.commands.AddCommand;
import seedu.pdf.logic.commands.CommandResult;
import seedu.pdf.logic.commands.HistoryCommand;
import seedu.pdf.logic.commands.ListCommand;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.logic.parser.exceptions.ParseException;
import seedu.pdf.model.Model;
import seedu.pdf.model.ModelManager;
import seedu.pdf.model.ReadOnlyPdfBook;
import seedu.pdf.model.UserPrefs;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.storage.JsonPdfBookStorage;
import seedu.pdf.storage.JsonUserPrefsStorage;
import seedu.pdf.storage.StorageManager;
import seedu.pdf.testutil.PdfBuilder;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");
    private static final String DEADLINE_NEWLY_ADDED_FILE = "NEWLY ADDED";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private Model model = new ModelManager();
    private Logic logic;

    @Before
    public void setUp() throws Exception {
        JsonPdfBookStorage pdfBookStorage = new JsonPdfBookStorage(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(pdfBookStorage, userPrefsStorage);
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
        assertCommandException(deleteCommand, MESSAGE_INVALID_PDF_DISPLAYED_INDEX);
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
        // Setup LogicManager with JsonPdfBookIoExceptionThrowingStub
        JsonPdfBookStorage pdfBookStorage =
                new JsonPdfBookIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(pdfBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + " " + PREFIX_FILE + FILEPATH_1_VALID;
        Pdf expectedPdf = new PdfBuilder(SAMPLE_PDF_1).withTags().withDeadline(DEADLINE_NEWLY_ADDED_FILE).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPdf(expectedPdf);
        expectedModel.commitPdfBook();
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandBehavior(CommandException.class, addCommand, expectedMessage, expectedModel);
        assertHistoryCorrect(addCommand);
    }

    @Test
    public void getFilteredPdfList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredPdfList().remove(0);
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
        Model expectedModel = new ModelManager(model.getPdfBook(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s pdf book was saved to the storage file.
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
    private static class JsonPdfBookIoExceptionThrowingStub extends JsonPdfBookStorage {
        private JsonPdfBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void savePdfBook(ReadOnlyPdfBook pdfBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
