package seedu.address.logic.commands;

import static seedu.address.logic.commands.QuizCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.QuizHelpCommand.MESSAGE_QUIZ_USAGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.modelManager.quizModel.Quiz;
import seedu.address.model.modelManager.quizModel.QuizCard;
import seedu.address.model.modelManager.quizModel.QuizModel;
import seedu.address.model.modelManager.quizModel.QuizModelManager;

public class QuizHelpCommandTest {
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
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_QUIZ_USAGE);
        assertCommandSuccess(new QuizHelpCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }
}
