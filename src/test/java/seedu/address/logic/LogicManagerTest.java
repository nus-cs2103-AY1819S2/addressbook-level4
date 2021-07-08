package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.battle.state.BattleState;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.StatsCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.statistics.PlayerStatistics;
import seedu.address.storage.JsonStatisticsStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;


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
        JsonStatisticsStorage statisticsStorage = new JsonStatisticsStorage(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(userPrefsStorage, statisticsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void addToStatistics_success() {
        PlayerStatistics newPlayerStats = new PlayerStatistics();
        newPlayerStats.addAttack();
        newPlayerStats.addMove();
        logic.addToStatistics("attack");
        assertEquals(newPlayerStats.getAttacksMade(), model.getPlayerStats().getAttacksMade());
        assertEquals(newPlayerStats.getMovesMade(), model.getPlayerStats().getMovesMade());
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        //assertHistoryCorrect(invalidCommand);
        // To modify this for testing Statistics Command
    }

    @Test
    public void execute_validCommand_success() {
        String listCommand = ListCommand.COMMAND_WORD;
        model.setBattleState(BattleState.PLAYER_PUT_SHIP);
        assertCommandSuccess(listCommand, "No ships put down.", model);
        //assertHistoryCorrect(listCommand); BOSTON: TO FIX
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() throws Exception {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        //JsonStatisticsStorage statisticsStorage = new JsonStatisticsStorage(temporaryFolder.newFile().toPath());
        //JsonAddressBookStorage addressBookStorage =
        //        new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        //JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        //StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, statisticsStorage);
        //logic = new LogicManager(model, storage);
        //
        //// Execute add command
        //String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
        //        + ADDRESS_DESC_AMY;
        //Cell expectedCell = new PersonBuilder(AMY).withTags().build();
        //ModelManager expectedModel = new ModelManager();
        //expectedModel.addPerson(expectedCell);
        //expectedModel.commitAddressBook();
        //String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        //assertCommandBehavior(CommandException.class, addCommand, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        //thrown.expect(UnsupportedOperationException.class);
        //logic.getFilteredPersonList().remove(0);
    }

    @Test
    public void getMapsTest() {
        assertEquals(logic.getEnemyMapGrid(), model.getEnemyMapGrid());
        assertEquals(logic.getHumanMapGrid(), model.getHumanMapGrid());
    }

    @Test
    public void getObservablesTest() {
        assertEquals(logic.getEnemyMapObservable(), model.getEnemyMapObservable());
        assertEquals(logic.getHumanMapObservable(), model.getHumanMapObservable());
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
        Model expectedModel = new ModelManager(model.getHumanMapGrid(), new UserPrefs());
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
     * {@code StatsCommand}.
     */
    private void assertHistoryCorrect(String... expectedCommands) {
        try {
            CommandResult result = logic.execute(StatsCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                    StatsCommand.MESSAGE_SUCCESS, String.join("\n", expectedCommands));
            assertEquals(expectedMessage, result.getFeedbackToUser()); //.split(" ")[0]);
        } catch (ParseException | CommandException e) {
            throw new AssertionError("Parsing and execution of StatsCommand.COMMAND_WORD should succeed.", e);
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonStatisticsIoExceptionThrowingStub extends JsonStatisticsStorage {
        private JsonStatisticsIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveStatisticsData(PlayerStatistics playerStats, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
