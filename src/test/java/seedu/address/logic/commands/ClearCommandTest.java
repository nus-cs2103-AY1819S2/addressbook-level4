package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestOrRant;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalRestOrRant;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.updateMode();

        assertCommandSuccess(Mode.RESTAURANT_MODE, new ClearCommand(), model, commandHistory,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(TypicalRestOrRant.getTypicalRestOrRant(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalRestOrRant.getTypicalRestOrRant(), new UserPrefs());
        expectedModel.setRestOrRant(new RestOrRant());
        expectedModel.updateMode();

        assertCommandSuccess(Mode.RESTAURANT_MODE, new ClearCommand(), model, commandHistory,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
