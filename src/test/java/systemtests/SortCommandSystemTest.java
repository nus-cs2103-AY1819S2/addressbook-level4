package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import org.junit.Test;

import seedu.address.logic.commands.GoToCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;



public class SortCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void sort() {
        Model model = getModel();

        assertFalse(MainWindow.isGoToMode());

        String testParameter = " name";

        String command = "   " + SortCommand.COMMAND_WORD + testParameter + "   ";
        ModelHelper.setFilteredList(model, ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);
        assertCommandSuccess(command, model, "name");
        assertSelectedCardUnchanged();

        // Case: Repeat previous command
        command = SortCommand.COMMAND_WORD + testParameter;
        assertCommandSuccess(command, model, "name");
        assertSelectedCardUnchanged();

        // Case: Enters patient 1
        command = GoToCommand.COMMAND_WORD + " 1";
        assertCommandSuccess(command, model);

    }

    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(GoToCommand.MESSAGE_EXPAND_PERSON_SUCCESS, 1);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    private void assertCommandSuccess(String command, Model expectedModel, String para) {
        String expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, para);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }
}
