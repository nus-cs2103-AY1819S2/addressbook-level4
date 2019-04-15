package seedu.address.logic.commands;

/**
 * Represents a Stats command with hidden internal logic and the ability to be executed.
 */
public abstract class StatsCommand extends Command {
    public static final String COMMAND_WORD = "stats";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": display an overview of all the projects or "
            + "the progress of an individual project.\n"
            + OverallStatsCommand.MESSAGE_USAGE + "\n";
}
