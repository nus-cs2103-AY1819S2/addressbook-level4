package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Generates analytics report based on selected list of persons
 */
public class GenerateAnalyticsCommand extends Command {

    public static final String COMMAND_WORD = "analytics";
    public static final String MESSAGE_SUCCESS = "Analytics generated!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, model.generateAnalytics());
    }

}
