package seedu.address.logic.commands.quiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.logic.commands.quiz.QuizAnswerCommand.MESSAGE_CORRECT;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2_ACTUAL;
import static seedu.address.testutil.TypicalSession.SESSION_PREVIEW_2;
import static seedu.address.testutil.TypicalSession.SESSION_PREVIEW_2_ACTUAL;
import static seedu.address.testutil.TypicalSession.SESSION_REVIEW_2;
import static seedu.address.testutil.TypicalSession.SESSION_REVIEW_2_ACTUAL;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.quiz.QuizUiDisplayFormatter;
import seedu.address.testutil.Assert;

public class QuizAnswerCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<QuizCard> validQuizCard;
    private Quiz quizActual;
    private Quiz quizExpected;
    private CommandHistory commandHistory = new CommandHistory();
    private ManagementModelManager managementModelManager;
    private QuizModelManager actualModel;
    private QuizModelManager expectedModel;


    @Before
    public void setUp() {
        quizExpected = new Quiz(SESSION_DEFAULT_2.generateSession(), SESSION_DEFAULT_2.getMode());
        quizActual = new Quiz(SESSION_DEFAULT_2_ACTUAL.generateSession(), SESSION_DEFAULT_2_ACTUAL.getMode());
        managementModelManager = new ManagementModelManager();
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
        expectedModel = new QuizModelManager(managementModelManager);
        ManagementModelManager actualMgmtManager = new ManagementModelManager();
        actualModel = new QuizModelManager(actualMgmtManager);

        expectedModel.init(quizExpected, SESSION_DEFAULT_2);
        actualModel.init(quizActual, SESSION_DEFAULT_2_ACTUAL);

        actualModel.getNextCard();

        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand(answer);
        expectedModel.getNextCard();
        QuizCard card = expectedModel.getNextCard();
        card.isCorrect(answer);

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actualModel, commandHistory,
            "", expectedModel);
    }

    @Test
    public void execute_validPreview_success() {
        final String answer = "any answer";
        expectedModel = new QuizModelManager(managementModelManager);
        ManagementModelManager actualMgmtManager = new ManagementModelManager();
        actualModel = new QuizModelManager(actualMgmtManager);

        quizExpected = new Quiz(SESSION_PREVIEW_2.generateSession(), SESSION_PREVIEW_2.getMode());
        quizActual = new Quiz(SESSION_PREVIEW_2_ACTUAL.generateSession(), SESSION_PREVIEW_2_ACTUAL.getMode());

        expectedModel.init(quizExpected, SESSION_PREVIEW_2);
        actualModel.init(quizActual, SESSION_PREVIEW_2_ACTUAL);

        actualModel.getNextCard();

        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand(answer);
        expectedModel.getNextCard();
        expectedModel.getNextCard();

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actualModel, commandHistory,
            "", expectedModel);

        // complete preview quiz
        quizAnswerCommand = new QuizAnswerCommand(answer);
        expectedModel.end();

        CommandResult commandResult = new CommandResult(QuizAnswerCommand.MESSAGE_COMPLETE, true, false, false);

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actualModel, commandHistory,
            commandResult, expectedModel);
    }

    @Test
    public void execute_validReview_success() {
        final String answer = "Tokyo";
        expectedModel = new QuizModelManager(managementModelManager);
        ManagementModelManager actualMgmtManager = new ManagementModelManager();
        actualModel = new QuizModelManager(actualMgmtManager);

        quizExpected = new Quiz(SESSION_REVIEW_2.generateSession(), SESSION_REVIEW_2.getMode());
        quizActual = new Quiz(SESSION_REVIEW_2_ACTUAL.generateSession(), SESSION_REVIEW_2_ACTUAL.getMode());

        expectedModel.init(quizExpected, SESSION_REVIEW_2);
        actualModel.init(quizActual, SESSION_REVIEW_2_ACTUAL);

        actualModel.getNextCard();
        actualModel.getNextCard();
        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand(answer);

        expectedModel.getNextCard();
        expectedModel.getNextCard();
        expectedModel.updateTotalAttemptsAndStreak(1, answer);
        expectedModel.getNextCard();

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actualModel, commandHistory,
            MESSAGE_CORRECT, expectedModel);
    }

    @Test
    public void execute_correctAndWrongAnswer_success() {
        final String correctAns = "Brussels";
        final String wrongAns = "wronganswer";

        // correct
        expectedModel = new QuizModelManager(managementModelManager);
        ManagementModelManager actualMgmtManager = new ManagementModelManager();
        actualModel = new QuizModelManager(actualMgmtManager);

        expectedModel.init(quizExpected, SESSION_DEFAULT_2);
        actualModel.init(quizActual, SESSION_DEFAULT_2_ACTUAL);

        actualModel.getNextCard();
        actualModel.getNextCard();
        actualModel.getNextCard();

        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand(correctAns);
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        expectedModel.updateTotalAttemptsAndStreak(0, correctAns);
        QuizCard card = expectedModel.getNextCard();
        expectedModel.setDisplayFormatter(new QuizUiDisplayFormatter("question", card.getQuestion(),
            "answer", QuizMode.REVIEW));

        String expectedMessage = MESSAGE_CORRECT;
        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actualModel, commandHistory,
            expectedMessage, expectedModel);

        // wrong
        quizAnswerCommand = new QuizAnswerCommand(wrongAns);
        actualModel.getNextCard();
        expectedModel.updateTotalAttemptsAndStreak(0, wrongAns);
        card = expectedModel.getNextCard();

        expectedMessage = String.format(QuizAnswerCommand.MESSAGE_WRONG_ONCE, wrongAns);
        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actualModel, commandHistory,
            expectedMessage, expectedModel);

        // wrong twice
        expectedModel.updateTotalAttemptsAndStreak(0, wrongAns);
        expectedMessage = String.format(QuizAnswerCommand.MESSAGE_WRONG, wrongAns, card.getAnswer());
        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actualModel, commandHistory,
            expectedMessage, expectedModel);

        // complete the quiz
        quizAnswerCommand = new QuizAnswerCommand("Japan");
        actualModel.getNextCard();
        expectedModel.getNextCard();
        expectedModel.updateTotalAttemptsAndStreak(1, "Japan");
        expectedModel.end();

        expectedMessage = MESSAGE_CORRECT + QuizAnswerCommand.MESSAGE_COMPLETE;

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actualModel, commandHistory,
            expectedMessage, expectedModel);

    }

    @Test
    public void equals() {
        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand("Tokyo");
        QuizAnswerCommand quizAnswerCommandDiff = new QuizAnswerCommand("Something");

        // same object -> returns true
        assertEquals(quizAnswerCommand, quizAnswerCommand);

        // same values -> returns true
        QuizAnswerCommand quizAnswerCommandCopy = new QuizAnswerCommand("Tokyo");
        assertEquals(quizAnswerCommand, quizAnswerCommandCopy);

        // different answer -> returns false
        assertNotEquals(quizAnswerCommand, quizAnswerCommandDiff);

        // null -> returns false
        assertNotNull(quizAnswerCommand);

        // differnt type -> returns false
        assertNotEquals(5, quizAnswerCommand);
    }
}
