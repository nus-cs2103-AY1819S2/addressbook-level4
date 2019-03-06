package seedu.address.quiz.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.quiz.commands.QuizCommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.quiz.Quiz;
import seedu.address.quiz.QuizCard;
import seedu.address.quiz.QuizModel;
import seedu.address.quiz.QuizModelManager;

public class AnswerCommandTest {
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
        new AnswerCommand(null);
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

        AnswerCommand answerCommand = new AnswerCommand(answer);
        expectedModel.getNextCard();
        QuizCard card = expectedModel.getNextCard();
        card.isCorrect(answer);

        String expectedMessage = String.format(AnswerCommand.MESSAGE_QUESTION_ANSWER, card.getQuestion(),
            card.getAnswer());

        assertCommandSuccess(answerCommand, actual, commandHistory, expectedMessage, expectedModel);
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

        AnswerCommand answerCommand = new AnswerCommand(answer);
        expectedModel.getNextCard();
        QuizCard card = expectedModel.getNextCard();
        card.isCorrect(answer);

        String expectedMessage = String.format(AnswerCommand.MESSAGE_QUESTION_ANSWER, card.getQuestion(),
            card.getAnswer());

        assertCommandSuccess(answerCommand, actual, commandHistory, expectedMessage, expectedModel);

        // complete preview quiz
        answerCommand = new AnswerCommand("Budapest");
        expectedModel.end();

        expectedMessage = AnswerCommand.MESSAGE_COMPLETE;

        assertCommandSuccess(answerCommand, actual, commandHistory, expectedMessage, expectedModel);
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

        AnswerCommand answerCommand = new AnswerCommand(answer);
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        QuizCard card = expectedModel.getNextCard();
        card.isCorrect(answer);

        String expectedMessage = String.format(AnswerCommand.MESSAGE_QUESTION, card.getQuestion());

        assertCommandSuccess(answerCommand, actual, commandHistory, expectedMessage, expectedModel);
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

        AnswerCommand answerCommand = new AnswerCommand(correctAns);
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        QuizCard card = expectedModel.getNextCard();
        card.isCorrect(correctAns);

        String expectedMessage = AnswerCommand.MESSAGE_CORRECT
            + String.format(AnswerCommand.MESSAGE_QUESTION, card.getQuestion());

        assertCommandSuccess(answerCommand, actual, commandHistory, expectedMessage, expectedModel);

        // wrong
        answerCommand = new AnswerCommand(wrongAns);
        actual.getNextCard();
        card = expectedModel.getNextCard();
        card.isCorrect(wrongAns);

        expectedMessage = String.format(AnswerCommand.MESSAGE_WRONG, card.getAnswer())
            + String.format(AnswerCommand.MESSAGE_QUESTION, card.getQuestion());

        assertCommandSuccess(answerCommand, actual, commandHistory, expectedMessage, expectedModel);

        // complete the quiz
        answerCommand = new AnswerCommand("Hungary");
        actual.getNextCard();
        card = expectedModel.getNextCard();
        card.isCorrect(wrongAns);
        expectedModel.end();

        expectedMessage = AnswerCommand.MESSAGE_CORRECT + AnswerCommand.MESSAGE_COMPLETE;

        assertCommandSuccess(answerCommand, actual, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void equals() {
        AnswerCommand answerCommand = new AnswerCommand("Tokyo");
        AnswerCommand answerCommandDiff = new AnswerCommand("Something");

        // same object -> returns true
        assertTrue(answerCommand.equals(answerCommand));

        // same values -> returns true
        AnswerCommand answerCommandCopy = new AnswerCommand("Tokyo");
        assertTrue(answerCommand.equals(answerCommandCopy));

        // different answer -> returns false
        assertFalse(answerCommand.equals(answerCommandDiff));

        // null -> returns false
        assertFalse(answerCommand == null);

        // differnt type -> returns false
        assertFalse(answerCommand.equals(5));
    }
}
