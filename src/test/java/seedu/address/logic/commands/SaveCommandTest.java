package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.storage.JsonStatisticsStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

/**
 * The StatsCommandTest will test the methods of the statistics class.
 * Contains integration tests (interaction with Model, Player, Storage and Battleship commands)
 */
public class SaveCommandTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableStatistics");
    private static final Path STATS_FILE = TEST_DATA_FOLDER.resolve("statsTest.json");
    private static final Path STATS_FILE_2 = TEST_DATA_FOLDER.resolve("statsTestBetter.json");
    private static final Path STATS_FILE_3 = TEST_DATA_FOLDER.resolve("statsTest2.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private Model model;
    private CommandHistory history;
    private LogicManager logic;

    public void setUp_same() throws Exception {
        JsonStatisticsStorage statisticsStorage = new JsonStatisticsStorage(STATS_FILE_3);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(userPrefsStorage, statisticsStorage);
        model = new ModelManager();
        model.getPlayerStats().setStorage(storage);
        history = new CommandHistory();
        logic = new LogicManager(model, storage);
    }

    public void setUp_better() throws Exception {
        JsonStatisticsStorage statisticsStorage = new JsonStatisticsStorage(STATS_FILE);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(userPrefsStorage, statisticsStorage);
        model = new ModelManager();
        model.getPlayerStats().setStorage(storage);
        history = new CommandHistory();
        logic = new LogicManager(model, storage);
    }

    public void setUp_worst() throws Exception {
        JsonStatisticsStorage statisticsStorage = new JsonStatisticsStorage(STATS_FILE_2);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(userPrefsStorage, statisticsStorage);
        model = new ModelManager();
        model.getPlayerStats().setStorage(storage);
        history = new CommandHistory();
        logic = new LogicManager(model, storage);
    }

    @Test
    public void getAccuracy_success() {
        assertEquals(0, (int) new SaveCommand().getAccuracy(0, 0));
        assertEquals(1, (int) new SaveCommand().getAccuracy(1, 0));
    }

    @Test
    public void execute_better_success() throws Exception {
        setUp_better();
        SaveCommand saveCommand = new SaveCommand();
        model.getPlayerStats().addHit();
        CommandResult commandResult = saveCommand.execute(model, history);

        assertEquals(String.format(SaveCommand.MESSAGE_SUCCESS_BETTER
                + '\n'
                + "Current Game : %.1f%%", model.getPlayerStats().getAccuracy() * 100)
                + '\n'
                + "Previous Game : 0.0%", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_worst_success() throws Exception {
        setUp_worst();
        SaveCommand saveCommand = new SaveCommand();
        model.getPlayerStats().addHit();
        CommandResult commandResult = saveCommand.execute(model, history);

        assertEquals(String.format(SaveCommand.MESSAGE_SUCCESS_BETTER
                + '\n'
                + "Current Game : %.1f%%", model.getPlayerStats().getAccuracy() * 100)
                + '\n'
                + "Previous Game : 33.3%", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_same_success() throws Exception {
        setUp_same();
        SaveCommand saveCommand = new SaveCommand();
        CommandResult commandResult = saveCommand.execute(model, history);
        assertEquals(SaveCommand.MESSAGE_SUCCESS_SAME
                + '\n'
                + "Current Game : 0.0", commandResult.getFeedbackToUser());
    }
}
