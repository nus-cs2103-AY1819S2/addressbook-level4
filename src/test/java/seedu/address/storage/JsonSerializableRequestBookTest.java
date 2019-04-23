package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.RequestBook;
import seedu.address.model.request.exceptions.DuplicateRequestException;

class JsonSerializableRequestBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
        "data", "JsonSerializableRequestBookTest");
    private static final Path TYPICAL_REQUESTS_FILE = TEST_DATA_FOLDER.resolve
        ("typicalRequestsBook.json");
    private static final Path INVALID_REQUESTS_FILE = TEST_DATA_FOLDER.resolve
        ("invalidRequestBook.json");
    private static final Path DUPLICATE_REQUESTS_FILE = TEST_DATA_FOLDER
        .resolve("duplicateRequestBook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalRequestsFile_success() throws Exception {
        JsonSerializableRequestBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_REQUESTS_FILE,
             JsonSerializableRequestBook.class).get();
        RequestBook requestBookFromFile = dataFromFile.toModelType();
        RequestBook typicalRequestsRequestBook = getTypicalRequestBook();
        assertEquals(typicalRequestsRequestBook, requestBookFromFile);
    }

    @Test
    public void toModelType_invalidRequestFile_throwsIllegalValueException() throws Exception {
        JsonUtil.readJsonFile(INVALID_REQUESTS_FILE,
            JsonSerializableRequestBook.class).get();
        thrown.expect(IllegalValueException.class);
    }

    @Test
    public void toModelType_duplicateRequest_throwsIllegalValueException() throws Exception {
        JsonUtil.readJsonFile(DUPLICATE_REQUESTS_FILE,
            JsonSerializableRequestBook.class).get();
        thrown.expect(DuplicateRequestException.class);
        thrown.expectMessage(JsonSerializableRequestBook.MESSAGE_DUPLICATE_REQUEST);
    }
}
