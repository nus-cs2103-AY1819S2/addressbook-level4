package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureGeneral;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonnelDatabase;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UserType;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PersonnelDatabase;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitPersonnelDatabase();

        assertCommandSuccess(new ClearCommand(UserType.DEFAULT_ADMIN_USERNAME),
                model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());
        expectedModel.setPersonnelDatabase(new PersonnelDatabase());
        expectedModel.commitPersonnelDatabase();

        assertCommandSuccess(new ClearCommand(UserType.DEFAULT_ADMIN_USERNAME),
                model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeGeneralEmptyAddressBookThrowsCommandException() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitPersonnelDatabase();

        assertCommandFailureGeneral(new ClearCommand(UserType.DEFAULT_ADMIN_USERNAME),
                model, commandHistory, Messages.MESSAGE_NO_AUTHORITY);
    }

    @Test
    public void executeGeneralNonEmptyAddressBookThrowsCommandException() {
        Model model = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalPersonnelDatabase(), new UserPrefs());
        expectedModel.setPersonnelDatabase(new PersonnelDatabase());
        expectedModel.commitPersonnelDatabase();

        assertCommandFailureGeneral(new ClearCommand(UserType.DEFAULT_ADMIN_USERNAME),
                model, commandHistory, Messages.MESSAGE_NO_AUTHORITY);
    }

}
