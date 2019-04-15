package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalArchiveBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPinBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ArchiveClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyArchiveBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitBooks();

        assertCommandSuccess(new ArchiveClearCommand(), model, commandHistory,
                ArchiveClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyArchiveBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchiveBook(),
                getTypicalPinBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalArchiveBook(),
                getTypicalPinBook(), new UserPrefs());
        expectedModel.setArchiveBook(new AddressBook());
        expectedModel.commitBooks();

        assertCommandSuccess(new ArchiveClearCommand(), model, commandHistory,
                ArchiveClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
