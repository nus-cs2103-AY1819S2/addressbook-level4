package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonRequestBookStorage requestBookStorage = new JsonRequestBookStorage(getTempFilePath(
            "rb"));
        JsonHealthWorkerBookStorage healthWorkerBookStorage =
            new JsonHealthWorkerBookStorage(getTempFilePath("hb"));
        storageManager = new StorageManager(userPrefsStorage,
            requestBookStorage, healthWorkerBookStorage);
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

    //TODO: to be rewritten for all other books
//    @Test
//    public void addressBookReadSave() throws Exception {
//        /*
//         * Note: This is an integration test that verifies the StorageManager is properly wired to the
//         * {@link JsonAddressBookStorage} class.
//         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
//         */
//        AddressBook original = getTypicalAddressBook();
//        storageManager.saveAddressBook(original);
//        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
//        assertEquals(original, new AddressBook(retrieved));
//    }
//
//    @Test
//    public void getAddressBookFilePath() {
//        assertNotNull(storageManager.getAddressBookFilePath());
//    }

}
