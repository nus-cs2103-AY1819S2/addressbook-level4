package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SummaryCommand.MESSAGE_NO_AUTHOR_PREFERED;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SummaryCommandTest {
    private Model model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalBookShelf(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_summary_success() throws CommandException {
        String expectedFeedBack = "You've read 8 books.\n"
            + MESSAGE_NO_AUTHOR_PREFERED
            + "Book(s) receive a rating of 10 from you: To Kill a Mocking Bird\n"
            + "You prefer books that you labeled as fantasy(including The Hunger Games, Life of Pi)\n";
        CommandResult expectedCommandResult = new CommandResult(expectedFeedBack, false, false);
        assertCommandSuccess(new SummaryCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }
}
