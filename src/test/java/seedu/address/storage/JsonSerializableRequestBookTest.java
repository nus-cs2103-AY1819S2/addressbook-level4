package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.RequestBook;
import seedu.address.testutil.TypicalRequests;

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

//    @Test
//    void toModelType_typicalRequestsFile_success() throws Exception {
//        JsonSerializableRequestBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_REQUESTS_FILE,
//            JsonSerializableRequestBook.class).get();
//        RequestBook requestBookFromFile = dataFromFile.toModelType();
//        RequestBook typicalRequestsRequestBook = TypicalRequests.getTypicalRequestBook();
//        assertEquals(requestBookFromFile, typicalRequestsRequestBook);
//    }

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
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableRequestBook.MESSAGE_DUPLICATE_REQUEST);
    }
}
