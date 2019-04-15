package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_CLEAR_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Ignore;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

@Ignore
public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();
    private CurrentEdit currentEdit = new CurrentEditManager();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, MESSAGE_CLEAR_SUCCESS, expectedModel,
            currentEdit);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, MESSAGE_CLEAR_SUCCESS, expectedModel,
            currentEdit);
    }

}
