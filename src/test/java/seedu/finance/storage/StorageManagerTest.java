package seedu.finance.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.finance.commons.core.GuiSettings;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.ReadOnlyFinanceTracker;
import seedu.finance.model.UserPrefs;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonFinanceTrackerStorage financeTrackerStorage = new JsonFinanceTrackerStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(financeTrackerStorage, userPrefsStorage);
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
    public void financeTrackerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonFinanceTrackerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonFinanceTrackerStorageTest} class.
         */
        FinanceTracker original = getTypicalFinanceTracker();
        storageManager.saveFinanceTracker(original);
        ReadOnlyFinanceTracker retrieved = storageManager.readFinanceTracker().get();
        assertEquals(original, new FinanceTracker(retrieved));
    }

    @Test
    public void getFinanceTrackerFilePath() {
        assertNotNull(storageManager.getFinanceTrackerFilePath());
    }


    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

    @Test
    public void setFinanceTrackerStorageTest() {
        JsonFinanceTrackerStorage financeTrackerStorageTest = new JsonFinanceTrackerStorage(getTempFilePath("test"));
        JsonUserPrefsStorage userPrefsStorageTest = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        StorageManager otherStorageManager = new StorageManager(financeTrackerStorageTest, userPrefsStorageTest);

        storageManager.setFinanceTrackerStorage(otherStorageManager);
        assertEquals(storageManager.getFinanceTrackerStorage(), otherStorageManager.getFinanceTrackerStorage());
    }
}
