package seedu.address.logic.commands;

import static seedu.address.logic.commands.ClearCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithPerson;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAddressBook();

        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, false, false, true);
        assertCommandSuccess(new ClearCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBookWithPerson(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBookWithPerson(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        expectedModel.commitAddressBook();

        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, false, false, true);
        assertCommandSuccess(new ClearCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }

}
