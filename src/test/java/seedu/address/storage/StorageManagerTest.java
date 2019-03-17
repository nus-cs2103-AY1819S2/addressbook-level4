package seedu.hms.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.hms.commons.core.GuiSettings;
import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.ReadOnlyHotelManagementSystem;
import seedu.hms.model.UserPrefs;
//import seedu.hms.model.VersionedHotelManagementSystem;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonHotelManagementSystemStorage hotelManagementSystemStorage =
            new JsonHotelManagementSystemStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(hotelManagementSystemStorage, userPrefsStorage);
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
    public void hotelManagementSystemReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonHotelManagementSystemStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonHotelManagementSystemStorageTest}
         * class.
         */
        HotelManagementSystem original = getTypicalHotelManagementSystem();
        storageManager.saveHotelManagementSystem(original);
        ReadOnlyHotelManagementSystem retrieved = storageManager.readHotelManagementSystem().get();
        assertEquals(original, new HotelManagementSystem(retrieved));
    }

    @Test
    public void getHotelManagementSystemFilePath() {
        assertNotNull(storageManager.getHotelManagementSystemFilePath());
    }

}
