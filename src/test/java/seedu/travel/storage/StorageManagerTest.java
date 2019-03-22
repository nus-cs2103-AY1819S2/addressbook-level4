package seedu.travel.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.travel.testutil.TypicalPlaces.getTypicalTravelBuddy;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.travel.commons.core.GuiSettings;
import seedu.travel.model.ReadOnlyTravelBuddy;
import seedu.travel.model.TravelBuddy;
import seedu.travel.model.UserPrefs;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonTravelBuddyStorage addressBookStorage = new JsonTravelBuddyStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
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
    public void travelBuddyReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonTravelBuddyStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonTravelBuddyStorageTest} class.
         */
        TravelBuddy original = getTypicalTravelBuddy();
        storageManager.saveTravelBuddy(original);
        ReadOnlyTravelBuddy retrieved = storageManager.readTravelBuddy().get();
        assertEquals(original, new TravelBuddy(retrieved));
    }

    @Test
    public void getTravelBuddyFilePath() {
        assertNotNull(storageManager.getTravelBuddyFilePath());
    }

}
