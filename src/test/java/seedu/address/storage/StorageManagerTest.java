package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.FoodDiary;
import seedu.address.model.ReadOnlyFoodDiary;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() throws Exception {
        JsonFoodDiaryStorage foodDiaryStorage = new JsonFoodDiaryStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonPostalDataStorage postalDataStorage = new JsonPostalDataStorage(getTempFilePath("data"));
        storageManager = new StorageManager(foodDiaryStorage, userPrefsStorage, postalDataStorage);
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
    public void foodDiaryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonFoodDiaryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonFoodDiaryStorageTest} class.
         */
        FoodDiary original = getTypicalFoodDiary();
        storageManager.saveFoodDiary(original);
        ReadOnlyFoodDiary retrieved = storageManager.readFoodDiary().get();
        assertEquals(original, new FoodDiary(retrieved));
    }

    @Test
    public void getFoodDiaryFilePath() {
        assertNotNull(storageManager.getFoodDiaryFilePath());
    }

}
