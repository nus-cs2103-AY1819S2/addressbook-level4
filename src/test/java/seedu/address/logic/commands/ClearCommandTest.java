package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.BookShelf;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyBookShelf_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitBookShelf();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyBookShelf_success() {
        Model model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalBookShelf(), new UserPrefs());
        expectedModel.setBookShelf(new BookShelf());
        expectedModel.commitBookShelf();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
