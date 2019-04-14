package seedu.knowitall.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.transformation.FilteredList;
import seedu.knowitall.commons.core.GuiSettings;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.model.card.QuestionContainsKeywordsPredicate;
import seedu.knowitall.model.card.exceptions.CardNotFoundException;
import seedu.knowitall.testutil.CardBuilder;
import seedu.knowitall.testutil.CardFolderBuilder;
import seedu.knowitall.testutil.TypicalCards;
import seedu.knowitall.testutil.TypicalIndexes;

public class ModelManagerTest {
    public static final String CORRECT_ANSWER_TO_GEORGE = "9482442";
    public static final String WRONG_ANSWER_TO_GEORGE = "9482441";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager;

    @Before
    public void setUp() {
        List<ReadOnlyCardFolder> cardFolders = new ArrayList<>();
        cardFolders.add(TypicalCards.getTypicalFolderOne());
        cardFolders.add(TypicalCards.getTypicalFolderTwo());
        cardFolders.add(TypicalCards.getEmptyCardFolder());
        modelManager = new ModelManager(cardFolders);
        modelManager.enterFolder(cardFolders.size() - 1);
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertNull(modelManager.getSelectedCard());
        assertNull(modelManager.getCurrentTestedCard());
        assertNull(modelManager.getCurrentTestedCardFolder());
        assertEquals(0, modelManager.getNumAnsweredCorrectly());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setcardFolderFilesPath(Paths.get("knowitall/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setcardFolderFilesPath(Paths.get("new/knowitall/book/file/path"));
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
    public void setcardFolderFilesPath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setcardFolderFilesPath(null);
    }

    @Test
    public void setcardFolderFilesPath_validPath_setscardFolderFilesPath() {
        Path path = Paths.get("knowitall/book/file/path");
        modelManager.setcardFolderFilesPath(path);
        assertEquals(path, modelManager.getcardFolderFilesPath());
    }

    @Test
    public void hasFolder_existingFolder_returnsTrue() {
        String folderName = TypicalCards.getTypicalFolderOneName();
        assertTrue(modelManager.hasFolder(folderName));
    }
    @Test
    public void hasFolder_nonExistingFolder_returnsFalse() {
        String nonExistentFolderName = "Non-existent folder";
        assertFalse(modelManager.hasFolder(nonExistentFolderName));
    }

    @Test
    public void hasFolder_nullFolder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasFolder(null);
    }

    @Test
    public void deleteFolder_existingFolder_folderDeleted() {
        List<ReadOnlyCardFolder> cardFolders = Arrays.asList(TypicalCards.getTypicalFolderOne(),
                TypicalCards.getEmptyCardFolder());
        Model expectedModel = new ModelManager(cardFolders);

        modelManager.exitFolderToHome();
        modelManager.deleteFolder(TypicalIndexes.INDEX_SECOND_CARD_FOLDER.getZeroBased());
        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void deleteFolder_nonExistingFolder_throwsAssertionError() {
        thrown.expect(AssertionError.class);

        modelManager.deleteFolder(3);
    }

    @Test
    public void addFolder_nonExistingFolder_folderAdded() {
        List<ReadOnlyCardFolder> cardFolders = new ArrayList<>();
        cardFolders.add(TypicalCards.getTypicalFolderOne());
        modelManager = new ModelManager(cardFolders);
        modelManager.exitFolderToHome();
        modelManager.addFolder(new CardFolder(TypicalCards.getTypicalFolderTwoName()));

        cardFolders.add(new CardFolder(TypicalCards.getTypicalFolderTwoName()));
        Model expectedModel = new ModelManager(cardFolders);
        assertEquals(expectedModel, modelManager);
    }

    @Test
    public void addFolder_existingFolder_throwsDuplicateException() {
        thrown.expect(DuplicateCardFolderException.class);
        modelManager.addFolder(new CardFolder(TypicalCards.getTypicalFolderOneName()));
    }

    @Test
    public void addFolder_nullFolder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.addFolder(null);
    }

    @Test
    public void hasCard_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasCard(null);
    }

    @Test
    public void hasCard_cardNotInCardFolder_returnsFalse() {
        assertFalse(modelManager.hasCard(ALICE));
    }

    @Test
    public void hasCard_cardInCardFolder_returnsTrue() {
        modelManager.addCard(ALICE);
        assertTrue(modelManager.hasCard(ALICE));
    }

    @Test
    public void deleteCard_cardIsSelectedAndFirstCardInFilteredCardList_selectionCleared() {
        modelManager.addCard(ALICE);
        modelManager.setSelectedCard(ALICE);
        modelManager.deleteCard(ALICE);
        assertNull(modelManager.getSelectedCard());
    }

    @Test
    public void deleteCard_cardIsSelectedAndSecondCardInFilteredCardList_firstCardSelected() {
        modelManager.addCard(ALICE);
        modelManager.addCard(CARD_2);
        assertEquals(Arrays.asList(ALICE, CARD_2), modelManager.getActiveFilteredCards());
        modelManager.setSelectedCard(CARD_2);
        modelManager.deleteCard(CARD_2);
        assertEquals(ALICE, modelManager.getSelectedCard());
    }

    @Test
    public void setCard_cardIsSelected_selectedCardUpdated() {
        modelManager.addCard(ALICE);
        modelManager.setSelectedCard(ALICE);
        Card updatedAlice = new CardBuilder(ALICE).withAnswer(VALID_ANSWER_2).build();
        modelManager.setCard(ALICE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedCard());
    }

    @Test
    public void sortCards_byScore() {
        modelManager.addCard(FIONA);
        modelManager.addCard(GEORGE);
        modelManager.sortFilteredCard(COMPARATOR_ASC_SCORE_CARDS);
        // Fiona should be sorted after George because higher score
        Card lastCard = modelManager.getActiveFilteredCards().get(modelManager.getActiveFilteredCards().size() - 1);
        assertEquals(FIONA, lastCard);
    }

    @Test
    public void sortCards_byQuestionLexicographic() {
        modelManager.addCard(FIONA);
        modelManager.addCard(GEORGE);
        modelManager.sortFilteredCard(COMPARATOR_LEXICOGRAPHIC_CARDS);
        // George should be sorted after Fiona by lexicographic order of questions
        Card lastCard = modelManager.getActiveFilteredCards().get(modelManager.getActiveFilteredCards().size() - 1);
        assertEquals(GEORGE, lastCard);
    }

    @Test
    public void getActiveFilteredCardList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getActiveFilteredCards().remove(0);
    }

    @Test
    public void getFilteredCardsList_retrievesList() {
        List<FilteredList<Card>> filteredCardsList =
                Stream.of(TypicalCards.getTypicalFolderOne(),
                        TypicalCards.getTypicalFolderTwo(),
                        TypicalCards.getEmptyCardFolder())
                        .map(VersionedCardFolder::new)
                        .map(folder -> new FilteredList<>(folder.getCardList()))
                        .collect(Collectors.toList());

        assertEquals(filteredCardsList, modelManager.getFilteredCardsList());
    }

    @Test
    public void setSelectedCard_cardNotInFilteredCardList_throwsCardNotFoundException() {
        thrown.expect(CardNotFoundException.class);
        modelManager.setSelectedCard(ALICE);
    }

    @Test
    public void setSelectedCard_cardInFilteredCardList_setsSelectedCard() {
        modelManager.addCard(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getActiveFilteredCards());
        modelManager.setSelectedCard(ALICE);
        assertEquals(ALICE, modelManager.getSelectedCard());
    }

    @Test
    public void startTestSession_isInEmptyFolder_throwsEmptyCardFolderException() {
        thrown.expect(EmptyCardFolderException.class);
        modelManager.startTestSession();
    }

    @Test
    public void startTestSession_isInNonEmptyFolder_startsTestSession() {
        setUpTwoCardsForTestSession();
        assertEquals(GEORGE, modelManager.getCurrentTestedCard());
        assertEquals(Model.State.IN_TEST, modelManager.getState());
        assertNotNull(modelManager.getCurrentTestedCardFolder());
        assertEquals(0, modelManager.getNumAnsweredCorrectly());
        Card lastCard = modelManager.getActiveFilteredCards().get(modelManager.getActiveFilteredCards().size() - 1);
        assertEquals(FIONA, lastCard);
    }

    @Test
    public void endTestSession_isInTestSession_endsTestSession() {
        setUpTwoCardsForTestSession();
        modelManager.endTestSession();
        assertEquals(Model.State.IN_FOLDER, modelManager.getState());
        assertNull(modelManager.getCurrentTestedCard());
        assertNull(modelManager.getCurrentTestedCardFolder());
        assertFalse(modelManager.isCardAlreadyAnswered());
        assertEquals(0, modelManager.getNumAnsweredCorrectly());
    }

    @Test
    public void setCurrentTestedCard_cardNotInFilteredCardList_throwsCardNotFoundException() {
        thrown.expect(CardNotFoundException.class);
        modelManager.setCurrentTestedCard(ALICE);
    }

    @Test
    public void setCurrentTestedCard_cardInFilteredCardList_setsCurrentTestedCard() {
        modelManager.addCard(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getActiveFilteredCards());
        modelManager.setCurrentTestedCard(ALICE);
        assertEquals(ALICE, modelManager.getCurrentTestedCard());
    }

    @Test
    public void markAttemptedAnswer_wrongAnswer_noChangeCorrectAnswerAttempts() {
        setUpTwoCardsForTestSession();
        Answer attempt = new Answer(WRONG_ANSWER_TO_GEORGE);
        int beforeNumAnsweredCorrectly = modelManager.getNumAnsweredCorrectly();
        modelManager.markAttemptedAnswer(attempt);

        assertEquals(beforeNumAnsweredCorrectly, modelManager.getNumAnsweredCorrectly());
    }

    @Test
    public void markAttemptedAnswer_correctAnswer_increaseCorrectAnswerAttempts() {
        setUpTwoCardsForTestSession();
        Answer attempt = new Answer(CORRECT_ANSWER_TO_GEORGE);
        int beforeNumAnsweredCorrectly = modelManager.getNumAnsweredCorrectly();
        modelManager.markAttemptedAnswer(attempt);

        assertEquals(beforeNumAnsweredCorrectly + 1, modelManager.getNumAnsweredCorrectly());
    }

    @Test
    public void testNextCard_hasANextCard_testsNextCard() {
        setUpTwoCardsForTestSession();
        modelManager.setCardAsAnswered();
        modelManager.testNextCard();

        assertNotNull(modelManager.getCurrentTestedCard());
        assertFalse(modelManager.isCardAlreadyAnswered());
    }

    @Test
    public void testNextCard_noNextCard_doesNotTestNextCard() {
        setUpOneCardForTestSession();
        modelManager.setCardAsAnswered();
        modelManager.testNextCard();

        assertTrue(modelManager.isCardAlreadyAnswered());
    }

    @Test
    public void equals() {
        CardFolder cardFolder = new CardFolderBuilder().withCard(ALICE).withCard(BENSON).build();
        CardFolder differentCardFolder = new CardFolder(TypicalCards.getTypicalFolderTwo());
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(Collections.singletonList(cardFolder), userPrefs);
        ModelManager modelManagerCopy = new ModelManager(Collections.singletonList(cardFolder), userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different cardFolder -> returns false
        assertFalse(modelManager.equals(new ModelManager(Collections.singletonList(differentCardFolder), userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getQuestion().fullQuestion.split("\\s+");
        modelManager.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        modelManager.updateFilteredCard(new QuestionContainsKeywordsPredicate(Arrays.asList(keywords)));

        Model differentModel = new ModelManager(Collections.singletonList(cardFolder), userPrefs);
        differentModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        assertFalse(modelManager.equals(differentModel));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCard(PREDICATE_SHOW_ALL_CARDS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setcardFolderFilesPath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(Collections.singletonList(cardFolder), differentUserPrefs)));

        // filteredList sorted differently -> returns true
        modelManager.sortFilteredCard(COMPARATOR_ASC_SCORE_CARDS);

        ModelManager sameModel = new ModelManager(Collections.singletonList(cardFolder), userPrefs);
        sameModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        assertTrue(modelManager.equals(sameModel));

    }

    private void setUpTwoCardsForTestSession() {
        modelManager.addCard(FIONA);
        modelManager.addCard(GEORGE);
        modelManager.startTestSession();
    }

    private void setUpOneCardForTestSession() {
        modelManager.addCard(FIONA);
        modelManager.startTestSession();
    }
}
