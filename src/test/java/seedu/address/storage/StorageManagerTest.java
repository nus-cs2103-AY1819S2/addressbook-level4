package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCards.getTypicalCardFolder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.CardFolder;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonCardFolderStorage cardFolderStorage = new JsonCardFolderStorage(getTempFilePath("ab"));
        List<CardFolderStorage> cardFolderStorageList = new ArrayList<>();
        // TODO: Iterate over all files in directory
        cardFolderStorageList.add(cardFolderStorage);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(cardFolderStorageList, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the Storage Manager is properly wired to the
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
    public void cardFolderReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonCardFolderStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonCardFolderStorageTest} class.
         */
        CardFolder original = getTypicalCardFolder();
        List<ReadOnlyCardFolder> savedFolders = new ArrayList<>();
        savedFolders.add(original);
        storageManager.saveCardFolders(savedFolders, testFolder.getRoot().toPath());
        List<ReadOnlyCardFolder> readFolders = new ArrayList<>();
        storageManager.readCardFolders(readFolders);
        assertEquals(savedFolders, readFolders);

        storageManager.saveCardFolder(original, savedFolders.size() - 1);
        readFolders.clear();
        storageManager.readCardFolders(readFolders);
        ReadOnlyCardFolder retrieved = readFolders.get(savedFolders.size() - 1);
        assertEquals(original, retrieved);
    }
}
