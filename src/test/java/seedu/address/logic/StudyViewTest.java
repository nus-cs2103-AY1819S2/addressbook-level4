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

import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.DeckBuilder;

public class StudyViewTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Deck deck = new DeckBuilder(DECK_A).build();
    private Deck deckWC = new DeckBuilder(DECK_WITH_CARDS).build();
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
        StudyView secondStudyView = new StudyView(deckWC);

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
}
