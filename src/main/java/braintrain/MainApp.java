package braintrain;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import braintrain.commons.core.Config;
import braintrain.commons.core.LogsCenter;
import braintrain.commons.core.Version;
import braintrain.commons.exceptions.DataConversionException;
import braintrain.commons.util.ConfigUtil;
import braintrain.commons.util.StringUtil;
import braintrain.logic.Logic;
import braintrain.logic.LogicManager;
import braintrain.model.Lessons;
import braintrain.model.Model;
import braintrain.model.ModelManager;
import braintrain.model.ReadOnlyUserPrefs;
import braintrain.model.UserPrefs;
import braintrain.quiz.QuizModel;
import braintrain.quiz.QuizModelManager;
import braintrain.storage.CsvLessonImportExport;
import braintrain.storage.CsvLessonsStorage;
import braintrain.storage.JsonUserPrefsStorage;
import braintrain.storage.LessonImportExport;
import braintrain.storage.LessonsStorage;
import braintrain.storage.Storage;
import braintrain.storage.StorageManager;
import braintrain.storage.UserPrefsStorage;
import braintrain.ui.Ui;
import braintrain.ui.UiManager;

import javafx.application.Application;
import javafx.stage.Stage;
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
        Lessons lessons = initLessons(lessonsStorage);
        LessonImportExport lessonImportExport = new CsvLessonImportExport(userPrefs.getLessonImportExportFilePath());
        storage = new StorageManager(userPrefsStorage, lessonsStorage, lessonImportExport);

        initLogging(config);

        model = initModelManager(userPrefs, lessons);
        quizModel = initQuizModelManager();

        logic = new LogicManager(model, quizModel);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code userPrefs}.
     */
    private Model initModelManager(ReadOnlyUserPrefs userPrefs, Lessons lessons) {
        return new ModelManager(userPrefs, lessons);
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
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected Lessons initLessons(LessonsStorage storage) {
        Path lessonsFolderPath = storage.getLessonsFolderPath();
        logger.info("Using lessons folder : " + lessonsFolderPath);

        Lessons initializedLessons = null;
        try {
            Optional<Lessons> prefsOptional = storage.readLessons();
            initializedLessons = prefsOptional.orElse(new Lessons());
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with a empty BrainTrain");
            initializedLessons = new Lessons();
        }
        /*
        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        */

        logger.info(initializedLessons.getLessons().size() + " lessons loaded.");
        return initializedLessons;
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
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
