package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalEquipments.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DisplayCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
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
