package seedu.finance;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.finance.commons.core.Config;
import seedu.finance.commons.core.GuiSettings;
import seedu.finance.commons.exceptions.DataConversionException;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.ReadOnlyFinanceTracker;
import seedu.finance.model.UserPrefs;
import seedu.finance.storage.JsonFinanceTrackerStorage;
import seedu.finance.storage.UserPrefsStorage;
import seedu.finance.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */

public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.json");

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyFinanceTracker> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyFinanceTracker> initialDataSupplier, Path saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            JsonFinanceTrackerStorage jsonFinanceTrackerStorage = new JsonFinanceTrackerStorage(saveFileLocation);
            try {
                jsonFinanceTrackerStorage.saveFinanceTracker(initialDataSupplier.get());
            } catch (IOException ioe) {
                throw new AssertionError(ioe);
            }
        }
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
        userPrefs.setFinanceTrackerFilePath(saveFileLocation);
        return userPrefs;
    }


    /**
     * Returns a defensive copy of the finance tracker data stored inside the storage file.
     */

    public FinanceTracker readStorageFinanceTracker() {
        try {
            return new FinanceTracker(storage.readFinanceTracker().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the FinanceTracker format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }


    /**
     * Returns the file path of the storage file.
     */

    public Path getStorageSaveLocation() {
        return storage.getFinanceTrackerFilePath();
    }


    /**
     * Returns a defensive copy of the model.
     */

    public Model getModel() {
        Model copy = new ModelManager((model.getFinanceTracker()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredRecordList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
