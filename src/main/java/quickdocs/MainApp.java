package quickdocs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import quickdocs.commons.core.Config;
import quickdocs.commons.core.LogsCenter;
import quickdocs.commons.core.Version;
import quickdocs.commons.exceptions.DataConversionException;
import quickdocs.commons.util.ConfigUtil;
import quickdocs.commons.util.StringUtil;
import quickdocs.logic.Logic;
import quickdocs.logic.LogicManager;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;
import quickdocs.model.QuickDocs;
import quickdocs.model.ReadOnlyUserPrefs;
import quickdocs.model.UserPrefs;
import quickdocs.storage.JsonQuickDocsStorage;
import quickdocs.storage.JsonUserPrefsStorage;
import quickdocs.storage.QuickDocsStorage;
import quickdocs.storage.Storage;
import quickdocs.storage.StorageManager;
import quickdocs.storage.UserPrefsStorage;
import quickdocs.ui.RootLayoutController;
import quickdocs.ui.Ui;
import quickdocs.ui.UiManager;

/**
 *
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    // seedu.address.quickdocs attributes
    private Stage primaryStage;
    private AnchorPane rootLayout;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);

        QuickDocsStorage quickDocsStorage = new JsonQuickDocsStorage(userPrefs.getQuickDocsFilePath());
        storage = new StorageManager(userPrefsStorage, quickDocsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);
        model.updateFilteredReminderList(model.getCurrentWeekRemindersPredicate());

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {

        QuickDocs initialQuickDocs = new QuickDocs();
        Optional<QuickDocs> quickDocs;
        try {
            quickDocs = storage.readQuickDocs();
            if (!quickDocs.isPresent()) {
                logger.info("Data file not found. Will be starting with a empty quickdocs");
            }
            initialQuickDocs = quickDocs.orElse(initialQuickDocs);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty QuickDocs");
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty QuickDocs");
        }

        return new ModelManager(initialQuickDocs, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
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

    //@Override
    //public void start(Stage primaryStage) {
    //logger.info("Starting AddressBook " + MainApp.VERSION);
    //ui.start(primaryStage);
    //}

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    //Quickdocs codes

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("QuickDocs");
        this.primaryStage.setMinWidth(400);
        this.primaryStage.setMinHeight(300);
        Image image = new Image("images/quickdocsicon_512.png");
        this.primaryStage.getIcons().add(image);
        initRootLayout();
    }

    /**
     * Display the root layout when application is launched
     */
    public void initRootLayout() {


        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();

            //this will work when the rootlayout fxml file is in the resources folder but cannot link to controller
            rootLayout = loader.load(getClass().getClassLoader().getResourceAsStream("view/RootLayout.fxml"));
            RootLayoutController rootLayoutController = loader.getController();
            rootLayoutController.initialiseRootLayout(logic);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            rootLayoutController.setPrimaryStage(primaryStage);
            rootLayoutController.fillReminderList();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
