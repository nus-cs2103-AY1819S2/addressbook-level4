package seedu.address.model.flashcard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BACKFACE_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_HITBAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INDONESIAN;
import static seedu.address.testutil.TypicalFlashcards.EMAIL;
import static seedu.address.testutil.TypicalFlashcards.HELLO;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.FlashcardBuilder;

public class FlashcardTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Flashcard flashcard = new FlashcardBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        flashcard.getTags().remove(0);
    }

    @Test
    public void isSameFlashcard() {
        // same object -> returns true
        assertTrue(HELLO.isSameFlashcard(HELLO));

        // null -> returns false
        assertFalse(HELLO.isSameFlashcard(null));

        // different frontFace -> returns false
        Flashcard editedHello =
            new FlashcardBuilder(HELLO).withFrontFace(VALID_FRONTFACE_GOOD).build();
        assertFalse(HELLO.isSameFlashcard(editedHello));

        // different backFace -> returns false
        editedHello = new FlashcardBuilder(HELLO).withBackFace(VALID_BACKFACE_GOOD).build();
        assertFalse(HELLO.isSameFlashcard(editedHello));

        // same face, different tags -> returns true
        editedHello = new FlashcardBuilder(HELLO).withTags(VALID_TAG_INDONESIAN, VALID_TAG_CHINESE).build();
        assertTrue(HELLO.isSameFlashcard(editedHello));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Flashcard helloCopy = new FlashcardBuilder(HELLO).build();
        assertEquals(HELLO, helloCopy);

        // same object -> returns true
        assertEquals(HELLO, HELLO);

        // null -> returns false
        assertFalse(HELLO.equals(null));

        // different type -> returns false
        assertFalse(HELLO.equals(5));

        // different flashcard -> returns false
        assertFalse(HELLO.equals(EMAIL));

        // different frontFace -> returns false
        Flashcard editedHello = new FlashcardBuilder(HELLO).withFrontFace(VALID_FRONTFACE_HITBAG).build();
        assertFalse(HELLO.equals(editedHello));

        // different backFace -> returns false
        editedHello = new FlashcardBuilder(HELLO).withBackFace(VALID_BACKFACE_GOOD).build();
        assertFalse(HELLO.equals(editedHello));

        // different tags -> returns false
        editedHello = new FlashcardBuilder(HELLO).withTags(VALID_TAG_CHINESE, VALID_TAG_INDONESIAN).build();
        assertFalse(HELLO.equals(editedHello));
    }
}
