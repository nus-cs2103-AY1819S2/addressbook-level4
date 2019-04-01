package seedu.travel.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.travel.logic.CommandHistory;
import seedu.travel.model.Model;

/**
 * Generates a chart.
 */
public class GenerateCommand extends Command {

    public static final String COMMAND_WORD = "generate";
    public static final String COMMAND_ALIAS = "g";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a chart based "
            + "on Country and Rating.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Chart has been successfully generated!";
    public static final String MESSAGE_FAILURE = "Unable to generate any charts!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.commitChart();
        model.setChartDisplayed(true);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
