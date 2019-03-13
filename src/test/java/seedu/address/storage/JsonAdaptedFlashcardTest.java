package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedFlashcard.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalFlashcards.GOOD;
import static seedu.address.testutil.TypicalFlashcards.HELLO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.flashcard.Face;
import seedu.address.testutil.Assert;

public class JsonAdaptedFlashcardTest {
    private static final String INVALID_FRONTFACE = " ";
    private static final String INVALID_BACKFACE = " ";
    private static final String INVALID_TAG = "#lol";

    private static final String VALID_FRONTFACE = HELLO.getFrontFace().text;
    private static final String VALID_BACKFACE = HELLO.getBackFace().text;
    private static final List<JsonAdaptedTag> VALID_TAGS = HELLO.getTags().stream()
        .map(JsonAdaptedTag::new)
        .collect(Collectors.toList());

    @Test
    public void toModelType_validFlashcardDetails_returnsFlashcard() throws Exception {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(GOOD);
        assertEquals(GOOD, flashcard.toModelType());
    }

    @Test
    public void toModelType_invalidFontFace_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard =
            new JsonAdaptedFlashcard(INVALID_FRONTFACE, VALID_BACKFACE, VALID_TAGS);
        String expectedMessage = Face.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullFrontFace_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(null, VALID_BACKFACE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Face.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidBackFace_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard =
            new JsonAdaptedFlashcard(VALID_FRONTFACE, INVALID_BACKFACE, VALID_TAGS);
        String expectedMessage = Face.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullBackFace_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_FRONTFACE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Face.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedFlashcard flashcard =
            new JsonAdaptedFlashcard(VALID_FRONTFACE, VALID_BACKFACE, invalidTags);
        Assert.assertThrows(IllegalValueException.class, flashcard::toModelType);
    }

}
