package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Map;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.chart.Chart;
import seedu.address.model.place.CountryCode;
import seedu.address.model.place.Rating;

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
        Map<CountryCode, Integer> mapCountry = model.generateChartCountry();
        Map<Rating, Integer> mapRating = model.generateChartRating();
        new Chart(mapCountry, mapRating);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
