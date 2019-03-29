package seedu.address.logic.commands.quiz;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2;
import static seedu.address.testutil.TypicalSession.SESSION_DEFAULT_2_ACTUAL;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.model.quiz.Quiz;
import seedu.address.testutil.Assert;

public class QuizDifficultCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private ManagementModelManager managementModelManager;
    private Quiz quizActual;
    private Quiz quizExpected;
    private QuizModelManager actualModel;
    private QuizModelManager expectedModel;


    @Before
    public void setUp() {
        quizExpected = new Quiz(SESSION_DEFAULT_2.generateSession(), SESSION_DEFAULT_2.getMode());
        quizActual = new Quiz(SESSION_DEFAULT_2_ACTUAL.generateSession(), SESSION_DEFAULT_2_ACTUAL.getMode());
        managementModelManager = new ManagementModelManager();
    }

    @Test
    public void execute_wrongModel_throwsCommandException() {
        Model model = new ManagementModelManager();
        Assert.assertThrows(CommandException.class, () ->
            new QuizDifficultCommand().execute(model, commandHistory));
    }

    @Test
    public void execute_valid_success() {
        expectedModel = new QuizModelManager(managementModelManager);
        ManagementModelManager actualMgmtManager = new ManagementModelManager();
        actualModel = new QuizModelManager(actualMgmtManager);

        expectedModel.init(quizExpected, SESSION_DEFAULT_2);
        actualModel.init(quizActual, SESSION_DEFAULT_2_ACTUAL);

        expectedModel.getNextCard();
        expectedModel.toggleIsCardDifficult(0);
        String expectedMessage = String.format(QuizDifficultCommand.MESSAGE_SUCCESS, "difficult");

        actualModel.getNextCard();
        QuizCommandTestUtil.assertCommandSuccess(new QuizDifficultCommand(), actualModel, commandHistory,
            expectedMessage, expectedModel);

        expectedModel.toggleIsCardDifficult(0);
        expectedMessage = String.format(QuizDifficultCommand.MESSAGE_SUCCESS, "not difficult");

        QuizCommandTestUtil.assertCommandSuccess(new QuizDifficultCommand(), actualModel, commandHistory,
            expectedMessage, expectedModel);
        assertEquals(expectedModel, actualModel);
    }
}
