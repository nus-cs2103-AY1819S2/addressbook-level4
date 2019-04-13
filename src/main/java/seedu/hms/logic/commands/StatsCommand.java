package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.Model;

/**
 * Show a new window containing all stats.
 */
public class StatsCommand extends Command {
    public static final String COMMAND_ALIAS = "sts";
    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_SUCCESS = "Successfully generated stats.";
    public static final String MESSAGE_FAILURE = "Failed generating stats.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, true, false);
    }
}
