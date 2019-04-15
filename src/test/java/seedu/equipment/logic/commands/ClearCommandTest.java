package seedu.equipment.logic.commands;

import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalEquipmentManager;

import org.junit.Test;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.Model;
import seedu.equipment.model.ModelManager;
import seedu.equipment.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyEquipmentManager_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitEquipmentManager();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyEquipmentManager_success() {
        Model model = new ModelManager(getTypicalEquipmentManager(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalEquipmentManager(), new UserPrefs());
        expectedModel.setEquipmentManager(new EquipmentManager());
        expectedModel.commitEquipmentManager();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
