package seedu.address.model.flashcard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INDONESIAN;
import static seedu.address.testutil.TypicalFlashcards.GOOD;
import static seedu.address.testutil.TypicalFlashcards.HITBAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.flashcard.exceptions.DuplicateFlashcardException;
import seedu.address.model.flashcard.exceptions.FlashcardNotFoundException;
import seedu.address.testutil.FlashcardBuilder;

public class UniqueFlashcardListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final UniqueFlashcardList uniqueFlashcardList = new UniqueFlashcardList();

    @Test
    public void contains_nullFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.contains(null);
    }

    @Test
    public void contains_flashcardNotInList_returnsFalse() {
        assertFalse(uniqueFlashcardList.contains(GOOD));
    }

    @Test
    public void contains_flashcardInList_returnsTrue() {
        uniqueFlashcardList.add(GOOD);
        assertTrue(uniqueFlashcardList.contains(GOOD));
    }

    @Test
    public void contains_flashcardWithSameIdentityFieldsInList_returnsTrue() {
        uniqueFlashcardList.add(GOOD);
        Flashcard editedGood = new FlashcardBuilder(GOOD).withTags(VALID_TAG_INDONESIAN).build();
        assertTrue(uniqueFlashcardList.contains(editedGood));
    }

    @Test
    public void add_nullFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.add(null);
    }

    @Test
    public void add_duplicateFlashcard_throwsDuplicateFlashcardException() {
        uniqueFlashcardList.add(GOOD);
        thrown.expect(DuplicateFlashcardException.class);
        uniqueFlashcardList.add(GOOD);
    }

    @Test
    public void setFlashcard_nullTargetFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.setFlashcard(null, GOOD);
    }

    @Test
    public void setFlashcard_nullEditedFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.setFlashcard(GOOD, null);
    }

    @Test
    public void setFlashcard_targetFlashcardNotInList_throwsFlashcardNotFoundException() {
        thrown.expect(FlashcardNotFoundException.class);
        uniqueFlashcardList.setFlashcard(GOOD, GOOD);
    }

    @Test
    public void setFlashcard_editedFlashcardIsSameFlashcard_success() {
        uniqueFlashcardList.add(GOOD);
        uniqueFlashcardList.setFlashcard(GOOD, GOOD);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(GOOD);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcard_editedFlashcardHasSameIdentity_success() {
        uniqueFlashcardList.add(GOOD);
        Flashcard editedAlice = new FlashcardBuilder(GOOD).withTags(VALID_TAG_CHINESE)
            .build();
        uniqueFlashcardList.setFlashcard(GOOD, editedAlice);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(editedAlice);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcard_editedFlashcardHasDifferentIdentity_success() {
        uniqueFlashcardList.add(GOOD);
        uniqueFlashcardList.setFlashcard(GOOD, HITBAG);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(HITBAG);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcard_editedFlashcardHasNonUniqueIdentity_throwsDuplicateFlashcardException() {
        uniqueFlashcardList.add(GOOD);
        uniqueFlashcardList.add(HITBAG);
        thrown.expect(DuplicateFlashcardException.class);
        uniqueFlashcardList.setFlashcard(GOOD, HITBAG);
    }

    @Test
    public void remove_nullFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.remove(null);
    }

    @Test
    public void remove_flashcardDoesNotExist_throwsFlashcardNotFoundException() {
        thrown.expect(FlashcardNotFoundException.class);
        uniqueFlashcardList.remove(GOOD);
    }

    @Test
    public void remove_existingFlashcard_removesFlashcard() {
        uniqueFlashcardList.add(GOOD);
        uniqueFlashcardList.remove(GOOD);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcards_nullUniqueFlashcardList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.setFlashcards((UniqueFlashcardList) null);
    }

    @Test
    public void setFlashcards_uniqueFlashcardList_replacesOwnListWithProvidedUniqueFlashcardList() {
        uniqueFlashcardList.add(GOOD);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(HITBAG);
        uniqueFlashcardList.setFlashcards(expectedUniqueFlashcardList);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcards_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFlashcardList.setFlashcards((List<Flashcard>) null);
    }

    @Test
    public void setFlashcards_list_replacesOwnListWithProvidedList() {
        uniqueFlashcardList.add(GOOD);
        List<Flashcard> flashcardList = Collections.singletonList(HITBAG);
        uniqueFlashcardList.setFlashcards(flashcardList);
        UniqueFlashcardList expectedUniqueFlashcardList = new UniqueFlashcardList();
        expectedUniqueFlashcardList.add(HITBAG);
        assertEquals(expectedUniqueFlashcardList, uniqueFlashcardList);
    }

    @Test
    public void setFlashcards_listWithDuplicateFlashcards_throwsDuplicateFlashcardException() {
        List<Flashcard> listWithDuplicateFlashcards = Arrays.asList(GOOD, GOOD);
        thrown.expect(DuplicateFlashcardException.class);
        uniqueFlashcardList.setFlashcards(listWithDuplicateFlashcards);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueFlashcardList.asUnmodifiableObservableList().remove(0);
    }
}
