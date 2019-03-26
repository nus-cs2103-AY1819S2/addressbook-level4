package seedu.hms;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.hms.commons.core.Config;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.commons.core.Version;
import seedu.hms.commons.exceptions.DataConversionException;
import seedu.hms.commons.util.ConfigUtil;
import seedu.hms.commons.util.StringUtil;
import seedu.hms.logic.Logic;
import seedu.hms.logic.LogicManager;
import seedu.hms.model.BookingManager;
import seedu.hms.model.BookingModel;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.Model;
import seedu.hms.model.ReadOnlyHotelManagementSystem;
import seedu.hms.model.ReadOnlyUserPrefs;
import seedu.hms.model.ReservationManager;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.util.SampleDataUtil;
import seedu.hms.storage.HotelManagementSystemStorage;
import seedu.hms.storage.JsonHotelManagementSystemStorage;
import seedu.hms.storage.JsonUserPrefsStorage;
import seedu.hms.storage.Storage;
import seedu.hms.storage.StorageManager;
import seedu.hms.storage.UserPrefsStorage;
import seedu.hms.ui.Ui;
import seedu.hms.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected CustomerModel customerModel;
    protected BookingModel bookingModel;
    protected ReservationModel reservationModel;
    protected Config config;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing HotelManagementSystem ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        HotelManagementSystemStorage hotelManagementSystemStorage =
            new JsonHotelManagementSystemStorage(userPrefs.getHotelManagementSystemFilePath());
        storage = new StorageManager(hotelManagementSystemStorage, userPrefsStorage);

        initLogging(config);

        Model[] models = initModelManager(storage, userPrefs);
        customerModel = (CustomerModel) models[0];
        bookingModel = (BookingModel) models[1];
        reservationModel = (ReservationModel) models[2];

        logic = new LogicManager(customerModel, bookingModel, reservationModel, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s hms book and {@code userPrefs}. <br>
     * The data from the sample hms book will be used instead if {@code storage}'s hms book is not found,
     * or an empty hms book will be used instead if errors occur when reading {@code storage}'s hms book.
     */
    private Model[] initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyHotelManagementSystem> hotelManagementSystemOptional;
        ReadOnlyHotelManagementSystem initialData;
        try {
            hotelManagementSystemOptional = storage.readHotelManagementSystem();
            if (!hotelManagementSystemOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample HotelManagementSystem");
            }
            initialData = hotelManagementSystemOptional.orElseGet(SampleDataUtil::getSampleHotelManagementSystem);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty HotelManagementSystem");
            initialData = new HotelManagementSystem();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty HotelManagementSystem");
            initialData = new HotelManagementSystem();
        }
        VersionedHotelManagementSystem versionedHotelManagementSystem = new VersionedHotelManagementSystem(initialData);
        Model[] result = {new CustomerManager(versionedHotelManagementSystem, userPrefs),
            new BookingManager(versionedHotelManagementSystem, userPrefs),
            new ReservationManager(versionedHotelManagementSystem, userPrefs) {
            }};
        return result;
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
            logger.warning("Problem while reading from the file. Will be starting with an empty HotelManagementSystem");
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
        logger.info("Starting HotelManagementSystem " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping hms Book ] =============================");
        try {
            storage.saveUserPrefs(customerModel.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
