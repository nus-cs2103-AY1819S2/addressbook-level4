package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalCardFolder;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.CardFolder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyCardFolder_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitCardFolder();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCardFolder_success() {
        Model model = new ModelManager(getTypicalCardFolder(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCardFolder(), new UserPrefs());
        expectedModel.setCardFolder(new CardFolder());
        expectedModel.commitCardFolder();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
