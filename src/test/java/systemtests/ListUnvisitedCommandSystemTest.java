package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTAL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.POSTAL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL;
import static seedu.address.testutil.TypicalRestaurants.ALICE;
import static seedu.address.testutil.TypicalRestaurants.BENSON;
import static seedu.address.testutil.TypicalRestaurants.CARL;
import static seedu.address.testutil.TypicalRestaurants.DANIEL;
import static seedu.address.testutil.TypicalRestaurants.ELLE;
import static seedu.address.testutil.TypicalRestaurants.FIONA;
import static seedu.address.testutil.TypicalRestaurants.GEORGE;
import static seedu.address.testutil.TypicalRestaurants.getTypicalRestaurants;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ListUnvisitedCommand;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Postal;

public class ListUnvisitedCommandSystemTest extends FoodDiarySystemTest {
    private static final String INVALID_POSTAL_CODE = "000000";
    private static final String INVALID_LONG_POSTAL_CODE = "1234567";
    @Test
    public void listUnvisited() {

        /* Case: postal code with less than 6 digits*/
        assertCommandFailure(ListUnvisitedCommand.COMMAND_WORD + " " + INVALID_POSTAL_DESC,
                Postal.MESSAGE_CONSTRAINTS);

        /* Case: postal code with more than 6 digits*/
        assertCommandFailure(ListUnvisitedCommand.COMMAND_WORD + " " + PREFIX_POSTAL
                + INVALID_LONG_POSTAL_CODE, Postal.MESSAGE_CONSTRAINTS);

        /* Case: Missing Prefix -> rejected */
        assertCommandFailure(ListUnvisitedCommand.COMMAND_WORD + " " + VALID_POSTAL_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListUnvisitedCommand.MESSAGE_USAGE));

        /* Case: Invalid postal code, command with leading spaces and trailing spaces
         * -> Restaurant list with all unvisited restaurant shown in original order
         */
        String command = "   " + ListUnvisitedCommand.COMMAND_WORD + " " + PREFIX_POSTAL + INVALID_POSTAL_CODE + "   ";
        Model expectedModel = getModel();
        ModelHelper.setSortedList(expectedModel, getTypicalRestaurants());
        assertCommandSuccess(command, expectedModel, ListUnvisitedCommand.MESSAGE_INVALID_POSTAL_CODE);
        assertSelectedCardUnchanged();


        /* Case: multiple restaurants in food diary, command with leading spaces and trailing spaces
         * -> restaurant sorted based on distance.
         */
        command = "   " + ListUnvisitedCommand.COMMAND_WORD + " " + POSTAL_DESC_AMY + "   ";
        expectedModel = getModel();
        ModelHelper.setSortedList(expectedModel, ALICE, ELLE, BENSON, CARL, DANIEL, GEORGE, FIONA);
        //assertCommandSuccess(command, expectedModel, ListUnvisitedCommand.MESSAGE_SUCCESS);
        assertSelectedCardUnchanged();

        /* Case: multiple restaurants in food diary, command without leading spaces and trailing spaces
         * -> restaurant sorted based on distance.
         */
        command = ListUnvisitedCommand.COMMAND_WORD + " " + POSTAL_DESC_AMY;
        //assertCommandSuccess(command, expectedModel, ListUnvisitedCommand.MESSAGE_SUCCESS);
        assertSelectedCardUnchanged();

    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the String expectedResultMessage
     * and the Observable list is changed.
     * These verifications are done by
     * {@code FoodDiarySystemTest#assertApplicationDisplaysExpectedForSorting(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see FoodDiarySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpectedForSorting("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code FoodDiarySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see FoodDiarySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpectedForSorting(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
