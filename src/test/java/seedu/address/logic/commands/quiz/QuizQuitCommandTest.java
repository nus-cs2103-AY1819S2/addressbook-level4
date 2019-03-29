package seedu.address.logic.commands.quiz;

import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2_ACTUAL;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.model.quiz.Quiz;
import seedu.address.testutil.Assert;

public class QuizQuitCommandTest {
    private QuizModel actualModel = new QuizModelManager(new ManagementModelManager());
    private QuizModel expectedModel = new QuizModelManager(new ManagementModelManager());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        Quiz quizExpected = new Quiz(SESSION_DEFAULT_2.generateSession(), SESSION_DEFAULT_2.getMode());
        Quiz quizActual = new Quiz(SESSION_DEFAULT_2_ACTUAL.generateSession(), SESSION_DEFAULT_2_ACTUAL.getMode());

        expectedModel.init(quizExpected, SESSION_DEFAULT_2);
        actualModel.init(quizActual, SESSION_DEFAULT_2_ACTUAL);
    }

    @Test
    public void execute_wrongModel_throwsCommandException() {
        Model model = new ManagementModelManager();
        Assert.assertThrows(CommandException.class, () ->
            new QuizQuitCommand().execute(model, commandHistory));
    }

    @Test
    public void execute_quitsWithoutAttemptingQuestion_success() {
        expectedModel.getNextCard();
        expectedModel.end();
        String expectedMessage = String.format(QuizQuitCommand.MESSAGE_COMPLETE, 0);

        actualModel.getNextCard();
        QuizCommandTestUtil.assertCommandSuccess(new QuizQuitCommand(), actualModel, commandHistory,
            expectedMessage, expectedModel);
    }

    @Test
    public void execute_quitsAfterAttemptingOneQuestionWrongly_success() {
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        expectedModel.updateTotalAttemptsAndStreak(0, "wrong answer");
        expectedModel.end();
        String expectedMessage = String.format(QuizQuitCommand.MESSAGE_COMPLETE, 0);

        actualModel.getNextCard();
        actualModel.getNextCard();
        actualModel.getNextCard();
        actualModel.updateTotalAttemptsAndStreak(0, "wrong answer");
        QuizCommandTestUtil.assertCommandSuccess(new QuizQuitCommand(), actualModel, commandHistory,
            expectedMessage, expectedModel);
    }

    @Test
    public void execute_quitsAfterAttemptingOneQuestionCorrectly_success() {
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        expectedModel.getNextCard();
        expectedModel.updateTotalAttemptsAndStreak(0, "wrong answer");
        expectedModel.updateTotalAttemptsAndStreak(0, "wrong answer");
        expectedModel.updateTotalAttemptsAndStreak(0, "Brussels");
        expectedModel.end();
        String expectedMessage = String.format(QuizQuitCommand.MESSAGE_COMPLETE, 1);

        actualModel.getNextCard();
        actualModel.getNextCard();
        actualModel.getNextCard();
        actualModel.updateTotalAttemptsAndStreak(0, "wrong answer");
        actualModel.updateTotalAttemptsAndStreak(0, "wrong answer");
        actualModel.updateTotalAttemptsAndStreak(0, "Brussels");
        QuizCommandTestUtil.assertCommandSuccess(new QuizQuitCommand(), actualModel, commandHistory,
            expectedMessage, expectedModel);
    }


}
