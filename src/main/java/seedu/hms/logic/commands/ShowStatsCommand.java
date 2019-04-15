package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Optional;

import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.Model;

/**
 * Show a new window containing all stats.
 */
public class ShowStatsCommand extends Command {
    public static final String COMMAND_ALIAS = "ss";
    public static final String COMMAND_WORD = "show-stats";
    public static final String MESSAGE_SUCCESS = "Successfully generated stats.";
    public static final String MESSAGE_FAILURE = "Failed generating stats.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generate a text report and charts for statistics.\n"
            + "Parameters: [STATS_ITEM]... (Must be between 1 and the total number of stats items available)\n"
            + "Example (shows all stats items): show-stats \n"
            + "Example (shows only the 1st and 3rd stats items): show-stats 1 3";

    private static int maxIndex;

    private final Optional<ArrayList<Index>> optionalIndexList;

    public ShowStatsCommand(ArrayList<Index> indexList) {
        this.optionalIndexList = Optional.of(indexList);
    }

    public ShowStatsCommand() {
        this.optionalIndexList = Optional.empty();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (optionalIndexList.isPresent()) {
            for (Index i : optionalIndexList.get()) {
                if (i.getOneBased() > maxIndex) {
                    throw new CommandException("The selected stats item does not exist");
                }
            }
        }
        return new CommandResult(MESSAGE_SUCCESS, false, true, optionalIndexList, false);
    }

    public static void setMaxIndex(int maxIndex) {
        ShowStatsCommand.maxIndex = maxIndex;
    }
}
