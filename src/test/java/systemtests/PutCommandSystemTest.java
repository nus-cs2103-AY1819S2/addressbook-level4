package systemtests;

import static seedu.equipment.logic.commands.CommandTestUtil.ID_DESC_LISTB;
import static seedu.equipment.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOB;

import org.junit.Test;

import seedu.equipment.logic.commands.PutCommand;
import seedu.equipment.model.Model;
import seedu.equipment.model.WorkList;
import seedu.equipment.model.equipment.Equipment;

public class PutCommandSystemTest extends EquipmentManagerSystemTest {

    @Test
    public void put() {
        Model model = getModel();

        /* ------------------------ Perform put operations on the shown unfiltered list ----------------------------- */

        /* Case: put an equipment into a worklist in the equipment manager.
         */

        Equipment putEquipment = model.getFilteredPersonList().get(2);
        WorkList putWorkList = model.getFilteredWorkListList().get(0);
        String command = " " + PutCommand.COMMAND_WORD
                + " i/" + putWorkList.getId().value
                + " s/" + putEquipment.getSerialNumber().serialNumber
                + " ";

        model.putEquipment(putWorkList.getId(), putEquipment.getSerialNumber());

        String expectedResultMessageHalf = PutCommand.MESSAGE_SUCCESS_ONE + putWorkList.getId().value
                + PutCommand.MESSAGE_SUCCESS_TWO;
        String expectedResultMessage = String.format(expectedResultMessageHalf,
                putEquipment.getSerialNumber().serialNumber);

        assertCommandSuccess(command, model, expectedResultMessage);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: the equipment is not found -> rejected */
        command = " " + PutCommand.COMMAND_WORD
                + " i/" + putWorkList.getId().value
                + " " + SERIAL_NUMBER_DESC_BOB
                + " ";
        assertCommandFailure(command, PutCommand.MESSAGE_EQUIPMENT_NOT_FOUND);

        /* Case: the worklist is not found -> rejected */
        command = " " + PutCommand.COMMAND_WORD
                + " " + ID_DESC_LISTB
                + " s/" + putEquipment.getSerialNumber().serialNumber
                + " ";
        assertCommandFailure(command, PutCommand.MESSAGE_WORKLIST_NOT_FOUND);
    }

    /**
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code EquipmentListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code EquipmentListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
