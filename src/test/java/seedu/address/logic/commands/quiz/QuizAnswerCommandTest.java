package seedu.address.logic.commands.quiz;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.quiz.Quiz;
import seedu.address.model.modelmanager.quiz.QuizCard;
import seedu.address.model.modelmanager.quiz.QuizModel;
import seedu.address.model.modelmanager.quiz.QuizModelManager;
import seedu.address.testutil.Assert;

public class QuizAnswerCommandTest {
    private static final QuizCard QUIZCARD_1 = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"));
    private static final QuizCard QUIZCARD_2 = new QuizCard("Hungary", "Budapest");
    private static final List<QuizCard> VALID_QUIZCARD = Arrays.asList(QUIZCARD_1, QUIZCARD_2);
    private static final Quiz QUIZ = new Quiz(VALID_QUIZCARD, Quiz.Mode.LEARN);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

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
        final QuizCard card1 = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"));
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2));
        final Quiz quiz = new Quiz(quizCards, Quiz.Mode.LEARN);
        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz);

        QuizModel actual = new QuizModelManager();
        actual.init(QUIZ);
        actual.getNextCard();

        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand(answer);
        expectedModel.getNextCard();
        QuizCard card = expectedModel.getNextCard();
        card.isCorrect(answer);

        String expectedMessage = String.format(QuizAnswerCommand.MESSAGE_QUESTION_ANSWER, card.getQuestion(),
            card.getAnswer());

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            expectedMessage, expectedModel);
    }

    @Test
    public void execute_validPreview_success() {
        final String answer = "Tokyo";
        final QuizCard card1 = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"));
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2));
        final Quiz quiz = new Quiz(quizCards, Quiz.Mode.PREVIEW);
        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz);

        QuizModel actual = new QuizModelManager();
        actual.init(new Quiz(quizCards, Quiz.Mode.PREVIEW));
        actual.getNextCard();

        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand(answer);
        expectedModel.getNextCard();
        QuizCard card = expectedModel.getNextCard();
        card.isCorrect(answer);

        String expectedMessage = String.format(QuizAnswerCommand.MESSAGE_QUESTION_ANSWER, card.getQuestion(),
            card.getAnswer());

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            expectedMessage, expectedModel);

        // complete preview quiz
        quizAnswerCommand = new QuizAnswerCommand("Budapest");
        expectedModel.end();

        expectedMessage = QuizAnswerCommand.MESSAGE_COMPLETE;

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            expectedMessage, expectedModel);
    }

    @Test
    public void execute_validReview_success() {
        final String answer = "Tokyo";
        final QuizCard card1 = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"));
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2));
        final Quiz quiz = new Quiz(quizCards, Quiz.Mode.LEARN);
        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz);

        QuizModel actual = new QuizModelManager();
        actual.init(new Quiz(quizCards, Quiz.Mode.LEARN));
        actual.getNextCard();
        actual.getNextCard();

        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand(answer);
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        QuizCard card = expectedModel.getNextCard();
        card.isCorrect(answer);

        String expectedMessage = String.format(QuizAnswerCommand.MESSAGE_QUESTION, card.getQuestion());

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            expectedMessage, expectedModel);
    }

    @Test
    public void execute_correctAndWrongAnswer_success() {
        final String correctAns = "Tokyo";
        final String wrongAns = "wronganswer";
        final QuizCard card1 = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"));
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2));
        final Quiz quiz = new Quiz(quizCards, Quiz.Mode.LEARN);

        // correct
        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz);

        QuizModel actual = new QuizModelManager();
        actual.init(new Quiz(quizCards, Quiz.Mode.LEARN));
        actual.getNextCard();
        actual.getNextCard();
        actual.getNextCard();

        QuizAnswerCommand quizAnswerCommand = new QuizAnswerCommand(correctAns);
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        QuizCard card = expectedModel.getNextCard();
        card.isCorrect(correctAns);

        String expectedMessage = QuizAnswerCommand.MESSAGE_CORRECT
            + String.format(QuizAnswerCommand.MESSAGE_QUESTION, card.getQuestion());

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            expectedMessage, expectedModel);

        // wrong
        quizAnswerCommand = new QuizAnswerCommand(wrongAns);
        actual.getNextCard();
        card = expectedModel.getNextCard();
        card.isCorrect(wrongAns);

        expectedMessage = QuizAnswerCommand.MESSAGE_WRONG_ONCE
            + String.format(QuizAnswerCommand.MESSAGE_QUESTION, card.getQuestion());

        QuizCommandTestUtil.assertCommandSuccess(quizAnswerCommand, actual, commandHistory,
            expectedMessage, expectedModel);

        // wrong twice
        expectedMessage = String.format(QuizAnswerCommand.MESSAGE_WRONG, card.getAnswer())
            + String.format(QuizAnswerCommand.MESSAGE_QUESTION, card.getQuestion());
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
