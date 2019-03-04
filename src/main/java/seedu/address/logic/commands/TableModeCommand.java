package seedu.address.logic.commands;

import seedu.address.logic.Mode;

/**
 * Change the RestOrRant's mode to {@code Mode.TABLE_MODE}.
 * Used to adding, deleting, editing order items from Orders of a table.
 */
public class TableModeCommand extends ChangeModeCommand {
    public static final String COMMAND_WORD = "tableMode";
    public static final String MESSAGE_SUCCESS = "Mode changed to Table Mode";

    @Override
    public CommandResult generateCommandResult() {
        return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, Mode.TABLE_MODE);
    }

    @Override
    boolean isSameMode(Mode mode) {
        return mode.equals(Mode.TABLE_MODE);
    }
}
