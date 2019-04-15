package seedu.equipment.logic.commands;

import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalEquipmentManager;

import org.junit.Before;
import org.junit.Test;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.Model;
import seedu.equipment.model.ModelManager;
import seedu.equipment.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListWorkListCommand.
 */
public class ListWorkListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalEquipmentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getEquipmentManager(), new UserPrefs());
    }

    @Test
    public void execute_extraParameters_throwException() {
        assertCommandFailure(new ListWorkListCommand("dd"), model, commandHistory,
                ListWorkListCommand.EXTRA_PARA_EXCEPTION);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListWorkListCommand(""), model, commandHistory,
                ListWorkListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
