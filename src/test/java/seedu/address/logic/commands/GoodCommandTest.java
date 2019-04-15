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
 * Contains integration tests and unit tests for GoodCommand.
 */
public class GoodCommandTest {

    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardCollection(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    @Test
    public void execute_good_success() throws CommandException {
        new QuizCommand().execute(model, commandHistory);
        new GoodCommand().execute(model, commandHistory);
        assertEquals((int) model.getQuizGood().getValue(), 1);
    }

    @Test
    public void execute_emptyQuizFlashcards_exitsQuiz() throws CommandException {
        new QuizCommand().execute(model, commandHistory);
        model.getQuizFlashcards().clear();
        new GoodCommand().execute(model, commandHistory);
        assertEquals(QuizState.NOT_QUIZ_MODE, (int) model.getQuizMode());
    }


    @Test
    public void execute_notInQuiz_failure() {
        assertCommandFailure(new GoodCommand(), model, commandHistory, GoodCommand.MESSAGE_FAILURE_NOT_QUIZ_MODE);
    }

}
