package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_HITBAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static seedu.address.testutil.TypicalFlashcards.GOOD;
import static seedu.address.testutil.TypicalFlashcards.HITBAG;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.FlashcardContainsKeywordsPredicate;
import seedu.address.model.flashcard.exceptions.FlashcardNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CardCollectionBuilder;
import seedu.address.testutil.FlashcardBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new CardCollection(), new CardCollection(modelManager.getCardCollection()));
        assertEquals(null, modelManager.getSelectedFlashcard());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setCardCollectionFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setCardCollectionFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setCardCollectionFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setCardCollectionFilePath(null);
    }

    @Test
    public void setCardCollectionFilePath_validPath_setsCardCollectionFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setCardCollectionFilePath(path);
        assertEquals(path, modelManager.getCardCollectionFilePath());
    }

    @Test
    public void hasFlashcard_nullFlashcard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasFlashcard(null);
    }

    @Test
    public void hasFlashcard_flashcardNotInCardCollection_returnsFalse() {
        assertFalse(modelManager.hasFlashcard(GOOD));
    }

    @Test
    public void hasFlashcard_flashcardInCardCollection_returnsTrue() {
        modelManager.addFlashcard(GOOD);
        assertTrue(modelManager.hasFlashcard(GOOD));
    }

    @Test
    public void deleteFlashcard_flashcardIsSelectedAndFirstFlashcardInFilteredFlashcardList_selectionCleared() {
        modelManager.addFlashcard(GOOD);
        modelManager.setSelectedFlashcard(GOOD);
        modelManager.deleteFlashcard(GOOD);
        assertEquals(null, modelManager.getSelectedFlashcard());
    }

    @Test
    public void deleteFlashcard_flashcardIsSelectedAndSecondFlashcardInFilteredFlashcardList_firstFlashcardSelected() {
        modelManager.addFlashcard(GOOD);
        modelManager.addFlashcard(HITBAG);
        assertEquals(Arrays.asList(GOOD, HITBAG), modelManager.getFilteredFlashcardList());
        modelManager.setSelectedFlashcard(HITBAG);
        modelManager.deleteFlashcard(HITBAG);
        assertEquals(GOOD, modelManager.getSelectedFlashcard());
    }

    @Test
    public void setFlashcard_flashcardIsSelected_selectedFlashcardUpdated() {
        modelManager.addFlashcard(GOOD);
        modelManager.setSelectedFlashcard(GOOD);
        Flashcard updatedAlice = new FlashcardBuilder(GOOD).withFrontFace(VALID_FRONTFACE_HITBAG).build();
        modelManager.setFlashcard(GOOD, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedFlashcard());
    }

    @Test
    public void getFilteredFlashcardList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredFlashcardList().remove(0);
    }

    @Test
    public void setSelectedFlashcard_flashcardNotInFilteredFlashcardList_throwsFlashcardNotFoundException() {
        thrown.expect(FlashcardNotFoundException.class);
        modelManager.setSelectedFlashcard(GOOD);
    }

    @Test
    public void setSelectedFlashcard_flashcardInFilteredFlashcardList_setsSelectedFlashcard() {
        modelManager.addFlashcard(GOOD);
        assertEquals(Collections.singletonList(GOOD), modelManager.getFilteredFlashcardList());
        modelManager.setSelectedFlashcard(GOOD);
        assertEquals(GOOD, modelManager.getSelectedFlashcard());
    }

    @Test
    public void equals() {
        CardCollection cardCollection = new CardCollectionBuilder().withFlashcard(GOOD).withFlashcard(HITBAG).build();
        CardCollection differentCardCollection = new CardCollection();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(cardCollection, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(cardCollection, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different cardCollection -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentCardCollection, userPrefs)));

        // different filteredList -> returns false
        String[] frontFaceKeywords = GOOD.getFrontFace().text.split("\\s+");
        String[] backFaceKeywords = GOOD.getBackFace().text.split("\\s+");
        Set<Tag> tagSet = GOOD.getTags();
        ArrayList<String> tagKeywords = new ArrayList<>();
        for (Tag tag : tagSet) {
            tagKeywords.add(tag.tagName);
        }
        modelManager.updateFilteredFlashcardList(new FlashcardContainsKeywordsPredicate(
                Arrays.asList(frontFaceKeywords), Arrays.asList(backFaceKeywords), tagKeywords));
        assertFalse(modelManager.equals(new ModelManager(cardCollection, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setCardCollectionFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(cardCollection, differentUserPrefs)));
    }
}
