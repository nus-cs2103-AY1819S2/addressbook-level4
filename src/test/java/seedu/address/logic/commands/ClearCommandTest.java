package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Inventory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyInventory_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitInventory();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyInventory_success() {
        Model model = new ModelManager(getTypicalInventory(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalInventory(), new UserPrefs());
        expectedModel.setInventory(new Inventory());
        expectedModel.commitInventory();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
