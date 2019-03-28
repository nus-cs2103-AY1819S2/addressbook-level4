package seedu.address.model.modelmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.quiz.QuizUiDisplayFormatter;
import seedu.address.model.session.Session;
import seedu.address.testutil.Assert;
import seedu.address.testutil.SessionBuilder;
import seedu.address.testutil.SrsCardBuilder;

public class QuizModelManagerTest {
    private static final QuizMode MODE = QuizMode.PREVIEW;
    private static final QuizCard QUIZCARD_1 = new QuizCard("Japan", "Tokyo",
            Arrays.asList("JP", "Asia"));
    private static final QuizCard QUIZCARD_2 = new QuizCard("Hungary", "Budapest");
    private static final List<QuizCard> VALID_QUIZCARD = Arrays.asList(QUIZCARD_1, QUIZCARD_2);
    private static final Quiz QUIZ = new Quiz(VALID_QUIZCARD, QuizMode.LEARN);
    private static final QuizUiDisplayFormatter formatter = new QuizUiDisplayFormatter("Question", "some question",
        "Answer", "some answer", QuizMode.LEARN);
    private static final Session SESSION = new SessionBuilder(new Session("01-01-Learn",
            1, QuizMode.LEARN, List.of(new SrsCardBuilder().build()))).build();
    private static final ManagementModelManager MANAGEMENT_MODEL_MANAGER = new ManagementModelManager();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private QuizModelManager modelManager = new QuizModelManager();

    @Test
    public void getNextCard_notInitialised_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
            modelManager.getNextCard());
    }
    @Test
    public void getSessionFields() {
        modelManager.initWithSession(QUIZ, SESSION);
        assertEquals("01-01-Learn", modelManager.getName());
        assertEquals(1, modelManager.getCount());
        assertEquals(QuizMode.LEARN, modelManager.getMode());
        assertEquals(List.of(new SrsCardBuilder().build()), modelManager.getQuizSrsCards());
    }

    @Test
    public void getNextCard() {
        modelManager.init(QUIZ);

        // get first card
        assertEquals(new QuizCard(0, QUIZCARD_1.getQuestion(), QUIZCARD_1.getAnswer(), MODE),
            modelManager.getNextCard());

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
        final QuizCard card1 = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"));
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2));

        modelManager.initWithSession(new Quiz(quizCards, QuizMode.LEARN), SESSION);
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
        final QuizCard card1 = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"));
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2));

        modelManager.initWithSession(new Quiz(quizCards, QuizMode.LEARN), SESSION);
        QuizCard expected = modelManager.getNextCard();

        assertEquals(new QuizCard(0, "Japan", "Tokyo", MODE), modelManager.getCurrentQuizCard());
        assertEquals(expected, modelManager.getCurrentQuizCard());
    }

    @Test
    public void toggleIsCardDifficult() {
        modelManager.initWithSession(QUIZ, SESSION);

        assertTrue(modelManager.toggleIsCardDifficult(0));
        assertFalse(modelManager.toggleIsCardDifficult(0));
    }

    @Test
    public void isDone() {
        assertTrue(modelManager.isQuizDone());

        modelManager.initWithSession(QUIZ, SESSION);
        assertFalse(modelManager.isQuizDone());

        modelManager.end();
        assertTrue(modelManager.isQuizDone());
    }

    @Test
    public void end() {
        final QuizCard card1 = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"));
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2));
        final Quiz quiz = new Quiz(quizCards, QuizMode.LEARN);
        modelManager.initWithSession(quiz, SESSION);

        assertTrue(modelManager.hasCardLeft());

        // before doing any question
        List<List<Integer>> expected = new ArrayList<>();
        expected.add(Arrays.asList(0, 0, 0));
        expected.add(Arrays.asList(1, 0, 0));

        assertEquals(expected, modelManager.end());

        // after quiz end still can ask for next card, keeps track of previous entry
        assertTrue(modelManager.hasCardLeft());

        // preview questions and answer
        modelManager.getNextCard();
        modelManager.getNextCard();

        // start the actual quiz
        modelManager.getNextCard();
        modelManager.updateTotalAttemptsAndStreak(0, "Tokyo");
        modelManager.getNextCard();
        modelManager.updateTotalAttemptsAndStreak(1, "Budapest");
        modelManager.getNextCard();
        modelManager.updateTotalAttemptsAndStreak(0, "Japan");
        modelManager.getNextCard();
        modelManager.updateTotalAttemptsAndStreak(1, "Hungary");

        expected = new ArrayList<>();
        expected.add(Arrays.asList(0, 2, 2));
        expected.add(Arrays.asList(1, 2, 2));

        assertEquals(expected, modelManager.end());
        assertEquals(4, modelManager.getQuizTotalAttempts());
        assertEquals(4, modelManager.getQuizTotalCorrectQuestions());
    }

    @Test
    public void getDisplayFormatter() {
        modelManager.setDisplayFormatter(formatter);
        assertEquals(formatter, modelManager.getDisplayFormatter());
    }

    @Test
    public void equals() {
        Quiz quiz = new Quiz(VALID_QUIZCARD, QuizMode.LEARN);

        // same values -> returns true
        modelManager = new QuizModelManager();
        modelManager.initWithSession(quiz, SESSION);
        QuizModelManager modelManagerCopy = new QuizModelManager();
        modelManagerCopy.initWithSession(quiz, SESSION);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager == null);

        // different types -> returns false
        assertFalse(modelManager.equals(5));
    }
}
