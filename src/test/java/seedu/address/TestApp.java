package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
//import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.HealthWorkerBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyHealthWorkerBook;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.RequestBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonHealthWorkerBookStorage;
import seedu.address.storage.JsonRequestBookStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.testutil.TestUtil;
//import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING_HEALTHWORKERBOOK =
            TestUtil.getFilePathInSandboxFolder("healthWorkerData.json");
    public static final Path SAVE_LOCATION_FOR_TESTING_REQUESTBOOK =
            TestUtil.getFilePathInSandboxFolder("requestData.json");


    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyHealthWorkerBook> initialHealthWorkerBookSupplier = () -> null;
    protected Supplier<ReadOnlyRequestBook> initialRequestBookSupplier = () -> null;
    protected Path saveHealthWorkerBookLocation = SAVE_LOCATION_FOR_TESTING_HEALTHWORKERBOOK;
    protected Path saveRequestBookLocation = SAVE_LOCATION_FOR_TESTING_REQUESTBOOK;


    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyHealthWorkerBook> initialHealthWorkerBookSupplier, Path healthWorkerBookPath,
                   Supplier<ReadOnlyRequestBook> initialRequestBookSupplier, Path requestBookPath) {
        super();
        this.initialHealthWorkerBookSupplier = initialHealthWorkerBookSupplier;
        this.initialRequestBookSupplier = initialRequestBookSupplier;
        this.saveHealthWorkerBookLocation = healthWorkerBookPath;
        this.saveRequestBookLocation = requestBookPath;

        // If some initial local data has been provided, write those to the file
        if (initialHealthWorkerBookSupplier.get() != null) {
            JsonHealthWorkerBookStorage jsonHealthWorkerBookStorage= new JsonHealthWorkerBookStorage(healthWorkerBookPath);
            try {
                jsonHealthWorkerBookStorage.saveHealthWorkerBook(initialHealthWorkerBookSupplier.get());
            } catch (IOException ioe) {
                throw new AssertionError(ioe);
            }
        }
        if (initialRequestBookSupplier.get() != null) {
            JsonRequestBookStorage jsonRequestBookStorage= new JsonRequestBookStorage(requestBookPath);
            try {
                jsonRequestBookStorage.saveRequestBook(initialRequestBookSupplier.get());
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
        userPrefs.setHealthWorkerBookFilePath(saveHealthWorkerBookLocation);
        userPrefs.setRequestBookFilePath(saveRequestBookLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the address book data stored inside the storage file.
     */
    //TODO: implement requestBook and HealthworkerBook for tests
    //public AddressBook readStorageAddressBook() {
      //  try {
        //    return new AddressBook(storage.readAddressBook().get());
        //} catch (DataConversionException dce) {
         //   throw new AssertionError("Data is not in the AddressBook format.", dce);
        //} catch (IOException ioe) {
          //  throw new AssertionError("Storage file cannot be found.", ioe);
        //}
    //}

    /**
     * Returns the file path of the storage file.
     */
    //public Path getStorageSaveLocation() {
      //  return storage.getAddressBookFilePath();
    //}

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        // TODO: Fix HealthWorkerBook implementation
        Model copy = new ModelManager(new HealthWorkerBook(),
                new RequestBook(), new UserPrefs());
        //ModelHelper.setFilteredList(copy, model.getFilteredPersonList());
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
