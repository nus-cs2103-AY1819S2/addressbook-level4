package seedu.equipment.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalEquipmentManager;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.equipment.commons.core.GuiSettings;
import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.ReadOnlyEquipmentManager;
import seedu.equipment.model.UserPrefs;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonEquipmentManagerStorage equipmentManagerStorage = new JsonEquipmentManagerStorage(
                getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(equipmentManagerStorage, userPrefsStorage);
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
    public void equipmentManagerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonEquipmentManagerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonEquipmentManagerStorageTest} class.
         */
        EquipmentManager original = getTypicalEquipmentManager();
        storageManager.saveEquipmentManager(original);
        ReadOnlyEquipmentManager retrieved = storageManager.readEquipmentManager().get();
        assertEquals(original, new EquipmentManager(retrieved));
    }

    @Test
    public void getEquipmentManagerFilePath() {
        assertNotNull(storageManager.getEquipmentManagerFilePath());
    }

}
