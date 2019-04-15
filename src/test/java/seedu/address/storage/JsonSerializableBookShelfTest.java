package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.BookShelf;
import seedu.address.testutil.TypicalBooks;

public class JsonSerializableBookShelfTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableBookShelfTest");
    private static final Path TYPICAL_BOOKS_FILE = TEST_DATA_FOLDER.resolve("typicalBooksBookShelf.json");
    private static final Path INVALID_BOOK_FILE = TEST_DATA_FOLDER.resolve("invalidBookBookShelf.json");
    private static final Path DUPLICATE_BOOK_FILE = TEST_DATA_FOLDER.resolve("duplicateBookBookShelf.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalBooksFile_success() throws Exception {
        JsonSerializableBookShelf dataFromFile = JsonUtil.readJsonFile(TYPICAL_BOOKS_FILE,
                JsonSerializableBookShelf.class).get();
        BookShelf bookShelfFromFile = dataFromFile.toModelType();
        BookShelf typicalBooksBookShelf = TypicalBooks.getTypicalBookShelf();
        assertEquals(bookShelfFromFile, typicalBooksBookShelf);
    }

    @Test
    public void toModelType_invalidBookFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBookShelf dataFromFile = JsonUtil.readJsonFile(INVALID_BOOK_FILE,
                JsonSerializableBookShelf.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateBooks_throwsIllegalValueException() throws Exception {
        JsonSerializableBookShelf dataFromFile = JsonUtil.readJsonFile(DUPLICATE_BOOK_FILE,
                JsonSerializableBookShelf.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableBookShelf.MESSAGE_DUPLICATE_BOOK);
        dataFromFile.toModelType();
    }

}
