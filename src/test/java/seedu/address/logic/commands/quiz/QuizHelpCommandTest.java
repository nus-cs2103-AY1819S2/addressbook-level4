package seedu.address.logic.commands.quiz;

import static seedu.address.logic.commands.quiz.QuizCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.quiz.QuizHelpCommand.MESSAGE_QUIZ_USAGE;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2_ACTUAL;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.model.quiz.Quiz;

public class QuizHelpCommandTest {
    private QuizModel actualModel = new QuizModelManager();
    private QuizModel expectedModel = new QuizModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        Quiz quizExpected = new Quiz(SESSION_DEFAULT_2.generateSession(), SESSION_DEFAULT_2.getMode());
        Quiz quizActual = new Quiz(SESSION_DEFAULT_2_ACTUAL.generateSession(), SESSION_DEFAULT_2_ACTUAL.getMode());

        expectedModel.initWithSession(quizExpected, SESSION_DEFAULT_2);
        actualModel.initWithSession(quizActual, SESSION_DEFAULT_2_ACTUAL);
    }

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_QUIZ_USAGE);
        assertCommandSuccess(new QuizHelpCommand(), actualModel, commandHistory, expectedCommandResult, expectedModel);
    }
}
