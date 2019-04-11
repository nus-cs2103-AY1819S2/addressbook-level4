package seedu.knowitall.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.knowitall.model.Model.COMPARATOR_ASC_SCORE_CARDS;
import static seedu.knowitall.model.Model.COMPARATOR_LEXICOGRAPHIC_CARDS;
import static seedu.knowitall.model.Model.PREDICATE_SHOW_ALL_CARDS;
import static seedu.knowitall.testutil.TypicalCards.ALICE;
import static seedu.knowitall.testutil.TypicalCards.BENSON;
import static seedu.knowitall.testutil.TypicalCards.CARD_2;
import static seedu.knowitall.testutil.TypicalCards.FIONA;
import static seedu.knowitall.testutil.TypicalCards.GEORGE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.knowitall.commons.core.GuiSettings;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.model.card.QuestionContainsKeywordsPredicate;
import seedu.knowitall.model.card.exceptions.CardNotFoundException;
import seedu.knowitall.testutil.CardBuilder;
import seedu.knowitall.testutil.CardFolderBuilder;
import seedu.knowitall.testutil.TypicalCards;
import seedu.knowitall.testutil.TypicalIndexes;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager model;

    @Before
    public void setUp() {
        List<ReadOnlyCardFolder> cardFolders = new ArrayList<>();
        cardFolders.add(TypicalCards.getTypicalCardFolderOne());
        cardFolders.add(TypicalCards.getTypicalCardFolderTwo());
        cardFolders.add(TypicalCards.getEmptyCardFolder());
        model = new ModelManager(cardFolders);
        model.enterFolder(cardFolders.size() - 1);
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), model.getUserPrefs());
        assertEquals(new GuiSettings(), model.getGuiSettings());
        assertNull(model.getSelectedCard());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        model.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setcardFolderFilesPath(Paths.get("knowitall/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        model.setUserPrefs(userPrefs);
        assertEquals(userPrefs, model.getUserPrefs());

        // Modifying userPrefs should not modify model's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setcardFolderFilesPath(Paths.get("new/knowitall/book/file/path"));
        assertEquals(oldUserPrefs, model.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        model.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        model.setGuiSettings(guiSettings);
        assertEquals(guiSettings, model.getGuiSettings());
    }

    @Test
    public void setcardFolderFilesPath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        model.setcardFolderFilesPath(null);
    }

    @Test
    public void setcardFolderFilesPath_validPath_setscardFolderFilesPath() {
        Path path = Paths.get("knowitall/book/file/path");
        model.setcardFolderFilesPath(path);
        assertEquals(path, model.getcardFolderFilesPath());
    }

    @Test
    public void hasFolder_existingFolder_returnsTrue() {
        String folderName = TypicalCards.getTypicalFolderOneName();
        assertTrue(model.hasFolder(folderName));
    }
    @Test
    public void hasFolder_nonExistingFolder_returnsFalse() {
        String nonExistentFolderName = "Non-existent folder";
        assertFalse(model.hasFolder(nonExistentFolderName));
    }

    @Test
    public void hasFolder_nullFolder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        model.hasFolder(null);
    }

    @Test
    public void deleteFolder_existingFolder_folderDeleted() {
        List<ReadOnlyCardFolder> cardFolders = Arrays.asList(TypicalCards.getTypicalCardFolderOne(),
                TypicalCards.getEmptyCardFolder());
        Model expectedModel = new ModelManager(cardFolders);

        model.exitFolderToHome();
        model.deleteFolder(TypicalIndexes.INDEX_SECOND_CARD_FOLDER.getZeroBased());
        assertEquals(expectedModel, model);
    }

    @Test
    public void deleteFolder_nonExistingFolder_throwsAssertionError() {
        thrown.expect(AssertionError.class);

        model.deleteFolder(3);
    }

    @Test
    public void hasCard_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        model.hasCard(null);
    }

    @Test
    public void hasCard_cardNotInCardFolder_returnsFalse() {
        assertFalse(model.hasCard(ALICE));
    }

    @Test
    public void hasCard_cardInCardFolder_returnsTrue() {
        model.addCard(ALICE);
        assertTrue(model.hasCard(ALICE));
    }

    @Test
    public void deleteCard_cardIsSelectedAndFirstCardInFilteredCardList_selectionCleared() {
        model.addCard(ALICE);
        model.setSelectedCard(ALICE);
        model.deleteCard(ALICE);
        assertEquals(null, model.getSelectedCard());
    }

    @Test
    public void deleteCard_cardIsSelectedAndSecondCardInFilteredCardList_firstCardSelected() {
        model.addCard(ALICE);
        model.addCard(CARD_2);
        assertEquals(Arrays.asList(ALICE, CARD_2), model.getActiveFilteredCards());
        model.setSelectedCard(CARD_2);
        model.deleteCard(CARD_2);
        assertEquals(ALICE, model.getSelectedCard());
    }

    @Test
    public void setCard_cardIsSelected_selectedCardUpdated() {
        model.addCard(ALICE);
        model.setSelectedCard(ALICE);
        Card updatedAlice = new CardBuilder(ALICE).withAnswer(VALID_ANSWER_2).build();
        model.setCard(ALICE, updatedAlice);
        assertEquals(updatedAlice, model.getSelectedCard());
    }

    @Test
    public void sortCards_byScore() {
        model.addCard(FIONA);
        model.addCard(GEORGE);
        model.sortFilteredCard(COMPARATOR_ASC_SCORE_CARDS);
        // Fiona should be sorted after George because higher score
        Card lastCard = model.getActiveFilteredCards().get(model.getActiveFilteredCards().size() - 1);
        assertEquals(FIONA, lastCard);
    }

    @Test
    public void sortCards_byQuestionLexicographic() {
        model.addCard(FIONA);
        model.addCard(GEORGE);
        model.sortFilteredCard(COMPARATOR_LEXICOGRAPHIC_CARDS);
        // Fiona should be sorted after George because higher score
        Card lastCard = model.getActiveFilteredCards().get(model.getActiveFilteredCards().size() - 1);
        assertEquals(GEORGE, lastCard);
    }

    @Test
    public void getFilteredCardList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        model.getActiveFilteredCards().remove(0);
    }

    @Test
    public void setSelectedCard_cardNotInFilteredCardList_throwsCardNotFoundException() {
        thrown.expect(CardNotFoundException.class);
        model.setSelectedCard(ALICE);
    }

    @Test
    public void setSelectedCard_cardInFilteredCardList_setsSelectedCard() {
        model.addCard(ALICE);
        assertEquals(Collections.singletonList(ALICE), model.getActiveFilteredCards());
        model.setSelectedCard(ALICE);
        assertEquals(ALICE, model.getSelectedCard());
    }

    @Test
    public void setCurrentTestedCard_cardNotInFilteredCardList_throwsCardNotFoundException() {
        thrown.expect(CardNotFoundException.class);
        model.setSelectedCard(ALICE);
    }

    @Test
    public void setCurrentTestedCard_cardInFilteredCardList_setsSelectedCard() {
        model.addCard(ALICE);
        assertEquals(Collections.singletonList(ALICE), model.getActiveFilteredCards());
        model.setCurrentTestedCard(ALICE);
        assertEquals(ALICE, model.getCurrentTestedCard());
    }

    @Test
    public void equals() {
        CardFolder cardFolder = new CardFolderBuilder().withCard(ALICE).withCard(BENSON).build();
        CardFolder differentCardFolder = new CardFolder(this.getClass().getName());
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        model = new ModelManager(Collections.singletonList(cardFolder), userPrefs);
        ModelManager modelManagerCopy = new ModelManager(Collections.singletonList(cardFolder), userPrefs);
        assertTrue(model.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(model.equals(model));

        // null -> returns false
        assertFalse(model.equals(null));

        // different types -> returns false
        assertFalse(model.equals(5));

        // different cardFolder -> returns false
        assertFalse(model.equals(new ModelManager(Collections.singletonList(differentCardFolder), userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getQuestion().fullQuestion.split("\\s+");
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        model.updateFilteredCard(new QuestionContainsKeywordsPredicate(Arrays.asList(keywords)));

        Model differentModel = new ModelManager(Collections.singletonList(cardFolder), userPrefs);
        differentModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        assertFalse(model.equals(differentModel));

        // resets model to initial state for upcoming tests
        model.updateFilteredCard(PREDICATE_SHOW_ALL_CARDS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setcardFolderFilesPath(Paths.get("differentFilePath"));
        assertFalse(model.equals(new ModelManager(Collections.singletonList(cardFolder), differentUserPrefs)));

        // filteredList sorted differently -> returns true
        model.sortFilteredCard(COMPARATOR_ASC_SCORE_CARDS);

        ModelManager sameModel = new ModelManager(Collections.singletonList(cardFolder), userPrefs);
        sameModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        assertTrue(model.equals(sameModel));

    }
}
