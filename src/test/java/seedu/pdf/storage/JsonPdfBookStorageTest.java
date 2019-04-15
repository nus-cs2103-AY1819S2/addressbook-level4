package seedu.pdf.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_3;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_6;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_7;
import static seedu.pdf.testutil.TypicalPdfs.getTypicalPdfBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.pdf.commons.exceptions.DataConversionException;
import seedu.pdf.model.PdfBook;
import seedu.pdf.model.ReadOnlyPdfBook;

public class JsonPdfBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonPdfBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readPdfBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readPdfBook(null);
    }

    private java.util.Optional<ReadOnlyPdfBook> readPdfBook(String filePath) throws Exception {
        return new JsonPdfBookStorage(Paths.get(filePath)).readPdfBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPdfBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readPdfBook("notJsonFormatPdfBook.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readPdfBook_invalidPdfPdfBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readPdfBook("invalidPdfPdfBook.json");
    }

    @Test
    public void readPdfBook_invalidAndValidPdfPdfBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readPdfBook("invalidAndValidPdfPdfBook.json");
    }

    @Test
    public void readAndSavePdfBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempPdfPlusPlus.json");
        PdfBook original = getTypicalPdfBook();
        JsonPdfBookStorage jsonPdfBookStorage = new JsonPdfBookStorage(filePath);

        // Save in new file and read back
        jsonPdfBookStorage.savePdfBook(original, filePath);
        ReadOnlyPdfBook readBack = jsonPdfBookStorage.readPdfBook(filePath).get();
        assertEquals(original, new PdfBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPdf(SAMPLE_PDF_7);
        original.removePdf(SAMPLE_PDF_3);
        jsonPdfBookStorage.savePdfBook(original, filePath);
        readBack = jsonPdfBookStorage.readPdfBook(filePath).get();
        assertEquals(original, new PdfBook(readBack));

        // Save and read without specifying file value
        original.addPdf(SAMPLE_PDF_6);
        jsonPdfBookStorage.savePdfBook(original); // file value not specified
        readBack = jsonPdfBookStorage.readPdfBook().get(); // file value not specified
        assertEquals(original, new PdfBook(readBack));

    }

    @Test
    public void savePdfBook_nullPdfBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePdfBook(null, "SomeFile.json");
    }

    /**
     * Saves {@code pdfBook} at the specified {@code filePath}.
     */
    private void savePdfBook(ReadOnlyPdfBook pdfBook, String filePath) {
        try {
            new JsonPdfBookStorage(Paths.get(filePath))
                    .savePdfBook(pdfBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePdfBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePdfBook(new PdfBook(), null);
    }
}
