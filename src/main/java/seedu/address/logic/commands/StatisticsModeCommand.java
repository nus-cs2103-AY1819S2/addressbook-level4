package seedu.address.logic.commands;

import seedu.address.logic.Mode;

/**
 * Change the RestOrRant's mode to {@code Mode.STATISTICS_MODE}.
 * Used to view information related to the restaurant's statistics like its daily/monthly/yearly revenue.
 */
public class StatisticsModeCommand extends ChangeModeCommand {
    public static final String COMMAND_WORD = "statisticsMode"; // change to standardize with other modes
    public static final String COMMAND_ALIAS = "SM";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change to Statistics Mode.\n" + "Example: " +
            COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Mode changed to Statistics Mode";

    @Override
    public CommandResult generateCommandResult() {
        return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, Mode.STATISTICS_MODE);
    }

    @Override
    boolean isSameMode(Mode mode) {
        return mode.equals(Mode.STATISTICS_MODE);
    }

}
