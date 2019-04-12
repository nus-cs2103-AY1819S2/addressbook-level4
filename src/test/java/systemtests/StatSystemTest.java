package systemtests;

import org.junit.Test;
import org.testfx.api.FxRobot;

import seedu.address.logic.commands.StatsCommand;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalRecords;

public class StatSystemTest extends AddressBookSystemTest {

    @Test
    public void stat() {
        Model model = getModel();

        Patient currentPatient = (Patient) model.getFilteredPersonList().get(0);
        currentPatient.setRecords(TypicalRecords.getTypicalRecords());

        String cmd = "stat 1";
        String expMsg = String.format(StatsCommand.MESSAGE_SUCCESS, TypicalPersons.ALICE.getName().fullName);
        assertCommandResult(cmd, expMsg, model);

        alertRobotClick();
    }

    /**
     * For use for checking suggestion messages in patient mode
     */
    private void assertCommandResult (String commmand, String expectedResultMessage, Model expModel) {
        executeCommand(commmand);
        assertApplicationDisplaysExpected("", expectedResultMessage, expModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * To click on the generated AlertBox
     */
    private void alertRobotClick() {
        FxRobot clickBot = new FxRobot();
        clickBot.clickOn(840, 0);
    }
}
