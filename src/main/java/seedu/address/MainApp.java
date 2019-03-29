package seedu.address;

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
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.model.user.User;
import seedu.address.storage.CsvLessonsStorage;
import seedu.address.storage.CsvUserStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.LessonsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.UserStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected ManagementModel managementModel;
    protected QuizModel quizModel;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing BrainTrain ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        LessonsStorage lessonsStorage = new CsvLessonsStorage(userPrefs.getLessonsFolderPath());
        LessonList lessonList = initLessons(lessonsStorage);
        UserStorage userStorage = new CsvUserStorage(userPrefs.getUserFilePath());
        User user = initUser(userStorage);
        storage = new StorageManager(userPrefsStorage, lessonsStorage, userStorage);

        initLogging(config);

        managementModel = initModelManager(userPrefs, lessonList, user);
        quizModel = initQuizModelManager();

        logic = new LogicManager(managementModel, quizModel, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ManagementModelManager} with the data from {@code userPrefs}.
     */
    private ManagementModel initModelManager(ReadOnlyUserPrefs userPrefs, LessonList lessonList, User user) {
        return new ManagementModelManager(userPrefs, lessonList, user);
    }

    /**
     * Returns an empty {@code QuizModelManager}.
     */
    private QuizModelManager initQuizModelManager() {
        return new QuizModelManager();
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
            logger.warning("Problem while reading from the file. Will be starting with a empty BrainTrain");
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


    /**
     * Returns a {@code LessonList} using the folder at {@code storage}'s LessonList folder path,
     * or a new {@code LessonList} with no initial lessons if errors occur when
     * reading from the file.
     */
    protected LessonList initLessons(LessonsStorage storage) {
        Path lessonsFolderPath = storage.getLessonsFolderPath();
        logger.info("Using lessons folder : " + lessonsFolderPath);

        LessonList initializedLessonList = null;
        Optional<LessonList> prefsOptional = storage.readLessons();
        initializedLessonList = prefsOptional.orElse(new LessonList());

        logger.info(initializedLessonList.getLessons().size() + " lessons loaded.");
        return initializedLessonList;
    }

    /**
     * Returns a {@code User} using the file at {@code storage}'s user file path,
     * or a new {@code User} with empty data if errors occur when reading from the file.
     */
    protected User initUser(UserStorage storage) {
        Path userFilePath = storage.getUserFilePath();
        logger.info("Using user data folder : " + userFilePath);

        User initializedUser = null;
        Optional<User> prefsOptional = storage.readUser();
        initializedUser = prefsOptional.orElse(new User());

        logger.info("User data successfully loaded " + initializedUser.getCards().size() + " cards.");
        return initializedUser;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting BrainTrain " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping BrainTrain ] =============================");
        try {
            storage.saveUserPrefs(managementModel.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
