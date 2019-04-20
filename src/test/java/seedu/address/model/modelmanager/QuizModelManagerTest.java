package seedu.address.model.modelmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.CardBuilder.DEFAULT_OPTIONAL;
import static seedu.address.testutil.TypicalCards.CARD_BELGIUM;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2;
import static seedu.address.testutil.TypicalSession.SESSION_LEARNT_BEFORE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.testutil.Assert;
import seedu.address.testutil.SrsCardBuilder;

public class QuizModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private QuizModel modelManager = new QuizModelManager();
    private QuizCard firstCard;
    private Quiz quiz;

    @Before
    public void setUp() {
        List<QuizCard> generatedCards = SESSION_DEFAULT_2.generateSession();
        firstCard = generatedCards.get(0).generateOrderedQuizCardWithIndex(0, QuizMode.PREVIEW);
        quiz = new Quiz(generatedCards, SESSION_DEFAULT_2.getMode());
    }

    @Test
    public void getNextCard_notInitialised_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
            modelManager.getNextCard());
    }
    @Test
    public void getSessionFields() {
        modelManager.init(quiz, SESSION_LEARNT_BEFORE);
        assertEquals("default", modelManager.getName());
        assertEquals(1, modelManager.getIndex());
        assertEquals(1, modelManager.getCount());
        assertEquals(QuizMode.LEARN, modelManager.getMode());
        assertEquals(List.of(new SrsCardBuilder().build()), modelManager.getQuizSrsCards());
    }
    @Test
    public void testUpdateUser() {
        ManagementModelManager mgtManager = new ManagementModelManager();
        QuizModelManager manager = new QuizModelManager(mgtManager);
        manager.init(quiz, SESSION_LEARNT_BEFORE);
        List<List<Integer>> quizInformation = new ArrayList<>();
        quizInformation.add(List.of(0, 1, 1, 0));
        manager.updateUserProfile(quizInformation);
        assertEquals(mgtManager.getCardSrsData(CARD_BELGIUM.hashCode()).getNumOfAttempts(), 2);
        assertEquals(mgtManager.getCardSrsData(CARD_BELGIUM.hashCode()).getStreak(), 2);
        assertEquals(mgtManager.getCardSrsData(CARD_BELGIUM.hashCode()).isDifficult(), false);
    }
    @Test
    public void getNextCard() {
        modelManager.init(quiz, SESSION_DEFAULT_2);

        // get first card
        assertEquals(firstCard, modelManager.getNextCard());

        // get the rest
        assertTrue(modelManager.hasCardLeft());
        modelManager.getNextCard();
        modelManager.getNextCard();
        modelManager.getNextCard();
        modelManager.getNextCard();
        modelManager.getNextCard();

        // no cards left
        assertFalse(modelManager.hasCardLeft());
    }

    @Test
    public void getCurrentProgress() {
        modelManager.init(quiz, SESSION_DEFAULT_2);

        assertEquals("0/6", modelManager.getCurrentProgress());

        modelManager.getNextCard();
        assertEquals("1/6", modelManager.getCurrentProgress());

        modelManager.getNextCard();
        modelManager.getNextCard();
        modelManager.getNextCard();
        modelManager.getNextCard();
        modelManager.getNextCard();
        assertEquals("6/6", modelManager.getCurrentProgress());

    }

    @Test
    public void getCurrentQuizCard() {
        modelManager.init(quiz, SESSION_DEFAULT_2);

        QuizCard expected = modelManager.getNextCard();

        assertEquals(firstCard, modelManager.getCurrentQuizCard());
        assertEquals(expected, modelManager.getCurrentQuizCard());
    }

    @Test
    public void getQuizCardList() {
        modelManager.init(quiz, SESSION_DEFAULT_2);

        assertEquals(quiz.getOriginalQuizCardList(), modelManager.getQuizCardList());
    }

    @Test
    public void toggleIsCardDifficult() {
        modelManager.init(quiz, SESSION_DEFAULT_2);

        assertTrue(modelManager.toggleIsCardDifficult(0));
        assertFalse(modelManager.toggleIsCardDifficult(0));
    }

    @Test
    public void getOpt() {
        modelManager.init(quiz, SESSION_DEFAULT_2);
        modelManager.getNextCard();

        assertEquals(DEFAULT_OPTIONAL, modelManager.getOpt());
    }

    @Test
    public void isQuizDone() {
        assertTrue(modelManager.isQuizDone());

        modelManager.init(quiz, SESSION_DEFAULT_2);
        assertFalse(modelManager.isQuizDone());

        modelManager.setResultDisplay(false);
        modelManager.end();
        assertTrue(modelManager.isQuizDone());
    }

    @Test
    public void setResultDisplay() {
        modelManager.init(quiz, SESSION_DEFAULT_2);

        modelManager.setResultDisplay(true);
        assertTrue(modelManager.isResultDisplay());

        modelManager.setResultDisplay(false);
        assertFalse(modelManager.isResultDisplay());
    }

    @Test
    public void end() {
        modelManager.init(quiz, SESSION_DEFAULT_2);

        assertTrue(modelManager.hasCardLeft());

        // before doing any question
        List<List<Integer>> expected = new ArrayList<>();

        assertEquals(expected, modelManager.end());

        // after quiz end still can ask for next card, keeps track of previous entry
        assertTrue(modelManager.hasCardLeft());

        // preview questions and answer
        modelManager.getNextCard();
        modelManager.getNextCard();

        // start the actual quiz
        modelManager.getNextCard();
        modelManager.updateTotalAttemptsAndStreak(0, "Brussels");
        modelManager.getNextCard();
        modelManager.updateTotalAttemptsAndStreak(1, "Tokyo");
        modelManager.getNextCard();
        modelManager.updateTotalAttemptsAndStreak(0, "Belgium");
        modelManager.getNextCard();
        modelManager.updateTotalAttemptsAndStreak(1, "Japan");

        expected = new ArrayList<>();
        expected.add(Arrays.asList(0, 2, 2, 0));
        expected.add(Arrays.asList(1, 2, 2, 0));

        assertEquals(expected, modelManager.end());
        assertEquals(4, modelManager.getQuizTotalAttempts());
        assertEquals(4, modelManager.getQuizTotalCorrectQuestions());
    }

    @Test
    public void equals() {
        modelManager.init(quiz, SESSION_DEFAULT_2);

        // same values -> returns true
        QuizModelManager modelManagerCopy = new QuizModelManager();
        modelManagerCopy.init(quiz, SESSION_DEFAULT_2);
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // other object -> returns false
        assertNotEquals(modelManager, quiz);

        // different types -> returns false
        assertNotEquals(5, modelManager);
    }
}
