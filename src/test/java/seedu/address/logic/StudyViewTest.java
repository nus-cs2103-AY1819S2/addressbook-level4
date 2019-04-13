package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCards.ADDITION;
import static seedu.address.testutil.TypicalCards.SUBTRACTION;
import static seedu.address.testutil.TypicalDecks.DECK_A;
import static seedu.address.testutil.TypicalDecks.DECK_WITH_CARDS;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.BackCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GenerateQuestionCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.OpenDeckCommand;
import seedu.address.logic.commands.ShowAnswerCommand;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.DeckBuilder;

public class StudyViewTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Deck deck = new DeckBuilder(DECK_A).build();
    private Deck deckWithCards = new DeckBuilder(DECK_WITH_CARDS).build();
    private StudyView studyView;

    @Before
    public void setUp() {
        Card subtraction = new CardBuilder(SUBTRACTION).build();
        deck.addCard(subtraction);
        studyView = new StudyView(deck);
    }

    @Test
    public void constructor() {
        assertEquals(deck, studyView.getActiveDeck());
        assertEquals(StudyView.StudyState.QUESTION, studyView.getCurrentStudyState());
        assertEquals(null, studyView.getUserAnswer());
    }

    //=========== Current Card
    // ================================================================================

    @Test
    public void setCurrentCard_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        studyView.setCurrentCard(null);
    }

    @Test
    public void setCurrentCard_validCardQuestion_setCurrentCard() {
        Card addition = new CardBuilder(ADDITION).build();
        studyView.setCurrentCard(addition);
        assertEquals(addition, studyView.getCurrentCard());
        studyView.setCurrentStudyState(StudyView.StudyState.QUESTION);
        assertEquals(addition.getQuestion(), studyView.textShownProperty().getValue());
    }

    @Test
    public void setCurrentCard_validCardAnswer_setCurrentCard() {
        Card addition = new CardBuilder(ADDITION).build();
        studyView.setCurrentCard(addition);
        assertEquals(addition, studyView.getCurrentCard());
        studyView.setCurrentStudyState(StudyView.StudyState.ANSWER);
        assertEquals(addition.getAnswer(), studyView.textShownProperty().getValue());
    }

    @Test
    public void generateCard_anyCondition_setCurrentCard() {
        assertNotNull(studyView.getCurrentCard());
    }

    //=========== Study States
    // ================================================================================

    @Test
    public void setStudyState_nullState_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        studyView.setCurrentStudyState(null);
    }

    @Test
    public void setStudyState_validState_setStudyState() {
        studyView.setCurrentStudyState(StudyView.StudyState.QUESTION);
        assertEquals(StudyView.StudyState.QUESTION, studyView.getCurrentStudyState());
    }

    //=========== TextShown ================================================================================

    @Test
    public void updateTextShown_questionMode_returnsQuestion() {
        studyView.setCurrentStudyState(StudyView.StudyState.QUESTION);
        String question = studyView.getCurrentCard().getQuestion();
        assertEquals(question, studyView.textShownProperty().getValue());
    }

    @Test
    public void updateTextShown_answerMode_returnsAnswer() {
        studyView.setCurrentStudyState(StudyView.StudyState.ANSWER);
        String answer = studyView.getCurrentCard().getAnswer();
        assertEquals(answer, studyView.textShownProperty().getValue());
    }

    //=========== User Answer ================================================================================

    @Test
    public void setUserAnswer_nullAnswer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        studyView.setUserAnswer(null);
    }

    @Test
    public void setUserAnswer_validAnswer_setUserAnswer() {
        String sampleUserAnswer = "Four";
        studyView.setUserAnswer(sampleUserAnswer);
        assertEquals(sampleUserAnswer, studyView.getUserAnswer());
    }

    @Test
    public void equals() {
        StudyView firstStudyView = new StudyView(deck);
        StudyView secondStudyView = new StudyView(deckWithCards);

        // same object -> returns true
        assertTrue(firstStudyView.equals(firstStudyView));

        // copied object -> returns true
        StudyView firstStudyViewCopy = new StudyView(firstStudyView);
        assertTrue(firstStudyView.equals(firstStudyViewCopy));

        // same study view different study state -> returns false
        firstStudyViewCopy.setCurrentStudyState(StudyView.StudyState.ANSWER);
        assertFalse(firstStudyView.equals(firstStudyViewCopy));

        // different types -> returns false
        assertFalse(firstStudyView.equals(1));

        // null -> returns false
        assertFalse(firstStudyView.equals(null));

        // different studyViews with diff deck shufflers -> returns false
        assertFalse(firstStudyView.equals(secondStudyView));

    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(studyView.parse(ExitCommand.COMMAND_WORD, "") instanceof ExitCommand);
        assertTrue(studyView.parse(ExitCommand.COMMAND_WORD, " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(studyView.parse(HelpCommand.COMMAND_WORD, "") instanceof HelpCommand);
        assertTrue(studyView.parse(HelpCommand.COMMAND_WORD, " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(studyView.parse(HistoryCommand.COMMAND_WORD, "") instanceof HistoryCommand);
        assertTrue(studyView.parse(HistoryCommand.COMMAND_WORD, " 3") instanceof HistoryCommand);
    }

    @Test
    public void parseCommand_open() throws Exception {
        assertTrue(studyView.parse(OpenDeckCommand.COMMAND_WORD, "") instanceof OpenDeckCommand);
        assertTrue(studyView.parse(OpenDeckCommand.COMMAND_WORD, " 3") instanceof OpenDeckCommand);
    }

    @Test
    public void parseCommand_back() throws Exception {
        assertTrue(studyView.parse(BackCommand.COMMAND_WORD, "") instanceof BackCommand);
        assertTrue(studyView.parse(BackCommand.COMMAND_WORD, " 3") instanceof BackCommand);
    }

    @Test
    public void parseCommand_showAnswer() throws Exception {
        studyView.setCurrentStudyState(StudyView.StudyState.QUESTION);
        assertTrue(studyView.parse("sample", "answer") instanceof ShowAnswerCommand);
        assertTrue(studyView.parse("sample", "") instanceof ShowAnswerCommand);
    }

    @Test
    public void parseCommand_generateQuestion() throws Exception {
        studyView.setCurrentStudyState(StudyView.StudyState.ANSWER);
        assertTrue(studyView.parse("1", "1") instanceof GenerateQuestionCommand);
        assertTrue(studyView.parse("1", "") instanceof GenerateQuestionCommand);
    }

}
