package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import org.junit.Test;
import org.testfx.api.FxRobot;

import seedu.address.logic.commands.BackCommand;
import seedu.address.logic.commands.GoToCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.TypicalRecords;
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

        // Populates first patient with record data
        Patient currentPatient = (Patient) model.getFilteredPersonList().get(0);
        currentPatient.setRecords(TypicalRecords.getTypicalRecords());

        command = GoToCommand.COMMAND_WORD + " 1";
        assertCommandResultSuccess(command, model);

        // Case: Sort by procedure
        testParameter = " proc";
        command = SortCommand.COMMAND_WORD + testParameter;
        assertCommandResultSuccess(command, model, "proc");

        testParameter = " proc" + " desc";
        command = SortCommand.COMMAND_WORD + testParameter;
        assertCommandResultSuccess(command, model, "proc");

        testParameter = " desc";
        command = SortCommand.COMMAND_WORD + testParameter;
        assertCommandResultSuccess(command, model, "desc");

        testParameter = " desc" + " desc";
        command = SortCommand.COMMAND_WORD + testParameter;
        assertCommandResultSuccess(command, model, "desc");

        testParameter = " date";
        command = SortCommand.COMMAND_WORD + testParameter;
        assertCommandResultSuccess(command, model, "date");

        testParameter = " date" + " desc";
        command = SortCommand.COMMAND_WORD + testParameter;
        assertCommandResultSuccess(command, model, "date");

        testParameter = " gasdas";
        command = SortCommand.COMMAND_WORD + testParameter;
        assertCommandResultFailure(command, model, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            SortCommand.MESSAGE_USAGE));

        command = BackCommand.COMMAND_WORD;
        executeCommand(command);
        alertRobotClick();
        // assertCommandSuccess(command, model);

        // Case: Repeat previous command
        /*
        ModelHelper.setFilteredList(model, ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);
        testParameter = " name";
        command = SortCommand.COMMAND_WORD + testParameter;
        assertCommandSuccess(command, model, "name");
        assertSelectedCardUnchanged(); */
    }


    /**
     * For use with patient sorting
     */
    private void assertCommandPostSuccess(String command, Model expectedModel, String para) {
        String expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, para);

        executeCommand(command);
        alertRobotClick();
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * For use with patient sorting
     */
    private void assertCommandSuccess(String command, Model expectedModel, String para) {
        String expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, para);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * For use with goto mode
     */
    private void assertCommandResultSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(GoToCommand.MESSAGE_EXPAND_PERSON_SUCCESS, 1);

        executeCommand(command);
        assertApplicationRecordDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * For use with record sorting
     */
    private void assertCommandResultSuccess(String command, Model expectedModel, String para) {
        String expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, para);

        executeCommand(command);
        assertApplicationRecordDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * For record mode failure assertion
     */
    private void assertCommandResultFailure(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationRecordDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * To click on the generated AlertBox
     */
    private void alertRobotClick() {
        FxRobot clickBot = new FxRobot();
        clickBot.clickOn("Yes");
    }
}
