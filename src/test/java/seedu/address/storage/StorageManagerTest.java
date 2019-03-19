package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.RestOrRant;
import seedu.address.model.UserPrefs;
import seedu.address.model.menu.ReadOnlyMenu;
import seedu.address.model.order.ReadOnlyOrders;
import seedu.address.model.statistics.ReadOnlyStatistics;
import seedu.address.model.table.ReadOnlyTables;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonMenuStorage menuStorage = new JsonMenuStorage(getTempFilePath("menu"));
        JsonOrdersStorage ordersStorage = new JsonOrdersStorage(getTempFilePath("orders"));
        JsonStatisticsStorage statisticsStorage = new JsonStatisticsStorage(getTempFilePath("stats"));
        JsonTablesStorage tablesStorage = new JsonTablesStorage(getTempFilePath("tables"));
        //  JsonRestOrRantStorage restOrRantStorage = new JsonRestOrRantStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(userPrefsStorage, ordersStorage, menuStorage,
                statisticsStorage, tablesStorage);
        //  storageManager = new StorageManager(restOrRantStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void restOrRantReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonMenuStorage}, {@link JsonOrdersStorage}, {@link JsonTablesStorage}, {@link JsonStatisticsStorage}
         * classes.
         * More extensive testing of UserPref saving/reading is done in {@link JsonMenuStorageTest},
         * {@link JsonOrdersStorageTest}, {@link JsonTablesStorageTest}, {@link JsonStatisticsStorageTest} classes.
         */

        RestOrRant original = getTypicalRestOrRant();
        storageManager.saveMenu(original.getMenu());
        storageManager.saveOrders(original.getOrders());
        storageManager.saveTables(original.getTables());
        storageManager.saveStatistics(original.getStatistics());

        ReadOnlyMenu retrievedMenu = storageManager.readMenu().get();
        ReadOnlyOrders retrievedOrders = storageManager.readOrders().get();
        ReadOnlyTables retrievedTables = storageManager.readTables().get();
        ReadOnlyStatistics retrievedStatistics = storageManager.readStatistics().get();
        assertEquals(original, new RestOrRant(retrievedOrders, retrievedMenu, retrievedTables, retrievedStatistics));
    }

    @Test
    public void getMenuFilePath() {
        assertNotNull(storageManager.getMenuFilePath());
    }

    @Test
    public void getOrdersFilePath() {
        assertNotNull(storageManager.getOrdersFilePath());
    }

    @Test
    public void getTableFilePath() {
        assertNotNull(storageManager.getTableFilePath());
    }

    @Test
    public void getStatisticsFilePath() {
        assertNotNull(storageManager.getStatisticsFilePath());
    }

}
