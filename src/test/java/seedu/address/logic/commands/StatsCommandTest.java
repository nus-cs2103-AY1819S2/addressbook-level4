package seedu.address.logic.commands;

//import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.storage.JsonStatisticsStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

/**
 * Testing for StatsCommand.
 * @author bos10
 */
public class StatsCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableStatistics");
    private static final Path STATS_FILE = TEST_DATA_FOLDER.resolve("statsTest_statsCommand.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private Model model;
    private CommandHistory history;
    private LogicManager logic;

    @Before
    public void setUp() throws IOException {
        JsonStatisticsStorage statisticsStorage = new JsonStatisticsStorage(STATS_FILE);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(userPrefsStorage, statisticsStorage);
        model = new ModelManager();
        model.getPlayerStats().setStorage(storage);
        history = new CommandHistory();
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_success() throws CommandException {
        //StatsCommand statsCommand = new StatsCommand();
        //thrown.expect(ExceptionInInitializerError.class);
        //CommandResult commandResult = statsCommand.execute(model, history);
        //assertEquals(statsCommand.MESSAGE_SUCCESS, "CURRENT STATISTICS:\n%1$s");
    }
}
