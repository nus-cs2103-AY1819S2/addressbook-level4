package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.PdfBook;
import seedu.address.testutil.TypicalPdfs;

public class JsonSerializablePdfBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializablePdfBookTest");
    private static final Path TYPICAL_PDF_FILE = TEST_DATA_FOLDER.resolve("typicalPdfPdfBook.json");
    private static final Path INVALID_PDF_FILE = TEST_DATA_FOLDER.resolve("invalidPdfPdfBook.json");
    private static final Path DUPLICATE_PDF_FILE = TEST_DATA_FOLDER.resolve("duplicatePdfPdfBook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPdfFile_success() throws Exception {
        JsonSerializablePdfBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PDF_FILE,
                JsonSerializablePdfBook.class).get();
        PdfBook addressBookFromFile = dataFromFile.toModelType();
        PdfBook typicalPersonsAddressBook = TypicalPdfs.getTypicalPdfBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPdfFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePdfBook dataFromFile = JsonUtil.readJsonFile(INVALID_PDF_FILE,
                JsonSerializablePdfBook.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePdfs_throwsIllegalValueException() throws Exception {
        JsonSerializablePdfBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PDF_FILE,
                JsonSerializablePdfBook.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializablePdfBook.MESSAGE_DUPLICATE_PDF);
        dataFromFile.toModelType();
    }

}
