package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 *
 */

public class ReverseCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

//    @Before
//    public void setUp() {
//        // set up of models' undo/redo history
//        deleteFirstPerson(model);
//        deleteFirstPerson(model);
//
//        deleteFirstPerson(expectedModel);
//        deleteFirstPerson(expectedModel);
//    }

    @Test
    public void execute() {
        assertCommandFailure(new ReverseCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
