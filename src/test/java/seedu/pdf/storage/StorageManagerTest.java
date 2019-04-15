package seedu.pdf.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.pdf.testutil.TypicalPdfs.getTypicalPdfBook;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.pdf.commons.core.GuiSettings;
import seedu.pdf.model.PdfBook;
import seedu.pdf.model.ReadOnlyPdfBook;
import seedu.pdf.model.UserPrefs;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonPdfBookStorage pdfBookStorage = new JsonPdfBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(pdfBookStorage, userPrefsStorage);
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
    public void pdfBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonPdfBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonPdfBookStorageTest} class.
         */
        PdfBook original = getTypicalPdfBook();
        storageManager.savePdfBook(original);
        ReadOnlyPdfBook retrieved = storageManager.readPdfBook().get();
        assertEquals(original, new PdfBook(retrieved));
    }

    @Test
    public void getPdfBookFilePath() {
        assertNotNull(storageManager.getPdfBookFilePath());
    }

}
