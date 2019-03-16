package seedu.address.logic.commands;

import static seedu.address.logic.commands.QuizCommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.quiz.Quiz;
import seedu.address.quiz.QuizCard;
import seedu.address.quiz.QuizModel;
import seedu.address.quiz.QuizModelManager;

public class QuizStatusCommandTest {
    private QuizModel model = new QuizModelManager();
    private QuizModel expectedModel = new QuizModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        final QuizCard card1 = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"));
        final QuizCard card2 = new QuizCard("Hungary", "Budapest");
        final List<QuizCard> quizCards = new ArrayList<>(Arrays.asList(card1, card2));
        final Quiz quiz = new Quiz(quizCards, Quiz.Mode.LEARN);
        model.init(quiz);
        expectedModel.init(quiz);
    }

    @Test
    public void execute_status_success() {
        model.getNextCard();
        String expected = String.format(QuizStatusCommand.MESSAGE_RESULT, model.getQuizTotalAttempts(),
            model.getQuizTotalCorrectQuestions(), model.getCurrentProgress());
        assertCommandSuccess(new QuizStatusCommand(), model, commandHistory, expected, expectedModel);
    }
}
