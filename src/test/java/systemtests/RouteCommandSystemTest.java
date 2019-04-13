package systemtests;

import static org.junit.Assert.assertNotEquals;

import java.net.URL;

import org.junit.Test;

import seedu.equipment.logic.commands.RouteCommand;
import seedu.equipment.logic.commands.SelectCommand;
import seedu.equipment.model.Model;
import seedu.equipment.ui.BrowserPanel;

public class RouteCommandSystemTest extends EquipmentManagerSystemTest {

    @Test
    public void route() {
        /* Case: display route of equipments equipments on map
         * -> Equipments showed on map!
         */
        String command = "" + RouteCommand.COMMAND_WORD + " School of Computing, NUS, Singapore 117417";
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);

        /* ----------------------- Perform select operations and then route and select ---------------------------- */

        /* Case: Select some equipment, route, and then select the same equipment. The browser panel should change.
         */
        String selectCommand = "" + SelectCommand.COMMAND_WORD + " 1";
        URL oldUrl = getBrowserPanel().getLoadedUrl();
        executeCommand(selectCommand);
        assertNotEquals(oldUrl.toString(), getBrowserPanel().getLoadedUrl().toString());
        assertCommandSuccess(command, expectedModel);
        oldUrl = getBrowserPanel().getLoadedUrl();
        executeCommand(selectCommand);
        assertNotEquals(oldUrl.toString(), getBrowserPanel().getLoadedUrl().toString());
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
        String expectedResultMessage = RouteCommand.MESSAGE_ROUTE_EQUIPMENT_SUCCESS;
        URL oldUrl = getBrowserPanel().getLoadedUrl();
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
        assertNotEquals(oldUrl.toString(), getBrowserPanel().getLoadedUrl().toString());
        assertNotEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl().toString());
        assertNotEquals(BrowserPanel.MAP_MULTIPLE_POINT_BASE_URL, getBrowserPanel().getLoadedUrl().toString());
        assertNotEquals(BrowserPanel.MAP_ROUTE_BASE_URL, getBrowserPanel().getLoadedUrl().toString());
    }
}
