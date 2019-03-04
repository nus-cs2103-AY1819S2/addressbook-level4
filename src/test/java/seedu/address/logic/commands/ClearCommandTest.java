package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.CustomerManager;
import seedu.address.model.CustomerModel;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.VersionedAddressBook;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new CustomerManager();
        CustomerModel expectedModel = new CustomerManager();
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        CustomerModel model = new CustomerManager(new VersionedAddressBook(getTypicalAddressBook()), new UserPrefs());
        CustomerModel expectedModel = new CustomerManager(new VersionedAddressBook(getTypicalAddressBook()), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
