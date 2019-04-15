package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Displays a summary of all the projects
 */
public class OverallStatsCommand extends StatsCommand {

    public static final String MESSAGE_USAGE = StatsCommand.COMMAND_WORD
            + ": Displays A summary of the projects and employees.\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return new CommandResult(model.overallStats());

    }

    @Override
    public boolean equals(Object other) {
        return other instanceof OverallStatsCommand;
    }
}
