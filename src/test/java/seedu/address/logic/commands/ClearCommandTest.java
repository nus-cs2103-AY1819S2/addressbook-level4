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
    public void execute_emptyRestOrRant_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        CommandResult expectedCommandResult = new CommandResult(ClearCommand.MESSAGE_SUCCESS, false,
                false, Mode.RESTAURANT_MODE);

        assertCommandSuccess(Mode.RESTAURANT_MODE, new ClearCommand(), model, commandHistory,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_nonEmptyRestOrRant_success() {
        Model model = new ModelManager(TypicalRestOrRant.getTypicalRestOrRant(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalRestOrRant.getTypicalRestOrRant(), new UserPrefs());
        expectedModel.setRestOrRant(new RestOrRant());
        CommandResult expectedCommandResult = new CommandResult(ClearCommand.MESSAGE_SUCCESS, false,
                false, Mode.RESTAURANT_MODE);

        assertCommandSuccess(Mode.RESTAURANT_MODE, new ClearCommand(), model, commandHistory,
                expectedCommandResult, expectedModel);
    }

}
