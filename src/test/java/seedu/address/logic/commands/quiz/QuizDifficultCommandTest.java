package seedu.address.logic.commands.quiz;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.model.modelmanager.management.ManagementModelManager;
import seedu.address.model.quiz.Quiz;
import seedu.address.model.quiz.QuizCard;
import seedu.address.testutil.Assert;

public class QuizDifficultCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private QuizModelManager quizModel = new QuizModelManager();
    private CommandHistory commandHistory = new CommandHistory();
    private Quiz quiz;


    @Before
    public void setUp() {
        final QuizCard quizCardJapan = new QuizCard("Japan", "Tokyo", Arrays.asList("JP", "Asia"));
        final QuizCard quizCardHungary = new QuizCard("Hungary", "Budapest");
        final List<QuizCard> validQuizCard = Arrays.asList(quizCardJapan, quizCardHungary);
        quiz = new Quiz(validQuizCard, Quiz.Mode.LEARN);
    }

    @Test
    public void execute_wrongModel_throwsCommandException() {
        Model model = new ManagementModelManager();
        Assert.assertThrows(CommandException.class, () ->
            new QuizDifficultCommand().execute(model, commandHistory));
    }

    @Test
    public void execute_valid_success() {
        QuizModelManager expectedModel = new QuizModelManager();
        expectedModel.init(quiz);
        expectedModel.getNextCard();
        expectedModel.toggleIsCardDifficult(0);
        String expectedMessage = String.format(QuizDifficultCommand.MESSAGE_SUCCESS, "difficult");

        quizModel.init(quiz);
        quizModel.getNextCard();
        QuizCommandTestUtil.assertCommandSuccess(new QuizDifficultCommand(), quizModel, commandHistory,
            expectedMessage, expectedModel);

        expectedModel.toggleIsCardDifficult(0);
        expectedMessage = String.format(QuizDifficultCommand.MESSAGE_SUCCESS, "not difficult");

        QuizCommandTestUtil.assertCommandSuccess(new QuizDifficultCommand(), quizModel, commandHistory,
            expectedMessage, expectedModel);
        assertEquals(expectedModel, quizModel);
    }
}
