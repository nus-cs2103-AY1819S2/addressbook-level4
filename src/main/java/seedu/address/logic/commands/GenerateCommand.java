package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Generates a chart.
 */
public class GenerateCommand extends Command {

    public static final String COMMAND_WORD = "generate";
    public static final String COMMAND_ALIAS = "g";
    public static final String KEYWORD_COUNTRY = "country";
    public static final String KEYWORD_RATING = "rating";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a chart based "
            + "on either Country or Rating (case-insensitive).\n"
            + "Example: " + COMMAND_WORD + " " + KEYWORD_COUNTRY;
    public static final String MESSAGE_SUCCESS = "Chart has been successfully generated!";
    public static final String MESSAGE_FAILURE = "Unable to generate any charts!";

    public static String KEYWORD;

    public GenerateCommand(String keyword) {
        KEYWORD = keyword;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.generateChart();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
