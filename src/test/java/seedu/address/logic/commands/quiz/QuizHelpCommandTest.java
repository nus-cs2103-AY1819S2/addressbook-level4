package seedu.address.logic.commands.quiz;

import static seedu.address.logic.commands.quiz.QuizCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.quiz.QuizHelpCommand.MESSAGE_QUIZ_USAGE;
import static seedu.address.testutil.TypicalQuiz.QUIZ_LEARN;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2_ACTUAL;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.QuizModelManager;

/**
 * Contains integration tests with the QuizModel for QuizHelpCommand.
 */
public class QuizHelpCommandTest {
    private QuizModel actualModel = new QuizModelManager();
    private QuizModel expectedModel = new QuizModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        expectedModel.init(QUIZ_LEARN, SESSION_DEFAULT_2);
        actualModel.init(QUIZ_LEARN, SESSION_DEFAULT_2_ACTUAL);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_QUIZ_USAGE);
        assertCommandSuccess(new QuizHelpCommand(), actualModel, commandHistory, expectedCommandResult, expectedModel);
    }
}
