package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.HealthWorkerBook;
import seedu.address.testutil.TypicalHealthWorkers;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.storage.JsonSerializableHealthWorkerBook;

public class JsonSerializableHealthWorkerBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src",
            "test", "data", "JsonSerializableHealthWorkerBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve(
            "typicalhealthworkersbook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve(
            "invalidhealthworkerbook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve(
            "duplicatehealthworkerbook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalHealthworkersFile_success() throws Exception {
        JsonSerializableHealthWorkerBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableHealthWorkerBook.class).get();
        HealthWorkerBook addressBookFromFile = dataFromFile.toModelType();
        HealthWorkerBook typicalHealthWorkersHealthWorkerBook = TypicalHealthWorkers.getTypicalHealthWorkerBook();
        assertEquals(addressBookFromFile, typicalHealthWorkersHealthWorkerBook);
    }

    @Test
    public void toModelType_invalidHealthWorkerFile_throwsIllegalValueException() throws Exception {
        JsonSerializableHealthWorkerBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableHealthWorkerBook.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateHealthWorkers_throwsIllegalValueException() throws Exception {
        JsonSerializableHealthWorkerBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableHealthWorkerBook.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableHealthWorkerBook.MESSAGE_DUPLICATE_HEALTHWORKER);
        dataFromFile.toModelType();
    }

}
