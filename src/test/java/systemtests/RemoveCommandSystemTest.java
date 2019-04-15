package systemtests;

import static seedu.equipment.logic.commands.CommandTestUtil.ID_DESC_LISTB;
import static seedu.equipment.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOB;

import org.junit.Test;

import seedu.equipment.logic.commands.PutCommand;
import seedu.equipment.logic.commands.RemoveCommand;
import seedu.equipment.model.Model;
import seedu.equipment.model.WorkList;
import seedu.equipment.model.equipment.Equipment;

public class RemoveCommandSystemTest extends EquipmentManagerSystemTest {

    @Test
    public void remove() {
        Model model = getModel();

        /* ------------------------ Perform remove operations on the shown unfiltered list ----------------------- */

        /* Case: remove an equipment from a worklist in the equipment manager.
         */

        WorkList removeWorkList = model.getFilteredWorkListList().get(0);
        Equipment removeEquipment = model.getFilteredPersonList().get(0);

        String putCommand = " " + PutCommand.COMMAND_WORD
                + " i/" + removeWorkList.getId().value
                + " s/" + removeEquipment.getSerialNumber().serialNumber
                + " ";

        executeCommand(putCommand);

        String command = " " + RemoveCommand.COMMAND_WORD
                + " i/" + removeWorkList.getId().value
                + " s/" + removeEquipment.getSerialNumber().serialNumber
                + " ";

        model.removeEquipment(removeWorkList.getId(), removeEquipment.getSerialNumber());

        String expectedResultMessageHalf = RemoveCommand.MESSAGE_SUCCESS_ONE + removeWorkList.getId().value
                + RemoveCommand.MESSAGE_SUCCESS_TWO;
        String expectedResultMessage = String.format(expectedResultMessageHalf,
                removeEquipment.getSerialNumber().serialNumber);

        assertCommandSuccess(command, model, expectedResultMessage);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: the equipment is not found in equipment manager -> rejected */
        command = " " + RemoveCommand.COMMAND_WORD
                + " i/" + removeWorkList.getId().value
                + " " + SERIAL_NUMBER_DESC_BOB
                + " ";
        assertCommandFailure(command, RemoveCommand.MESSAGE_EQUIPMENT_NOT_FOUND);

        /* Case: the equipment is not found in the work list -> rejected */
        Equipment notInWorkList = model.getFilteredPersonList().get(1);
        command = " " + RemoveCommand.COMMAND_WORD
                + " i/" + removeWorkList.getId().value
                + " s/" + notInWorkList.getSerialNumber().serialNumber
                + " ";
        assertCommandFailure(command, RemoveCommand.MESSAGE_EQUIPMENT_NOT_IN_WORKLIST);

        /* Case: the worklist is not found -> rejected */
        command = " " + RemoveCommand.COMMAND_WORD
                + " " + ID_DESC_LISTB
                + " s/" + removeEquipment.getSerialNumber().serialNumber
                + " ";
        assertCommandFailure(command, RemoveCommand.MESSAGE_WORKLIST_NOT_FOUND);

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
