package seedu.knowitall.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.knowitall.commons.exceptions.IllegalValueException;
import seedu.knowitall.commons.util.JsonUtil;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.testutil.TypicalCards;

public class JsonSerializableCardFolderTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableCardFolderTest");
    private static final Path TYPICAL_CARDS_FILE = TEST_DATA_FOLDER.resolve("typicalCardsCardFolder.json");
    private static final Path INVALID_CARD_FILE = TEST_DATA_FOLDER.resolve("invalidCardCardFolder.json");
    private static final Path DUPLICATE_CARD_FILE = TEST_DATA_FOLDER.resolve("duplicateThumbnailCardFolder.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalCardsFile_success() throws Exception {
        JsonSerializableCardFolder dataFromFile = JsonUtil.readJsonFile(TYPICAL_CARDS_FILE,
                JsonSerializableCardFolder.class).get();
        CardFolder cardFolderFromFile = dataFromFile.toModelType();
        CardFolder typicalCardsCardFolder = TypicalCards.getTypicalCardFolder();
        assertEquals(cardFolderFromFile, typicalCardsCardFolder);
    }

    @Test
    public void toModelType_invalidCardFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCardFolder dataFromFile = JsonUtil.readJsonFile(INVALID_CARD_FILE,
                JsonSerializableCardFolder.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateCards_throwsIllegalValueException() throws Exception {
        JsonSerializableCardFolder dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CARD_FILE,
                JsonSerializableCardFolder.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableCardFolder.MESSAGE_DUPLICATE_CARD);
        dataFromFile.toModelType();
    }

}
