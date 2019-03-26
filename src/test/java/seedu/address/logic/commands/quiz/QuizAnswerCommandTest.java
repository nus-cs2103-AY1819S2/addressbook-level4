package seedu.address.logic.commands.quiz;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCards.CARD_JAPAN;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Lessons;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.model.modelmanager.management.ManagementModelManager;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.session.Session;
import seedu.address.model.srscard.SrsCard;
import seedu.address.model.user.CardSrsData;
import seedu.address.model.user.User;
import seedu.address.testutil.Assert;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.SessionBuilder;
import seedu.address.testutil.SrsCardBuilder;

// TODO CHANGE ALL INIT TO INITWITHSESSION
public class QuizAnswerCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<QuizCard> validQuizCard;
    private Quiz actualQuiz;
    private Quiz expectedQuiz;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        final QuizCard quizCardJapan = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"));
        final QuizCard quizCardHungary = new QuizCard("Hungary", "Budapest");
        validQuizCard = Arrays.asList(quizCardJapan, quizCardHungary);
        actualQuiz = new Quiz(validQuizCard, QuizMode.LEARN);
        expectedQuiz = new Quiz(validQuizCard, QuizMode.LEARN);
    }

    @Test
    public void constructor_nullAnswer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new QuizAnswerCommand(null);
    }

    @Test
    public void execute_wrongModel_throwsCommandException() {
        Model model = new ManagementModelManager();
        Assert.assertThrows(CommandException.class, () ->
            new QuizAnswerCommand("someanswer").execute(model, commandHistory));
    }

    @Test
    public void execute_validLearn_success() {
        final String answer = "Tokyo";
        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(actualQuiz);

        QuizModel actual = new QuizModelManager();
        actual.init(expectedQuiz);
        actual.getNextCard();

        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand(answer);
        expectedModel.getNextCard();
        QuizCard card = expectedModel.getNextCard();
        card.isCorrect(answer);

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            "", expectedModel);
    }

    @Test
    public void execute_validPreview_success() {
        final String answer = "";
        Lesson lesson = new LessonBuilder().build();
        final Session session = new SessionBuilder(new Session("01-01-Learn", 2,
            QuizMode.LEARN, List.of(new SrsCardBuilder().build(),
            new SrsCardBuilder(new SrsCard(CARD_JAPAN, new CardSrsData(CARD_JAPAN.hashCode(), 1,
                1, Instant.now().plus(Duration.ofHours(2))), lesson)).build()))).build();

        QuizModelManager expectedModel = new QuizModelManager();
        ManagementModelManager expectedMgmtModel =
            new ManagementModelManager(new UserPrefs(), new Lessons(), new User());

        expectedModel.initWithSession(new Quiz(validQuizCard, QuizMode.PREVIEW), session,
            expectedMgmtModel);

        QuizModel actual = new QuizModelManager();
        ManagementModelManager actualManagementModel =
            new ManagementModelManager(new UserPrefs(), new Lessons(), new User());
        actual.initWithSession(new Quiz(validQuizCard, QuizMode.PREVIEW), session, actualManagementModel);
        actual.getNextCard();

        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand(answer);
        expectedModel.getNextCard();
        QuizCard card = expectedModel.getNextCard();
        card.isCorrect(answer);

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            "", expectedModel);

        // complete preview quiz
        quizAnswerCommand = new QuizAnswerCommand("Budapest");
        expectedModel.end();

        String expectedMessage = QuizAnswerCommand.MESSAGE_COMPLETE;

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            expectedMessage, expectedModel);
    }

    @Test
    public void execute_validReview_success() {
        final String answer = "Tokyo";
        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(expectedQuiz);

        QuizModel actual = new QuizModelManager();
        actual.init(actualQuiz);
        actual.getNextCard();
        actual.getNextCard();

        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand(answer);
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        QuizCard card = expectedModel.getNextCard();
        card.isCorrect(answer);

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            "", expectedModel);
    }

    @Test
    public void execute_correctAndWrongAnswer_success() {
        final String correctAns = "Tokyo";
        final String wrongAns = "wronganswer";

        // correct
        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(expectedQuiz);

        QuizModel actual = new QuizModelManager();
        actual.init(actualQuiz);
        actual.getNextCard();
        actual.getNextCard();
        actual.getNextCard();

        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand(correctAns);
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        QuizCard card = expectedModel.getNextCard();
        card.isCorrect(correctAns);

        String expectedMessage = QuizAnswerCommand.MESSAGE_CORRECT;


        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            expectedMessage, expectedModel);

        // wrong
        quizAnswerCommand = new QuizAnswerCommand(wrongAns);
        actual.getNextCard();
        card = expectedModel.getNextCard();
        card.isCorrect(wrongAns);

        expectedMessage = String.format(QuizAnswerCommand.MESSAGE_WRONG_ONCE, wrongAns);

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            expectedMessage, expectedModel);

        // wrong twice
        expectedMessage = String.format(QuizAnswerCommand.MESSAGE_WRONG, wrongAns, card.getAnswer());
        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            expectedMessage, expectedModel);

        // complete the quiz
        quizAnswerCommand = new QuizAnswerCommand("Hungary");
        actual.getNextCard();
        card = expectedModel.getNextCard();
        card.isCorrect(wrongAns);
        expectedModel.end();

        expectedMessage = QuizAnswerCommand.MESSAGE_CORRECT + QuizAnswerCommand.MESSAGE_COMPLETE;

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            expectedMessage, expectedModel);

    }

    @Test
    public void equals() {
        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand("Tokyo");
        QuizAnswerCommand quizAnswerCommandDiff = new QuizAnswerCommand("Something");

        // same object -> returns true
        assertTrue(quizAnswerCommand.equals(quizAnswerCommand));

        // same values -> returns true
        QuizAnswerCommand quizAnswerCommandCopy = new QuizAnswerCommand("Tokyo");
        assertTrue(quizAnswerCommand.equals(quizAnswerCommandCopy));

        // different answer -> returns false
        assertFalse(quizAnswerCommand.equals(quizAnswerCommandDiff));

        // null -> returns false
        assertFalse(quizAnswerCommand == null);

        // differnt type -> returns false
        assertFalse(quizAnswerCommand.equals(5));
    }
}
