package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DECKS;
import static seedu.address.testutil.TypicalCards.ADDITION;
import static seedu.address.testutil.TypicalCards.SUBTRACTION;
import static seedu.address.testutil.TypicalDecks.DECK_A;
import static seedu.address.testutil.TypicalDecks.DECK_B;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CardsView;
import seedu.address.model.deck.DeckNameContainsKeywordsPredicate;
import seedu.address.model.deck.exceptions.CardNotFoundException;
import seedu.address.model.deck.exceptions.IllegalOperationWhileReviewingDeckException;
import seedu.address.testutil.TopDeckBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new TopDeck(), modelManager.getTopDeck());
        assertEquals(null, modelManager.getSelectedItem());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setTopDeckFilePath(Paths.get("topdeck/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setTopDeckFilePath(Paths.get("new/topdeck/file/path"));
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
    public void setTopDeckFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setTopDeckFilePath(null);
    }

    @Test
    public void setTopDeckFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("topdeck/file/path");
        modelManager.setTopDeckFilePath(path);
        assertEquals(path, modelManager.getTopDeckFilePath());
    }

    @Test
    public void hasDeck_deckNotInTopDeck_returnsFalse() {
        assertFalse(modelManager.hasDeck(DECK_A));
    }

    @Test
    public void hasDeck_deckInTopDeck_returnsTrue() {
        modelManager.addDeck(DECK_A);
        assertTrue(modelManager.hasDeck(DECK_A));
    }

    @Test
    public void changeDeck_deckInTopDeck_changeViewState() {
        modelManager.addDeck(DECK_B);
        modelManager.changeDeck(DECK_B);
        assertTrue(modelManager.getViewState() instanceof CardsView);
    }

    //========== Card Management Tests ==================================================================
    @Test
    public void hasCard_notInCardsView_throwsIllegalOperationWhileReviewingDeckException() {
        thrown.expect(IllegalOperationWhileReviewingDeckException.class);
        modelManager.hasCard(ADDITION);
    }


    @Test
    public void hasCard_nullCard_throwsNullPointerException() {
        modelManager.addDeck(DECK_B);
        modelManager.changeDeck(DECK_B);
        thrown.expect(NullPointerException.class);
        modelManager.hasCard(null);
    }

    @Test
    public void hasCard_cardInTopDeck_returnsTrue() {
        modelManager.addDeck(DECK_B);
        modelManager.changeDeck(DECK_B);
        modelManager.addCard(ADDITION);
        assertTrue(modelManager.hasCard(ADDITION));
    }

    @Test
    public void hasCard_cardNotInTopDeck_returnsFalse() {
        modelManager.addDeck(DECK_B);
        modelManager.changeDeck(DECK_B);
        assertFalse(modelManager.hasCard(SUBTRACTION));
    }

    @Test
    public void addCard_notInCardsView_throwsIllegalOperationWhileReviewingDeckException() {
        thrown.expect(IllegalOperationWhileReviewingDeckException.class);
        modelManager.addCard(SUBTRACTION);
    }

    @Test
    public void addCard_nullCard_throwsNulPointerException() {
        modelManager.addDeck(DECK_B);
        modelManager.changeDeck(DECK_B);
        thrown.expect(NullPointerException.class);
        modelManager.addCard(null);
    }

    @Test
    public void addCard_validCard_deckContainsNewCard() {
        modelManager.addDeck(DECK_B);
        modelManager.changeDeck(DECK_B);
        modelManager.addCard(SUBTRACTION);
        assertTrue(modelManager.hasCard(SUBTRACTION));
    }

    @Test
    public void deleteCard_notInCardsView_throwsIllegalOperationWhileReviewingDeckException() {
        thrown.expect(IllegalOperationWhileReviewingDeckException.class);
        modelManager.deleteCard(SUBTRACTION);
    }

    @Test
    public void deleteCard_nullCard_throwsNulPointerException() {
        modelManager.addDeck(DECK_B);
        modelManager.changeDeck(DECK_B);
        thrown.expect(NullPointerException.class);
        modelManager.deleteCard(null);
    }

    @Test
    public void deleteCard_cardIsSelected_selectionCleared() {
        modelManager.addDeck(DECK_B);
        modelManager.changeDeck(DECK_B);
        modelManager.addCard(ADDITION);
        modelManager.addCard(SUBTRACTION);
        modelManager.setSelectedItem(ADDITION);
        modelManager.deleteCard(SUBTRACTION);
        assertEquals(null, modelManager.getSelectedItem());
    }

    @Test
    public void deleteCard_cardInDeck_cardNotInDeck() {
        modelManager.addDeck(DECK_B);
        modelManager.changeDeck(DECK_B);
        modelManager.addCard(ADDITION);

        assertTrue(modelManager.hasCard(ADDITION));

        modelManager.deleteCard(ADDITION);

        assertFalse(modelManager.hasCard(ADDITION));
    }

    @Test
    public void setCard_notInCardsView_throwsIllegalOperationWhileReviewingDeckException() {
        thrown.expect(IllegalOperationWhileReviewingDeckException.class);
        modelManager.setCard(ADDITION, SUBTRACTION);
    }

    @Test
    public void setCard_nullCard_throwsNulPointerException() {
        modelManager.addDeck(DECK_B);
        modelManager.changeDeck(DECK_B);
        thrown.expect(NullPointerException.class);
        modelManager.setCard(null, ADDITION);
    }

    @Test
    public void setCard_cardInDeck_editedCardInDeck() {
        modelManager.addDeck(DECK_B);
        modelManager.changeDeck(DECK_B);
        modelManager.addCard(ADDITION);

        assertTrue(modelManager.hasCard(ADDITION));

        modelManager.setCard(ADDITION, SUBTRACTION);

        assertTrue(modelManager.hasCard(SUBTRACTION));
    }

    //TODO delete functionality for deck
//    @Test
//    public void deleteDeck_deckIsSelectedAndFirstDeckInFilteredDeckList_selectionCleared() {
//        modelManager.addDeck(DECK_A);
//        modelManager.setSelectedItem(DECK_A);
//        modelManager.deleteDeck(DECK_A);
//        assertEquals(null, modelManager.getSelectedItem());
//    }

    //TODO add delete deck functionality
//    @Test
//    public void deleteDeck_deckIsSelectedAndSecondDeckInFilteredDeckList_firstDeckSelected() {
//        modelManager.addDeck(DECK_A);
//        modelManager.addDeck(DECK_B);
//        assertEquals(Arrays.asList(DECK_A, DECK_B), modelManager.getFilteredList());
//        modelManager.setSelectedItem(DECK_B);
//        modelManager.deleteDeck(DECK_B);
//        assertEquals(DECK_A, modelManager.getSelectedItem());
//    }

    //TODO add setDeck
//    @Test
//    public void setDeck_deckIsSelected_selectedDeckUpdated() {
//        modelManager.addDeck(DECK_A);
//        modelManager.setSelectedItem(DECK_A);
//        Deck updatedAddition = new DeckBuilder(DECK_A).build();
//        modelManager.setDeck(DECK_A, updatedAddition);
//        assertEquals(updatedAddition, modelManager.getSelectedItem());
//    }

    @Test
    public void getFilteredList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredList().remove(0);
    }

    @Test
    public void setSelectedItem_cardNotInFilteredCardList_throwsCardNotFoundException() {
        modelManager.addDeck(DECK_B);
        modelManager.changeDeck(DECK_B);
        thrown.expect(CardNotFoundException.class);
        modelManager.setSelectedItem(ADDITION);
    }

    @Test
    public void setSelectedItem_cardInFilteredCardList_setsSelectedCard() {
        modelManager.addDeck(DECK_B);
        modelManager.changeDeck(DECK_B);
        modelManager.addCard(ADDITION);
        assertEquals(Collections.singletonList(ADDITION), modelManager.getFilteredList());
        modelManager.setSelectedItem(ADDITION);
        assertEquals(ADDITION, modelManager.getSelectedItem());
    }

    @Test
    public void setSelectedItem_deckInFilteredDeckList_setsSelectedDeck() {
        modelManager.addDeck(DECK_A);
        assertEquals(Collections.singletonList(DECK_A), modelManager.getFilteredList());
        modelManager.setSelectedItem(DECK_A);
        assertEquals(DECK_A, modelManager.getSelectedItem());
    }

    @Test
    public void equals() {
        TopDeck topDeck = new TopDeckBuilder().withDeck(DECK_A).withDeck(DECK_B).build();
        TopDeck differentTopDeck = new TopDeck();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(topDeck, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(topDeck, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different topDeck -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentTopDeck, userPrefs)));

        //TODO for card
//        // different filteredList -> returns false
//        String[] keywords = ADDITION.getQuestion().split("\\s+");
//        modelManager.updateFilteredList(new QuestionContainsKeywordsPredicate(Arrays.asList(keywords)));
//        assertFalse(modelManager.equals(new ModelManager(topDeck, userPrefs)));

        // different filteredList -> returns false
        String keyword = DECK_A.getName().toString();
        modelManager.updateFilteredList(new DeckNameContainsKeywordsPredicate((Arrays.asList(keyword))));
        assertFalse(modelManager.equals(new ModelManager(topDeck, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredList(PREDICATE_SHOW_ALL_DECKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTopDeckFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(topDeck, differentUserPrefs)));
    }
}
