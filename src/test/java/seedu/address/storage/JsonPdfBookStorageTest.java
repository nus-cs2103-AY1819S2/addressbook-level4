package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_1;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_2;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_3;
import static seedu.address.testutil.TypicalPdfs.getTypicalPdfBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.PdfBook;
import seedu.address.model.ReadOnlyPdfBook;

public class JsonPdfBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPdfBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyPdfBook> readAddressBook(String filePath) throws Exception {
        return new JsonPdfBookStorage(Paths.get(filePath)).readPdfBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("notJsonFormatPdfBook.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidPdfPdfBook.json");
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidPdfPdfBook.json");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.json");
        PdfBook original = getTypicalPdfBook();
        JsonPdfBookStorage jsonPdfBookStorage = new JsonPdfBookStorage(filePath);

        // Save in new file and read back
        jsonPdfBookStorage.savePdfBook(original, filePath);
        ReadOnlyPdfBook readBack = jsonPdfBookStorage.readPdfBook(filePath).get();
        assertEquals(original, new PdfBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPdf(SAMPLE_PDF_1);
        original.removePdf(SAMPLE_PDF_3);
        jsonPdfBookStorage.savePdfBook(original, filePath);
        readBack = jsonPdfBookStorage.readPdfBook(filePath).get();
        assertEquals(original, new PdfBook(readBack));

        // Save and read without specifying file value
        original.addPdf(SAMPLE_PDF_2);
        jsonPdfBookStorage.savePdfBook(original); // file value not specified
        readBack = jsonPdfBookStorage.readPdfBook().get(); // file value not specified
        assertEquals(original, new PdfBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.json");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyPdfBook addressBook, String filePath) {
        try {
            new JsonPdfBookStorage(Paths.get(filePath))
                    .savePdfBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new PdfBook(), null);
    }
}
