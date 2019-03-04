package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTables;
import seedu.address.model.order.ReadOnlyOrders;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.menu.ReadOnlyMenu;

/**
 * Manages storage of RestOrRant data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserPrefsStorage userPrefsStorage;
    private MenuStorage menuStorage;
    private OrdersStorage ordersStorage;
    private TableStorage tablesStorage;


    public StorageManager(UserPrefsStorage userPrefsStorage, OrdersStorage ordersStorage,
                          MenuStorage menuStorage, TableStorage tablesStorage) {
        super();
        this.menuStorage = menuStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.ordersStorage = ordersStorage;
        this.menuStorage = menuStorage;
        this.tablesStorage = tablesStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ Menu methods ==============================

    @Override
    public Path getMenuFilePath() {
        return menuStorage.getMenuFilePath();
    }

    @Override
    public Optional<ReadOnlyMenu> readMenu() throws DataConversionException, IOException {
        return readMenu(menuStorage.getMenuFilePath());
    }

    @Override
    public Optional<ReadOnlyMenu> readMenu(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return menuStorage.readMenu(filePath);
    }

    @Override
    public void saveMenu(ReadOnlyMenu menu) throws IOException {
        saveMenu(menu, menuStorage.getMenuFilePath());
    }

    @Override
    public void saveMenu(ReadOnlyMenu menu, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        menuStorage.saveMenu(menu, filePath);
    }

    @Override
    public void backupMenu(ReadOnlyMenu menu) throws IOException {
         menuStorage.backupMenu(menu);
    }

    // ================ Orders methods ==============================

    @Override
    public Path getOrdersFilePath() {
        return ordersStorage.getOrdersFilePath();
    }


    @Override
    public Optional<ReadOnlyOrders> readOrders() throws DataConversionException, IOException {
        return readOrders(ordersStorage.getOrdersFilePath());
    }

    @Override
    public Optional<ReadOnlyOrders> readOrders(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return ordersStorage.readOrders(filePath);
    }
    
    @Override
    public void saveOrders(ReadOnlyOrders orders) throws IOException {
        saveOrders(orders, ordersStorage.getOrdersFilePath());
    }

    @Override
    public void saveOrders(ReadOnlyOrders orders, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        ordersStorage.saveOrders(orders, filePath);
    }

    @Override
    public void backupOrders(ReadOnlyOrders orders) throws IOException {
        ordersStorage.backupOrders(orders);
    }

    // ============= Tables methods ===============================

    @Override
    public Path getTableFilePath() {
        return tablesStorage.getTableFilePath();
    }

    @Override
    public Optional<ReadOnlyTables> readTable() throws DataConversionException, IOException {
        return readTable(tablesStorage.getTableFilePath());
    }

    @Override
    public Optional<ReadOnlyTables> readTable(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read fata from file: " + filePath);
        return tablesStorage.readTable(filePath);
    }

    @Override
    public void saveTable(ReadOnlyTables tables) throws IOException {
        saveTable(tables, tablesStorage.getTableFilePath());
    }

    @Override
    public void saveTable(ReadOnlyTables tables, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        tablesStorage.saveTable(tables, filePath);
    }

    @Override
    public void backupTables(ReadOnlyTables tables) throws IOException {
        tablesStorage.backupTables(tables);
    }
}
