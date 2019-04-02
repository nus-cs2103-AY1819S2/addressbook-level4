package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
import static seedu.address.testutil.TypicalFlashcards.getTypicalCardCollection;

import org.junit.Test;

import seedu.address.commons.core.QuizState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_inQuiz_exitsQuiz() throws CommandException {
        Model newModel = new ModelManager(getTypicalCardCollection(), new UserPrefs());
        new QuizCommand().execute(newModel, commandHistory);
        new ExitCommand().execute(newModel, commandHistory);
        assertEquals(QuizState.NOT_QUIZ_MODE, (int) newModel.getQuizMode());
    }
}
