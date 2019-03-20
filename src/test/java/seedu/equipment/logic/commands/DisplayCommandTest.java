package seedu.equipment.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.Model;
import seedu.equipment.model.ModelManager;
import seedu.equipment.model.UserPrefs;

public class DisplayCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getEquipmentManager(), new UserPrefs());
    }

    @Test
    public void execute_displayCommandExecuteSuccessful() {
        CommandHistory expectedCommandHistory = new CommandHistory(commandHistory);
        CommandResult result = new DisplayCommand().execute(model, commandHistory);
        CommandResult expectedCommandResult = new CommandResult(
                Messages.MESSAGE_EQUIPMENT_DISPLAYED_OVERVIEW, false, false, true);
        assertEquals(expectedCommandResult.toString(), result.toString());
        assertEquals(expectedModel, model);
        assertEquals(expectedCommandHistory, commandHistory);
    }
}
