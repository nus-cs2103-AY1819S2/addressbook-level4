package seedu.hms;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.hms.commons.core.Config;
import seedu.hms.commons.core.GuiSettings;
import seedu.hms.commons.exceptions.DataConversionException;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.ReadOnlyHotelManagementSystem;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.storage.JsonHotelManagementSystemStorage;
import seedu.hms.storage.UserPrefsStorage;
import seedu.hms.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.json");

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
        TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyHotelManagementSystem> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyHotelManagementSystem> initialDataSupplier, Path saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            JsonHotelManagementSystemStorage jsonHotelManagementSystemStorage =
                new JsonHotelManagementSystemStorage(saveFileLocation);
            try {
                jsonHotelManagementSystemStorage.saveHotelManagementSystem(initialDataSupplier.get());
            } catch (IOException ioe) {
                throw new AssertionError(ioe);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.setGuiSettings(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setHotelManagementSystemFilePath(saveFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the hms book data stored inside the storage file.
     */
    public HotelManagementSystem readStorageHotelManagementSystem() {
        try {
            return new HotelManagementSystem(storage.readHotelManagementSystem().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the HotelManagementSystem format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getHotelManagementSystemFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public CustomerModel getModel() {
        CustomerModel copy =
            new CustomerManager(new VersionedHotelManagementSystem(customerModel.getHotelManagementSystem()),
                new UserPrefs());
        ModelHelper.setFilteredList(copy, customerModel.getFilteredCustomerList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

}
