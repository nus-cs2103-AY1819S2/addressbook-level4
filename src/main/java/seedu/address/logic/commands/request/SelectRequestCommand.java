package seedu.address.logic.commands.request;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Selects a request identified using it's displayed index from the request book.
 */
public class SelectRequestCommand extends Command {

    public static final String COMMAND_WORD = "sr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Selects the request identified by the index number used in the displayed request "
        + "list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
