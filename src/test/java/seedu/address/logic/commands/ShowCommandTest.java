package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalFlashcards.getTypicalCardCollection;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.QuizState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains unit tests for ShowCommand.
 */
public class ShowCommandTest {

    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardCollection(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    @Test
    public void execute_inQuiz_success() throws CommandException {
        new QuizCommand().execute(model, commandHistory);
        new ShowCommand().execute(model, commandHistory);
        assertEquals((int) model.getQuizMode(), QuizState.QUIZ_MODE_BOTH);
        new ShowCommand().execute(model, commandHistory);
        assertEquals((int) model.getQuizMode(), QuizState.QUIZ_MODE_BOTH);
    }

    @Test
    public void execute_notInQuiz_failure() {
        assertCommandFailure(new ShowCommand(), model, commandHistory, ShowCommand.MESSAGE_FAILURE_NOT_QUIZ_MODE);
    }
}
