package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalFlashcards.getTypicalCardCollection;

import org.junit.Test;

import seedu.address.commons.core.QuizState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests and unit tests for BadCommand.
 */
public class BadCommandTest {

    private Model model = new ModelManager(getTypicalCardCollection(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_bad_success() throws CommandException {
        new QuizCommand().execute(model, commandHistory);
        new BadCommand().execute(model, commandHistory);
        assertEquals((int) model.getQuizBad().getValue(), 1);
    }

    @Test
    public void execute_emptyQuizFlashcards_exitsQuiz() throws CommandException {
        new QuizCommand().execute(model, commandHistory);
        model.getQuizFlashcards().clear();
        new BadCommand().execute(model, commandHistory);
        assertEquals(QuizState.NOT_QUIZ_MODE, (int) model.getQuizMode());
    }


    @Test
    public void execute_notInQuiz_failure() throws CommandException {
        assertCommandFailure(new BadCommand(), model, commandHistory, BadCommand.MESSAGE_FAILURE_NOT_QUIZ_MODE);
    }

}
