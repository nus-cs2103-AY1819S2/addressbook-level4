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
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.RestOrRant;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonMenuStorage;
import seedu.address.storage.JsonOrdersStorage;
import seedu.address.storage.JsonStatisticsStorage;
import seedu.address.storage.JsonTablesStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path TABLES_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleTables.json");
    public static final Path ORDERS_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleOrders.json");
    public static final Path MENU_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleMenu.json");
    public static final Path STATS_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleStats.json");

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyRestOrRant> initialDataSupplier = () -> null;
    protected Path tablesFileLocation = TABLES_FOR_TESTING;
    protected Path ordersFileLocation = ORDERS_FOR_TESTING;
    protected Path menuFileLocation = MENU_FOR_TESTING;
    protected Path statsFileLocation = STATS_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyRestOrRant> initialDataSupplier, Path tablesFileLocation,
                   Path ordersFileLocation, Path menuFileLocation, Path statsFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.tablesFileLocation = tablesFileLocation;
        this.ordersFileLocation = ordersFileLocation;
        this.menuFileLocation = menuFileLocation;
        this.statsFileLocation = statsFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            JsonTablesStorage jsonTablesStorage = new JsonTablesStorage(tablesFileLocation);
            JsonOrdersStorage jsonOrdersStorage = new JsonOrdersStorage(ordersFileLocation);
            JsonMenuStorage jsonMenuStorage = new JsonMenuStorage(menuFileLocation);
            JsonStatisticsStorage jsonStatsStorage = new JsonStatisticsStorage(statsFileLocation);
            try {
                ReadOnlyRestOrRant restOrRant = initialDataSupplier.get();
                jsonTablesStorage.saveTables(restOrRant.getTables());
                jsonOrdersStorage.saveOrders(restOrRant.getOrders());
                jsonMenuStorage.saveMenu(restOrRant.getMenu());
                jsonStatsStorage.saveStatistics(restOrRant.getStatistics());
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
        userPrefs.setTablesFilePath(tablesFileLocation);
        userPrefs.setOrdersFilePath(ordersFileLocation);
        userPrefs.setMenuFilePath(menuFileLocation);
        userPrefs.setStatisticsFilePath(statsFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the RestOrRant data stored inside the storage files.
     */
    public RestOrRant readStorageRestOrRant() {
        try {
            return new RestOrRant(storage.readOrders().get(), storage.readMenu().get(),
                    storage.readTables().get(), storage.readStatistics().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the RestOrRant format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the tables storage file.
     */
    public Path getTableStorageLocation() {
        return storage.getTableFilePath();
    }

    /**
     * Returns the file path of the orders storage file.
     */
    public Path getOrdersStorageLocation() {
        return storage.getOrdersFilePath();
    }

    /**
     * Returns the file path of the menu storage file.
     */
    public Path getMenuStorageLocation() {
        return storage.getMenuFilePath();
    }

    /**
     * Returns the file path of the statistics storage file.
     */
    public Path getStatisticsStorageLocation() {
        return storage.getStatisticsFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getRestOrRant()), new UserPrefs());
        ModelHelper.setOrderItemFilteredList(copy, model.getFilteredOrderItemList());
        ModelHelper.setMenuItemFilteredList(copy, model.getFilteredMenuItemList());
        ModelHelper.setTableFilteredList(copy, model.getFilteredTableList());
        ModelHelper.setRevenueFilteredList(copy, model.getFilteredRevenueList());
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
