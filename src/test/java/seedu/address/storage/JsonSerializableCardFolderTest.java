package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.CardFolder;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableCardFolderTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableCardFolderTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsCardFolder.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonCardFolder.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicateCardCardFolder.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableCardFolder dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableCardFolder.class).get();
        CardFolder cardFolderFromFile = dataFromFile.toModelType();
        CardFolder typicalPersonsCardFolder = TypicalPersons.getTypicalCardFolder();
        assertEquals(cardFolderFromFile, typicalPersonsCardFolder);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCardFolder dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableCardFolder.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableCardFolder dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableCardFolder.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableCardFolder.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
