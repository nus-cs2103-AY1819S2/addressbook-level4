package seedu.address.logic.commands.quiz;

import static seedu.address.logic.commands.quiz.QuizCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalQuiz.QUIZ_LEARN;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2_ACTUAL;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.testutil.Assert;

/**
 * Contains integration tests with the QuizModel for QuizStatusCommand.
 */
public class QuizStatusCommandTest {
    private QuizModel actualModel = new QuizModelManager();
    private QuizModel expectedModel = new QuizModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_wrongModel_throwsCommandException() {
        Model model = new ManagementModelManager();
        Assert.assertThrows(CommandException.class, () ->
            new QuizStatusCommand().execute(model, commandHistory));
    }

    @Test
    public void execute_status_success() {
        expectedModel.init(QUIZ_LEARN, SESSION_DEFAULT_2);
        actualModel.init(QUIZ_LEARN, SESSION_DEFAULT_2_ACTUAL);

        actualModel.getNextCard();
        expectedModel.getNextCard();

        String expected = String.format(QuizStatusCommand.MESSAGE_SUCCESS, actualModel.getQuizTotalAttempts(),
            actualModel.getQuizTotalCorrectQuestions(), actualModel.getCurrentProgress());
        assertCommandSuccess(new QuizStatusCommand(), actualModel, commandHistory, expected, expectedModel);
    }
}
