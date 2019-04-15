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
    public static final String MESSAGE_USAGE = "Generate a text report and charts for statistics.";

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
        return new CommandResult(MESSAGE_SUCCESS, false, true, optionalIndexList, false);
    }
}
