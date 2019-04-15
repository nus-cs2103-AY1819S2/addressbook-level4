package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalEmployees.getTypicalPocketProjectWithEmployees;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.PocketProject;
import seedu.address.model.ReadOnlyPocketProject;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonPocketProjectStorage pocketProjectStorage = new JsonPocketProjectStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(pocketProjectStorage, userPrefsStorage);
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
    public void pocketProjectReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonPocketProjectStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonPocketProjectStorageTest} class.
         */
        PocketProject original = getTypicalPocketProjectWithEmployees();
        storageManager.savePocketProject(original);
        ReadOnlyPocketProject retrieved = storageManager.readPocketProject().get();
        assertEquals(original, new PocketProject(retrieved));
    }

    @Test
    public void getPocketProjectFilePath() {
        assertNotNull(storageManager.getPocketProjectFilePath());
    }

}
