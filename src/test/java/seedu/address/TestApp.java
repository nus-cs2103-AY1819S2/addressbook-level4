package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PocketProject;
import seedu.address.model.ReadOnlyPocketProject;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonPocketProjectStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.json");

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyPocketProject> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyPocketProject> initialDataSupplier, Path saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            JsonPocketProjectStorage jsonPocketProjectStorage = new JsonPocketProjectStorage(saveFileLocation);
            try {
                jsonPocketProjectStorage.savePocketProject(initialDataSupplier.get());
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
        userPrefs.setPocketProjectFilePath(saveFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the pocket project data stored inside the storage file.
     */
    public PocketProject readStoragePocketProject() {
        try {
            return new PocketProject(storage.readPocketProject().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the PocketProject format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getPocketProjectFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getPocketProject()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredEmployeeList());
        ModelHelper.setProjectFilteredList(copy, model.getFilteredProjectList());
        return copy;
    }

    /**
     * Returns a defensive copy of the model with project list.
     */
    public Model getProjectModel() {
        Model copy = new ModelManager((model.getPocketProject()), new UserPrefs());
        ModelHelper.setProjectFilteredList(copy, model.getFilteredProjectList());
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
