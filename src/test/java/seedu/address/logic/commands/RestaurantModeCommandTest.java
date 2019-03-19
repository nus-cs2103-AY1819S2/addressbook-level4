package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODE_CHANGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class RestaurantModeCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_restaurantMode_success() {
        CommandResult expectedCommandResult = new CommandResult(RestaurantModeCommand.MESSAGE_SUCCESS, false,
                false, Mode.RESTAURANT_MODE);
        assertCommandSuccess(Mode.TABLE_MODE, new RestaurantModeCommand(), model, commandHistory,
                expectedCommandResult, expectedModel);
        assertCommandSuccess(Mode.MENU_MODE, new RestaurantModeCommand(), model, commandHistory, expectedCommandResult,
                expectedModel);
    }

    @Test
    public void execute_restaurantMode_failure() {
        assertCommandFailure(Mode.RESTAURANT_MODE, new RestaurantModeCommand(), model, commandHistory,
                MESSAGE_INVALID_MODE_CHANGE);
    }
}
