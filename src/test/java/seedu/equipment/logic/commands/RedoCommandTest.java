package seedu.equipment.logic.commands;

import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.equipment.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.RedoCommand;
import seedu.equipment.model.Model;
import seedu.equipment.model.ModelManager;
import seedu.equipment.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);
        model.undoAddressBook();
        model.undoAddressBook();

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
        expectedModel.undoAddressBook();
        expectedModel.undoAddressBook();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
