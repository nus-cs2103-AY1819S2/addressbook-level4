package seedu.address;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javax.xml.crypto.Data;

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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.CardFolderStorage;
import seedu.address.storage.JsonCardFolderStorage;
import seedu.address.storage.JsonUserPrefsStorage;
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
        logger.info("=============================[ Initializing CardFolder ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        List<CardFolderStorage> cardFolderStorageList = new ArrayList<>();

        Path cardFolderFilesPath = userPrefs.getcardFolderFilesPath();

        boolean withSample = false;
        if (Files.isDirectory(cardFolderFilesPath)) {
            Stream<Path> stream = Files.walk(cardFolderFilesPath);
            if (stream != null) {
                stream.filter(Files::isRegularFile)
                        .forEach(file -> cardFolderStorageList.add(new JsonCardFolderStorage(file)));
            }
        }
        if (cardFolderStorageList.isEmpty()) {
            logger.info("Folders not found. Will be starting with a sample CardFolder");
            Path samplePath = cardFolderFilesPath.resolve(SampleDataUtil.getSampleFolderFileName());
            cardFolderStorageList.add(new JsonCardFolderStorage(samplePath));
            withSample = true;
        }

        storage = new StorageManager(cardFolderStorageList, userPrefsStorage);

        initLogging(config);

        if (withSample) {
            model = initModelManagerWithSample(userPrefs);
        } else {
            model = initModelManager(storage, userPrefs);
        }

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s card folder and {@code userPrefs}. <br>
     * All folders in valid formats that are found will be read. If none are found, the data from the sample card folder
     * will be used instead.
     */
    Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        List<ReadOnlyCardFolder> initialCardFolders;
        initialCardFolders = new ArrayList<>();

        // read all valid card folders
        try {
            storage.readCardFolders(initialCardFolders);
        } catch (Exception e) {
            if (e instanceof DataConversionException) {
                logger.warning("Data file not in the correct format.");
            } else if (e instanceof IOException) {
                logger.warning("Problem while reading from the file.");
            } else {
                logger.warning("Unknown error while reading from file.");
            }
        }

        // if no card folder is valid, then start with a sample one.
        if (initialCardFolders.isEmpty()) {
            logger.warning("No CardFolders read. Will be starting with a sample CardFolder");
            return initModelManagerWithSample(userPrefs);
        }

        return new ModelManager(initialCardFolders, userPrefs);
    }

    /**
     * Returns a {@code ModelManager} with data from the sample card folder.
     */
    Model initModelManagerWithSample(ReadOnlyUserPrefs userPrefs) {
        List<ReadOnlyCardFolder> sampleCardFolders = new ArrayList<>();
        sampleCardFolders.add(SampleDataUtil.getSampleCardFolder());

        return new ModelManager(sampleCardFolders, userPrefs);
    }

    void initLogging(Config config) {
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

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
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
            logger.warning("Problem while reading from the file. Will be starting with an empty CardFolder");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting CardFolder " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping card folder ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
