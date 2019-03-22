package seedu.travel.logic;

import static org.junit.Assert.assertEquals;
import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX;
import static seedu.travel.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.travel.logic.commands.CommandTestUtil.ADDRESS_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.COUNTRY_CODE_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.DESCRIPTION_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.NAME_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.RATING_DESC_AMK;
import static seedu.travel.testutil.TypicalPlaces.AMK;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.travel.logic.commands.AddCommand;
import seedu.travel.logic.commands.CommandResult;
import seedu.travel.logic.commands.HistoryCommand;
import seedu.travel.logic.commands.ListCommand;
import seedu.travel.logic.commands.exceptions.CommandException;
import seedu.travel.logic.parser.exceptions.ParseException;
import seedu.travel.model.Model;
import seedu.travel.model.ModelManager;
import seedu.travel.model.ReadOnlyTravelBuddy;
import seedu.travel.model.UserPrefs;
import seedu.travel.model.place.Place;
import seedu.travel.storage.JsonTravelBuddyStorage;
import seedu.travel.storage.JsonUserPrefsStorage;
import seedu.travel.storage.StorageManager;
import seedu.travel.testutil.PlaceBuilder;


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
        JsonTravelBuddyStorage travelBuddyStorage = new JsonTravelBuddyStorage(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(travelBuddyStorage, userPrefsStorage);
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
        assertCommandException(deleteCommand, MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
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
        // Setup LogicManager with JsonTravelBuddyIoExceptionThrowingStub
        JsonTravelBuddyStorage travelBuddyStorage =
                new JsonTravelBuddyIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(travelBuddyStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand =
            AddCommand.COMMAND_WORD + NAME_DESC_AMK + COUNTRY_CODE_DESC_AMK + RATING_DESC_AMK + DESCRIPTION_AMK
                + ADDRESS_DESC_AMK;
        Place expectedPlace = new PlaceBuilder(AMK).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPlace(expectedPlace);
        expectedModel.commitTravelBuddy();
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandBehavior(CommandException.class, addCommand, expectedMessage, expectedModel);
        assertHistoryCorrect(addCommand);
    }

    @Test
    public void getFilteredPlaceList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredPlaceList().remove(0);
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
        Model expectedModel = new ModelManager(model.getTravelBuddy(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s travel book was saved to the storage file.
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
    private static class JsonTravelBuddyIoExceptionThrowingStub extends JsonTravelBuddyStorage {
        private JsonTravelBuddyIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveTravelBuddy(ReadOnlyTravelBuddy travelBuddy, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
