package seedu.address;

import static seedu.address.logic.commands.Statistics.undoRedoStatistics;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.HealthWorkerBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyHealthWorkerBook;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.RequestBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.HealthWorkerBookStorage;
import seedu.address.storage.JsonHealthWorkerBookStorage;
import seedu.address.storage.JsonRequestBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.RequestBookStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Health Hub ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        RequestBookStorage requestBookStorage = new JsonRequestBookStorage(userPrefs.getRequestBookFilePath());
        HealthWorkerBookStorage healthWorkerBookStorage = new JsonHealthWorkerBookStorage(
                userPrefs.getHealthWorkerBookFilePath());
        storage = new StorageManager(userPrefsStorage, requestBookStorage,
                healthWorkerBookStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        undoRedoStatistics(model);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address
     * book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if
     * {@code storage}'s address book is not found, or an empty address book will be
     * used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyHealthWorkerBook> healthWorkerBookOptional;
        Optional<ReadOnlyRequestBook> requestBookOptional;
        ReadOnlyHealthWorkerBook initialHealthWorkerBook;
        ReadOnlyRequestBook initialRequestBook;

        try {

            healthWorkerBookOptional = storage.readHealthWorkerBook();
            requestBookOptional = storage.readRequestBook();

            if (!requestBookOptional.isPresent()) {
                logger.info("RequestBook file not found. Will be starting with sample RequestBook");
            }
            if (!healthWorkerBookOptional.isPresent()) {
                logger.info("HealthWorkerBook file not found. Will be starting with a sample HealthWorkerBook");
            }
            initialRequestBook = requestBookOptional.orElseGet(SampleDataUtil::getSampleRequestBook);
            initialHealthWorkerBook = healthWorkerBookOptional.orElseGet(SampleDataUtil::getSampleHealthWorkerBook);


        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with empty books");
            initialHealthWorkerBook = new HealthWorkerBook();
            initialRequestBook = new RequestBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with empty books");
            initialHealthWorkerBook = new HealthWorkerBook();
            initialRequestBook = new RequestBook();
        }


        return new ModelManager(initialHealthWorkerBook, initialRequestBook, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        // Update config file in case it was missing to begin with or there are
        // new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs
     * file path, or a new {@code UserPrefs} with default configuration if errors
     * occur when reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty books");
            initializedPrefs = new UserPrefs();
        }

        // Update prefs file in case it was missing to begin with or there are
        // new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Health Hub " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Health Hub ] =============================");
        try {
            storage.saveHealthWorkerBook(model.getHealthWorkerBook());
            storage.saveRequestBook(model.getRequestBook());
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        } finally {
            // to fix timer task not stopping
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
