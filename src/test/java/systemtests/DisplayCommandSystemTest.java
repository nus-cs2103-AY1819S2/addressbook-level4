package systemtests;

import static org.junit.Assert.assertNotEquals;

import java.net.URL;

import org.junit.Test;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.logic.commands.DisplayCommand;
import seedu.equipment.model.Model;
import seedu.equipment.ui.BrowserPanel;

public class DisplayCommandSystemTest extends EquipmentManagerSystemTest {

    @Test
    public void display() {
        /* Case: display multiple equipments on map
         * -> Equipments showed on map!
         */
        String command = "" + DisplayCommand.COMMAND_WORD;
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);

    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_EQUIPMENTS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = Messages.MESSAGE_EQUIPMENT_DISPLAYED_OVERVIEW;
        URL oldUrl = getBrowserPanel().getLoadedUrl();
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
        assertNotEquals(oldUrl.toString(), getBrowserPanel().getLoadedUrl().toString());
        assertNotEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl().toString());
    }
}
