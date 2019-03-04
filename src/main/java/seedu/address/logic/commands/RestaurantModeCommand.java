package seedu.address.logic.commands;

import seedu.address.logic.Mode;

/**
 * Change the RestOrRant's mode to {@code Mode.RESTAURANT_MODE}.
 * Used to adding, deleting, editing order items from Orders of a table.
 */
public class RestaurantModeCommand extends ChangeModeCommand {
    public static final String COMMAND_WORD = "restaurantMode";
    public static final String MESSAGE_SUCCESS = "Mode changed to Restaurant Mode";

    @Override
    public CommandResult generateCommandResult() {
        return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, Mode.RESTAURANT_MODE);
    }

    @Override
    boolean isSameMode(Mode mode) {
        return mode.equals(Mode.RESTAURANT_MODE);
    }
}
