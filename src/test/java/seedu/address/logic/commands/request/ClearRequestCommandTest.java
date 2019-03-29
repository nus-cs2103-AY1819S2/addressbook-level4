package seedu.address.logic.commands.request;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RequestBook;
import seedu.address.model.UserPrefs;

public class ClearRequestCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyOrderBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitRequestBook();

        assertCommandSuccess(new ClearRequestCommand(), model, commandHistory,
            ClearRequestCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyOrderBook_success() {
        Model model = new ModelManager(getTypicalHealthWorkerBook(),
            getTypicalRequestBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalHealthWorkerBook(),
            getTypicalRequestBook(), new UserPrefs());
        expectedModel.resetData(new RequestBook());
        expectedModel.commitRequestBook();

        assertCommandSuccess(new ClearRequestCommand(), model, commandHistory,
            ClearRequestCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
