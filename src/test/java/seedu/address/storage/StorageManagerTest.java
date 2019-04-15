package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.WarningPanelSettings;
import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonInventoryStorage inventoryStorage = new JsonInventoryStorage(getTempFilePath("inv"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(inventoryStorage, userPrefsStorage);
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
        original.setWarningPanelSettings(new WarningPanelSettings(300, 600));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void inventoryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonInventoryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonInventoryStorageTest} class.
         */
        Inventory original = getTypicalInventory();
        storageManager.saveInventory(original);
        ReadOnlyInventory retrieved = storageManager.readInventory().get();
        assertEquals(original, new Inventory(retrieved));
    }

    @Test
    public void getInventoryFilePath() {
        assertNotNull(storageManager.getInventoryFilePath());
    }

}
