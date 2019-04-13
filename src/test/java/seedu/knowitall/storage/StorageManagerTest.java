package seedu.knowitall.storage;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.knowitall.testutil.TypicalCards.TYPICAL_FOLDER_ONE_NAME;
import static seedu.knowitall.testutil.TypicalCards.TYPICAL_FOLDER_TWO_NAME;
import static seedu.knowitall.testutil.TypicalCards.getEmptyCardFolder;
import static seedu.knowitall.testutil.TypicalCards.getTypicalFolderOne;
import static seedu.knowitall.testutil.TypicalCards.getTypicalFolderTwo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.knowitall.commons.core.GuiSettings;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.ReadOnlyCardFolder;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.testutil.TypicalCards;
import seedu.knowitall.testutil.TypicalIndexes;

public class StorageManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private File testDataFolder;

    private StorageManager storageManager;
    private List<CardFolderStorage> cardFolderStorageList;
    private UserPrefs userPrefs;
    private UserPrefsStorage userPrefsStorage;

    @Before
    public void setUp() throws IOException {
        userPrefs = new UserPrefs();
        userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        userPrefsStorage.saveUserPrefs(userPrefs);

        testDataFolder = testFolder.newFolder(userPrefs.getcardFolderFilesPath().toString());

        JsonCardFolderStorage cardFolderStorageOne =
                new JsonCardFolderStorage(getTempDataFilePath(TYPICAL_FOLDER_ONE_NAME));
        cardFolderStorageOne.saveCardFolder(TypicalCards.getTypicalFolderOne());
        cardFolderStorageList = new ArrayList<>();
        cardFolderStorageList.add(cardFolderStorageOne);
        storageManager = new StorageManager(cardFolderStorageList, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }

    private Path getTempDataFilePath(String fileName) {
        return getTempFilePath(Paths.get(testDataFolder.getPath(), fileName).toString());
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
    public void readCardFolders_nullList_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        storageManager.readCardFolders(null);
    }

    @Test
    public void saveCardFolder_readSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonCardFolderStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonCardFolderStorageTest} class.
         */
        CardFolder originalOne = getTypicalFolderOne();
        storageManager.saveCardFolder(originalOne, TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        List<ReadOnlyCardFolder> readFolders = new ArrayList<>();
        storageManager.readCardFolders(readFolders);
        ReadOnlyCardFolder retrievedOne = readFolders.get(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());;
        assertEquals(originalOne, retrievedOne);
        assertTrue(originalOne.hasSameCards(retrievedOne.getCardList()));
    }

    /**
     * This test relies on the {@code saveCardFolder()} method, which is tested in {@code saveCardFolder_readSave()}.
     */
    @Test
    public void saveCardFolders_readSave() throws Exception {
        CardFolder originalOne = getTypicalFolderOne();
        List<ReadOnlyCardFolder> savedFolders = new ArrayList<>();
        savedFolders.add(originalOne);

        // with one folder
        storageManager.saveCardFolders(savedFolders, testDataFolder.toPath());
        List<ReadOnlyCardFolder> readFolders = new ArrayList<>();
        storageManager.readCardFolders(readFolders);
        assertEquals(savedFolders, readFolders);

        // with two folders
        CardFolder originalTwo = getTypicalFolderTwo();
        savedFolders.add(originalTwo);
        storageManager.saveCardFolders(savedFolders, testDataFolder.toPath());
        storageManager.readCardFolders(readFolders);
        assertEquals(savedFolders, readFolders);
    }

    /**
     * This test relies on the {@code saveCardFolders()} method, which is tested in {@code saveCardFolders_readSave()}
     */
    @Test
    public void saveCardFolder_multipleFolders_correctOneSaved() throws Exception {
        CardFolder originalOne = getTypicalFolderOne();
        CardFolder originalTwo = getEmptyCardFolder();
        List<ReadOnlyCardFolder> savedFolders = new ArrayList<>();
        savedFolders.add(originalOne);
        savedFolders.add(originalTwo);
        storageManager.saveCardFolders(savedFolders, testDataFolder.toPath());

        CardFolder newTwo = getTypicalFolderTwo();
        storageManager.saveCardFolder(newTwo, TypicalIndexes.INDEX_SECOND_CARD_FOLDER.getZeroBased());
        List<ReadOnlyCardFolder> readFolders = new ArrayList<>();
        storageManager.readCardFolders(readFolders);
        ReadOnlyCardFolder retrievedTwo = readFolders.get(TypicalIndexes.INDEX_SECOND_CARD_FOLDER.getZeroBased());;
        assertEquals(newTwo, retrievedTwo);
        assertTrue(newTwo.hasSameCards(retrievedTwo.getCardList()));
    }

    @Test
    public void clearDirectory_pureDataDirectory_noExtraFilesIntroduced() throws IOException {
        JsonCardFolderStorage cardFolderStorageTwo =
                new JsonCardFolderStorage(getTempDataFilePath(TYPICAL_FOLDER_TWO_NAME));
        cardFolderStorageTwo.saveCardFolder(TypicalCards.getTypicalFolderTwo());
        cardFolderStorageList.add(cardFolderStorageTwo);
        storageManager = new StorageManager(cardFolderStorageList, userPrefsStorage);
        storageManager.saveCardFolders(new ArrayList<>(), testDataFolder.toPath());

        assertEquals(0, Objects.requireNonNull(new File(testDataFolder.toString()).list()).length);
    }

    @Test
    public void clearDirectory_impureDataDirectory_extraFilesNotDeleted() throws IOException {
        List<File> extraFiles = new ArrayList<>();
        extraFiles.add(new File(Paths.get(testDataFolder.toString(), "One").toString()));
        extraFiles.add(new File(Paths.get(testDataFolder.toString(), "Two").toString()));
        extraFiles.add(new File(Paths.get(testDataFolder.toString(), "Three").toString()));
        for (File file : extraFiles) {
            file.createNewFile();
        }
        int extraFileCount = extraFiles.size();

        storageManager.saveCardFolders(new ArrayList<>(), testDataFolder.toPath());

        assertEquals(extraFileCount, Objects.requireNonNull(new File(testDataFolder.toString()).list()).length);
    }

    @Test
    public void clearDirectory_noDataDirectory_notCalled() throws IOException {
        testFolder = new TemporaryFolder();
        Path testDataFolderPath = Paths.get(testFolder.toString(), userPrefs.getcardFolderFilesPath().toString());

        storageManager.saveCardFolders(new ArrayList<>(), testDataFolderPath);

        assertFalse(Files.exists(testDataFolderPath));
    }
}
